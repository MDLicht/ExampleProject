package com.mdlicht.zb.exampleproject.constraintlayout.adapter

import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mdlicht.zb.exampleproject.R
import com.mdlicht.zb.exampleproject.databinding.ItemConstraintlayoutContentsBinding

class ConstraintLayoutRecyclerAdapter : RecyclerView.Adapter<ConstraintLayoutRecyclerAdapter.ContentsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentsViewHolder {
        return ContentsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_constraintlayout_contents, parent, false))
    }

    override fun getItemCount(): Int {
        return 5
    }

    override fun onBindViewHolder(holder: ContentsViewHolder, position: Int) {

    }

    class ContentsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val binding: ItemConstraintlayoutContentsBinding = DataBindingUtil.bind(itemView)!!
    }
}