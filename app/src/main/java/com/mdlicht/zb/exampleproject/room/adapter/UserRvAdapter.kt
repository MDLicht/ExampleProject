package com.mdlicht.zb.exampleproject.room.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mdlicht.zb.exampleproject.R
import com.mdlicht.zb.exampleproject.databinding.ItemRoomContentsBinding
import com.mdlicht.zb.exampleproject.room.iface.OnUserClickListener
import com.mdlicht.zb.exampleproject.room.model.User

class UserRvAdapter(private val onClickListener: OnUserClickListener): RecyclerView.Adapter<UserRvAdapter.UserViewHolder>() {
    private val dataSet: MutableList<User> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_room_contents, parent, false))
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.binding!!.apply {
            user = dataSet[position]
            listener = onClickListener
        }
    }

    fun setDataSet(dataSet: List<User>?) {
        this.dataSet.clear()
        if(!dataSet.isNullOrEmpty()) {
            this.dataSet.addAll(dataSet)
        }
        notifyDataSetChanged()
    }

    class UserViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val binding = DataBindingUtil.bind<ItemRoomContentsBinding>(itemView)
    }
}