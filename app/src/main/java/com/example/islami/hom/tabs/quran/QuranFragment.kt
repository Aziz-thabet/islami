package com.example.islami.hom.tabs.quran

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.islami.databinding.FragmentQuranBinding
import com.example.islami.hom.AppConstens
import com.example.islami.hom.tabs.chpterDetails.ChapterDetailsActivity

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
        adapter.onItemClick = chapterAdapter.OnItemClick { position, chapter ->
            val intent = Intent(activity, ChapterDetailsActivity::class.java)
            intent.putExtra(AppConstens.EXTRA.EXTRA_CHAPTER, chapter)
            startActivity(intent)
        }
        viewBinding.chapterRecV.adapter = adapter
    }

    val chapters = AppConstens.getChapters()
}