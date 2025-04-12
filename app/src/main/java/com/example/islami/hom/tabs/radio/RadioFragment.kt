import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.islami.R
import com.example.islami.databinding.FragmentRadioBinding
import com.example.islami.hom.tabs.radio.OnPlayClick
import com.example.islami.hom.tabs.radio.Radio
import com.example.islami.hom.tabs.radio.RadioService


class RadioFragment : Fragment() {

    private lateinit var viewBinding: FragmentRadioBinding
    var radioService: RadioService? = null
    lateinit var radioList: List<Radio>
    var currentRadioIndex = 0
    var bound = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentRadioBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getRadioList()
        initViews()
        onPlayClick()
        onPreviousClick()
        onNextClick()

    }

    private fun onNextClick() {
        viewBinding.nextIv.setOnClickListener {
            pauseMediaPlayer()
            viewBinding.playIv.setImageResource(R.drawable.play)
            currentRadioIndex = if (currentRadioIndex == radioList.size - 1) {
                0
            } else {
                ++currentRadioIndex
            }
            radioService?.initMediaPlayer(
                radioList[currentRadioIndex].url,
                radioList[currentRadioIndex].name
            )
            viewBinding.auther.text = radioList[currentRadioIndex].name
        }
    }

    private fun onPreviousClick() {
        viewBinding.previousIv.setOnClickListener {
            pauseMediaPlayer()
            viewBinding.playIv.setImageResource(R.drawable.play)
            currentRadioIndex = if (currentRadioIndex == 0) {
                radioList.size - 1
            } else {
                --currentRadioIndex
            }
            radioService?.initMediaPlayer(
                radioList[currentRadioIndex].url,
                radioList[currentRadioIndex].name
            )
            viewBinding.auther.text = radioList[currentRadioIndex].name
        }

    }

    private fun onPlayClick() {
        viewBinding.playIv.setOnClickListener {
            radioService?.let { radioService ->
                if (!radioService.getIsPlaying()) {
                    radioService.StartMediaPlayer()
                    viewBinding.playIv.setImageResource(R.drawable.pause)
                } else {
                    pauseMediaPlayer()
                    viewBinding.playIv.setImageResource(R.drawable.play)
                }

            }
        }
    }

    private fun pauseMediaPlayer() {
        if (bound) {
            radioService?.playPauseMediaPlayer()
        }
    }

    private fun initViews() {
        viewBinding.auther.text = radioList[currentRadioIndex].name
        radioService?.initMediaPlayer(
            radioList[currentRadioIndex].url,
            radioList[currentRadioIndex].name
        )
    }

    private fun getRadioList() {
        radioList = listOf(
            Radio("إبراهيم الأخضر", "https://backup.qurango.net/radio/ibrahim_alakdar"),
            Radio(
                "شيخ أبو بكر الشاطري",
                "https://backup.qurango.net/radio/shaik_abu_bakr_al_shatri"
            ),
            Radio("أحمد بن علي العجمي", "https://backup.qurango.net/radio/ahmad_alajmy"),
            Radio("أحمد الحواشي", "https://backup.qurango.net/radio/ahmad_alhawashi"),
            Radio("أحمد صابر", "https://backup.qurango.net/radio/ahmad_saber"),
            Radio("أحمد نعينع", "https://backup.qurango.net/radio/ahmad_nauina"),
            Radio("أكرم العلاقمي", "https://backup.qurango.net/radio/akram_alalaqmi"),
        )
    }

    override fun onStart() {
        super.onStart()
        bindService()
        startService()
    }

    private fun startService() {
        val intent = Intent(requireActivity(), RadioService::class.java)
        requireActivity().startService(intent)
    }

    private fun bindService() {
        val intent = Intent(requireContext(), RadioService::class.java)
        requireActivity().bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as RadioService.MyBinder
            radioService = binder.getService()
            bound = true
            radioService?.OnPlayClick = OnPlayClick { isPlayed ->
                if (isPlayed) {
                    viewBinding.playIv.setImageResource(R.drawable.pause)
                } else {
                    viewBinding.playIv.setImageResource(R.drawable.play)
                }

            }
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            radioService = null
            bound = false
        }

    }

    override fun onStop() {
        super.onStop()
        requireActivity().unbindService(connection)
    }

}