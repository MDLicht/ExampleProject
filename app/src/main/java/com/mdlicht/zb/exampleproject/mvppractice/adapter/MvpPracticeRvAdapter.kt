package com.mdlicht.zb.exampleproject.mvppractice.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.mdlicht.zb.exampleproject.R
import com.mdlicht.zb.exampleproject.mvppractice.model.GitHubData
import com.mdlicht.zb.exampleproject.mvppractice.model.GitHubProfile
import com.mdlicht.zb.exampleproject.mvppractice.model.GitHubRepo
import com.mdlicht.zb.exampleproject.mvppractice.viewholder.ContentsViewHolder
import com.mdlicht.zb.exampleproject.mvppractice.viewholder.HeaderViewHolder

class MvpPracticeRvAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val dataSet = mutableListOf<GitHubData>()

    enum class ItemType(val type: Int) {
        TYPE_HEADER(0),
        TYPE_ITEM(1)
    }

    fun setDataSet(dataSet: List<GitHubData>?) {
        this.dataSet.clear()
        if (dataSet != null) {
            this.dataSet.addAll(dataSet)
        }
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            ItemType.TYPE_HEADER.type
        } else {
            ItemType.TYPE_ITEM.type
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ItemType.TYPE_HEADER.type -> HeaderViewHolder(
                LayoutInflater.from(context).inflate(
                    R.layout.item_mvp_practice_header,
                    parent,
                    false
                )
            )
            ItemType.TYPE_ITEM.type -> ContentsViewHolder(
                LayoutInflater.from(context).inflate(
                    R.layout.item_mvp_practice_contents,
                    parent,
                    false
                )
            )
            else -> throw Exception()
        }
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> {
                holder.onBindView(position, dataSet[position] as GitHubProfile)
            }
            is ContentsViewHolder -> {
                holder.onBindView(position, dataSet[position] as GitHubRepo)
            }
            else -> throw Exception()
        }
    }
}