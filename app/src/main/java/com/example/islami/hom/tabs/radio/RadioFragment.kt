import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.islami.R
import com.example.islami.databinding.FragmentRadioBinding
import com.example.islami.hom.tabs.radio.ApiClient
import com.example.islami.hom.tabs.radio.OnPlayClick
import com.example.islami.hom.tabs.radio.Radio
import com.example.islami.hom.tabs.radio.RadioService
import kotlinx.coroutines.launch


class RadioFragment : Fragment() {

    private lateinit var viewBinding: FragmentRadioBinding
    var radioService: RadioService? = null
    private var radioList: List<Radio> = emptyList()
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
        getRadioListFromApi()
        onPlayClick()
        onPreviousClick()
        onNextClick()

    }

    private fun onNextClick() {
        viewBinding.nextIv.setOnClickListener {
            showLoading() // ✅ اظهار اللودينج
            pauseMediaPlayer()
            viewBinding.playIv.setImageResource(R.drawable.play)

            currentRadioIndex =
                if (currentRadioIndex == radioList.size - 1) 0 else currentRadioIndex + 1
            val nextRadio = radioList[currentRadioIndex]

            viewBinding.auther.text = nextRadio.name

            radioService?.initMediaPlayer(nextRadio.url, nextRadio.name)

            // ✅ نشغل الراديو مباشرة لما يتحضر
            radioService?.mediaPlayer?.setOnPreparedListener {
                hideLoading() // ✅ نخفي اللودينج
                radioService?.StartMediaPlayer()
                viewBinding.playIv.setImageResource(R.drawable.pause)
            }
        }
    }


    private fun onPreviousClick() {
        viewBinding.previousIv.setOnClickListener {
            showLoading()
            pauseMediaPlayer()
            viewBinding.playIv.setImageResource(R.drawable.play)

            currentRadioIndex =
                if (currentRadioIndex == 0) radioList.size - 1 else currentRadioIndex - 1
            val previousRadio = radioList[currentRadioIndex]

            viewBinding.auther.text = previousRadio.name
            radioService?.initMediaPlayer(previousRadio.url, previousRadio.name)

            radioService?.mediaPlayer?.setOnPreparedListener {
                hideLoading()
                radioService?.StartMediaPlayer()
                viewBinding.playIv.setImageResource(R.drawable.pause)
            }
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
    private fun getRadioListFromApi() {
        lifecycleScope.launch {
            try {
                val response = ApiClient.retrofit.getRadioList()
                radioList = response.radios
                currentRadioIndex = 0
                initViews()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(
                    requireContext(),
                    "فشل في جلب البيانات: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun showLoading() {
        viewBinding.progressBar.visibility = View.VISIBLE
        viewBinding.playIv.visibility = View.INVISIBLE
    }

    private fun hideLoading() {
        viewBinding.progressBar.visibility = View.GONE
        viewBinding.playIv.visibility = View.VISIBLE
    }


}