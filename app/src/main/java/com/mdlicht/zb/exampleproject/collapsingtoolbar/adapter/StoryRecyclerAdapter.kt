package com.mdlicht.zb.exampleproject.collapsingtoolbar.adapter

import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mdlicht.zb.exampleproject.R
import com.mdlicht.zb.exampleproject.collapsingtoolbar.model.DataProvider
import com.mdlicht.zb.exampleproject.collapsingtoolbar.model.Story
import com.mdlicht.zb.exampleproject.databinding.ItemCollapsingStoryContentsBinding
import com.mdlicht.zb.exampleproject.databinding.ItemCollapsingStoryTitleBinding
import java.lang.Exception

class StoryRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val dataSet: List<Story> = DataProvider.getStroy()

    enum class ViewType(val type: Int) {
        TYPE_TITLE(0),
        TYPE_CONTENTS(1)
    }

    override fun getItemViewType(position: Int): Int {
        return when(position) {
            0 -> ViewType.TYPE_TITLE.type
            else -> ViewType.TYPE_CONTENTS.type
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            ViewType.TYPE_TITLE.type -> {
                TitleViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_collapsing_story_title, parent, false))
            }
            ViewType.TYPE_CONTENTS.type -> {
                ContentsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_collapsing_story_contents, parent, false))
            }
            else -> {
                throw Exception()
            }
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size + 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ContentsViewHolder) {
            holder.binding.title = dataSet[position - 1].title
            holder.binding.contents = dataSet[position - 1].contents.joinToString()
        }
    }

    class TitleViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val binding: ItemCollapsingStoryTitleBinding = DataBindingUtil.bind(itemView)!!
    }

    class ContentsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val binding: ItemCollapsingStoryContentsBinding = DataBindingUtil.bind(itemView)!!
    }
}