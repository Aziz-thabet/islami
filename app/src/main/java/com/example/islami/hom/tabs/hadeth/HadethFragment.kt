package com.example.islami.hom.tabs.hadeth

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.islami.databinding.FragmentHadethBinding
import com.example.islami.model.Hadeeth
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.CarouselSnapHelper
import com.google.android.material.carousel.FullScreenCarouselStrategy


class HadethFragment : Fragment() {
    lateinit var viewBinding: FragmentHadethBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentHadethBinding.inflate(inflater, container, false)
        return viewBinding.root
    }
    val hadeethList: MutableList<Hadeeth> = mutableListOf()
    lateinit var adapter: HadethCarouselAdapter
    lateinit var layoutManager: CarouselLayoutManager


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        readAhadeethFile()
        initHadethRecycler()
    }

    private fun initHadethRecycler() {
        adapter = HadethCarouselAdapter(hadeethList)
        layoutManager = CarouselLayoutManager(
            FullScreenCarouselStrategy(),

            CarouselLayoutManager.HORIZONTAL
        )
        layoutManager.carouselAlignment = CarouselLayoutManager.ALIGNMENT_CENTER
        viewBinding.hadeethRecyclerView.adapter = adapter
        viewBinding.hadeethRecyclerView.layoutManager = layoutManager

        val snapHelper = CarouselSnapHelper()
        snapHelper.attachToRecyclerView(viewBinding.hadeethRecyclerView)

        viewBinding.hadeethRecyclerView.addItemDecoration(
            MarginItemDecoration(getMarginInPx())
        )
    }

    fun readAhadeethFile() {
        val fileContent =
            activity?.assets?.open("hadeeth/hadeeth.txt")?.bufferedReader().use { it?.readText() }
        if (fileContent == null) return
        val hadeethLinesList = fileContent.trim().split("#")
        hadeethLinesList.forEach { singleHadeeth ->
            val lines = singleHadeeth.trim().split("\n")
            val title = lines[0]
            val content = lines.takeLast(lines.size - 1).joinToString("\n")
            val hadeeth = Hadeeth(title, content)
            hadeethList.add(hadeeth)
        }

    }

    fun getMarginInPx(): Int {
        val r = resources
        val px = Math.round(
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 24f, r.displayMetrics
            )
        )
        return px
    }
}