package com.example.islami.hom.tabs.spha

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.islami.databinding.FragmentSphaBinding

class sphaFragment : Fragment() {

    lateinit var viewBinding: FragmentSphaBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentSphaBinding.inflate(inflater, container, false)
        return viewBinding.root
    }
}