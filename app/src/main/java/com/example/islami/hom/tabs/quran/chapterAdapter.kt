package com.example.islami.hom.tabs.quran

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.islami.databinding.ItemChabterBinding
import com.example.islami.model.Chapter

class chapterAdapter(val chapters: List<Chapter>) :
    RecyclerView.Adapter<chapterAdapter.ViewHolder>() {

    class ViewHolder(val itemBinding: ItemChabterBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ItemChabterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return chapters.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val chapter = chapters[position]

        holder.itemBinding.EnTitleTv.text = chapter.titleEn
        holder.itemBinding.ArTitleTv.text = chapter.titleAr
        holder.itemBinding.versesNumberTv.text = chapter.versesNumber
        holder.itemBinding.chapterIndexTv.text = "${chapter.index + 1}"

        onItemClick?.let { onClick ->
            holder.itemView.setOnClickListener {
                onClick.onItemClick(position, chapter)
            }
        }

    }

    var onItemClick: OnItemClick? = null

    fun interface OnItemClick {
        fun onItemClick(position: Int, chapter: Chapter)
    }
}