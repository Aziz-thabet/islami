package com.example.islami.hom.tabs.spha

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.islami.R
import com.example.islami.databinding.FragmentSphaBinding

class sphaFragment : Fragment() {

    private lateinit var azkarList: MutableList<String>
    private lateinit var binding: FragmentSphaBinding
    private var CurrentIndex = 0
    private var counter = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSphaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        azkarList = resources.getStringArray(R.array.azkarList).toMutableList()
        initView()
        onSphaClick()
    }

    private fun onSphaClick() {
        binding.sphaBody.setOnClickListener {
            binding.sphaBody.rotation += (360 / 33).toFloat()
            if (counter < 33) {
                counter++
            } else {
                counter = 0
                CurrentIndex = if (CurrentIndex < azkarList.size - 1) {
                    ++CurrentIndex
                } else {
                    0
                }
                binding.tvZikr.text = azkarList[CurrentIndex]
            }
            binding.tvCounter.text = "$counter"
        }
    }

    private fun initView() {
        binding.tvZikr.text = azkarList[CurrentIndex]
        binding.tvCounter.text = "$counter"
    }
}