package com.example.islami.hom.tabs.hadeth

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.islami.databinding.ItemHadeethBinding
import com.example.islami.model.Hadeeth

class HadethCarouselAdapter(val hadeethList: List<Hadeeth>) :
    RecyclerView.Adapter<HadethCarouselAdapter.ViewHolder>() {

    class ViewHolder(val viewBinding: ItemHadeethBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ItemHadeethBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ViewHolder(itemBinding)
    }

    override fun getItemCount(): Int = hadeethList.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hadeeth = hadeethList[position]
        holder.viewBinding.hadethTitleTv.text = hadeeth.title
        holder.viewBinding.hadeethContentTv.text = hadeeth.content
    }
}