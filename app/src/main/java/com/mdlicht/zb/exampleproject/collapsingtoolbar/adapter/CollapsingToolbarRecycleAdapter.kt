package com.mdlicht.zb.exampleproject.collapsingtoolbar.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mdlicht.zb.exampleproject.R
import com.mdlicht.zb.exampleproject.databinding.ItemCollapsingIntroductionBinding
import com.mdlicht.zb.exampleproject.databinding.ItemCollapsingRatingLikeBinding
import com.mdlicht.zb.exampleproject.databinding.ItemCollapsingStoryBinding

class CollapsingToolbarRecycleAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    enum class ViewType(val type: Int) {
        TYPE_RATING_LIKE(0),
        TYPE_INTRODUCTION(1),
        TYPE_STORY(2)
    }

    override fun getItemViewType(position: Int): Int {
        return when(position) {
            0 -> ViewType.TYPE_RATING_LIKE.type
            1 -> ViewType.TYPE_INTRODUCTION.type
            2 -> ViewType.TYPE_STORY.type
            else -> -1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            ViewType.TYPE_RATING_LIKE.type -> {
                RatingLikeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_collapsing_rating_like, parent, false))
            }
            ViewType.TYPE_INTRODUCTION.type -> {
                IntroductionViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_collapsing_introduction, parent, false))
            }
            ViewType.TYPE_STORY.type -> {
                StoryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_collapsing_story, parent, false))
            }
            else -> {
                RatingLikeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_collapsing_rating_like, parent, false))
            }
        }
    }

    override fun getItemCount(): Int {
        return 3
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is IntroductionViewHolder) {
            holder.binding.rvIntroduction.apply {
                layoutManager = LinearLayoutManager(holder.binding.root.context)
                adapter = IntroductionRecyclerAdapter()
                isNestedScrollingEnabled = false
            }
        } else if(holder is StoryViewHolder) {
            holder.binding.rvStory.apply {
                layoutManager = LinearLayoutManager(holder.binding.root.context)
                adapter = StoryRecyclerAdapter()
                isNestedScrollingEnabled = false
            }
        }
    }

    class RatingLikeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemCollapsingRatingLikeBinding = DataBindingUtil.bind(itemView)!!
    }

    class IntroductionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemCollapsingIntroductionBinding = DataBindingUtil.bind(itemView)!!
    }

    class StoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemCollapsingStoryBinding = DataBindingUtil.bind(itemView)!!
    }
}