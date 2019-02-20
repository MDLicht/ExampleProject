package com.mdlicht.zb.exampleproject.zigzagfilter.adapter

import android.databinding.DataBindingUtil
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mdlicht.zb.exampleproject.R
import com.mdlicht.zb.exampleproject.databinding.ItemFilterColorBinding

class FilterColorRvAdapter(listener: OnSelctedColorChangedListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val colorList: List<String> = listOf(
        "#FF0000", "#FF5E00", "#FFBB00", "#FFE400", "#ABF200",
        "#1DDB16", "#00D8FF", "#0054FF", "#0100FF", "#5F00FF",
        "#FF00DD", "#FFA7A7", "#FFC19E", "#FFE08C", "#FAED7D",
        "#CEF279", "#B7F0B1", "#B2EBF4", "#B2CCFF", "#B5B2FF"
    )

    private var onSelctedColorChangedListener: OnSelctedColorChangedListener? = listener

    private val selectedColorList: MutableList<String> = mutableListOf()

    public interface OnSelctedColorChangedListener {
        public fun onSelectedColorChanged(colorList: List<String>)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FilterColorViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_filter_color, parent, false))
    }

    override fun getItemCount(): Int {
        return colorList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is FilterColorViewHolder) {
            holder.binding.ivColorChip.setBackgroundColor(Color.parseColor(colorList[position]))
            holder.binding.tvColorCode.text = colorList[position]
            holder.binding.root.setOnClickListener {
                val colorCode = colorList[position]
                if(selectedColorList.contains(colorCode)) {
                    selectedColorList.remove(colorCode)
                } else {
                    selectedColorList.add(colorCode)
                }
                onSelctedColorChangedListener?.onSelectedColorChanged(selectedColorList)
                notifyItemChanged(position)
            }
            if(selectedColorList.contains(colorList[position])) {
                holder.binding.mask.visibility = View.VISIBLE
            } else {
                holder.binding.mask.visibility = View.GONE
            }
        }
    }

    public fun clearColorFilter() {
        selectedColorList.clear()
        onSelctedColorChangedListener?.onSelectedColorChanged(selectedColorList)
        notifyDataSetChanged()
    }

    class FilterColorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemFilterColorBinding = DataBindingUtil.bind(itemView)!!
    }
}