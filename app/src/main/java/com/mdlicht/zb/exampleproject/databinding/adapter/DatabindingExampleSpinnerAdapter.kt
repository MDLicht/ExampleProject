package com.mdlicht.zb.exampleproject.databinding.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.mdlicht.zb.exampleproject.databinding.model.Color

class DatabindingExampleSpinnerAdapter(context: Context) : ArrayAdapter<Color>(context, android.R.layout.simple_spinner_dropdown_item, mutableListOf<Color>(Color("RED", "#FF0000"), Color("BLUE", "#0000FF"), Color("GREEN", "#00FF00"))) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val label = super.getView(position, convertView, parent) as TextView
        label.text = super.getItem(position).name
        return label
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val label = super.getView(position, convertView, parent) as TextView
        label.text = super.getItem(position).name
        return label
    }
}