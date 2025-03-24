package com.example.islami.hom.tabs.quran

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.islami.databinding.FragmentQuranBinding

class QuranFragment : Fragment() {


    lateinit var viewBinding: FragmentQuranBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentQuranBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    lateinit var adapter: chapterAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = chapterAdapter(chapters)
        viewBinding.chapterRecV.adapter = adapter
    }

    val chapters = AppConstens.getChapters()
}