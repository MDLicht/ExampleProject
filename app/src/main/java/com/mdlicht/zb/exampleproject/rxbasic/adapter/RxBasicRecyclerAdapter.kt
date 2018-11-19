package com.mdlicht.zb.exampleproject.rxbasic.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mdlicht.zb.exampleproject.R
import com.mdlicht.zb.exampleproject.databinding.ItemRxbasicContentsBinding
import com.mdlicht.zb.exampleproject.databinding.ItemRxbasicHeaderBinding
import com.mdlicht.zb.exampleproject.rxbasic.model.GitHubData
import com.mdlicht.zb.exampleproject.rxbasic.model.GitHubProfile
import com.mdlicht.zb.exampleproject.rxbasic.model.GitHubRepo
import com.mdlicht.zb.exampleproject.rxbasic.viewmodel.GitHubProfileViewModel
import com.mdlicht.zb.exampleproject.rxbasic.viewmodel.GitHubRepoViewModel

class RxBasicRecyclerAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val dataSet = mutableListOf<GitHubData>()

    enum class ItemType(val type: Int) {
        TYPE_HEADER(0),
        TYPE_ITEM(1)
    }

    fun setDataSet(dataSet: List<GitHubData>?) {
        if (dataSet != null) {
            this.dataSet.clear()
            this.dataSet.addAll(dataSet)
        } else {
            this.dataSet.clear()
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
            ItemType.TYPE_HEADER.type -> RxBasicHeaderViewHolder(
                LayoutInflater.from(context).inflate(
                    R.layout.item_rxbasic_header,
                    parent,
                    false
                )
            )
            ItemType.TYPE_ITEM.type -> RxBasicContentsViewHolder(
                LayoutInflater.from(context).inflate(
                    R.layout.item_rxbasic_contents,
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
            is RxBasicHeaderViewHolder -> {
                holder.binding.vm = GitHubProfileViewModel()
                holder.binding.profile = dataSet[position] as GitHubProfile
            }
            is RxBasicContentsViewHolder -> {
                holder.binding.vm = GitHubRepoViewModel()
                holder.binding.repo = dataSet[position] as GitHubRepo
            }
            else -> throw Exception()
        }
    }

    class RxBasicContentsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemRxbasicContentsBinding = DataBindingUtil.bind(itemView)!!
    }

    class RxBasicHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemRxbasicHeaderBinding = DataBindingUtil.bind(itemView)!!
    }
}