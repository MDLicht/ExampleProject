package com.mdlicht.zb.exampleproject.xmlparsing.viewholder

import androidx.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mdlicht.zb.exampleproject.R
import com.mdlicht.zb.exampleproject.baserecyclerview.viewholder.BaseViewHolder
import com.mdlicht.zb.exampleproject.databinding.ItemXmlParsingNoteBinding
import com.mdlicht.zb.exampleproject.xmlparsing.model.Note

class XmlParsingViewHolder private constructor(itemView: View): BaseViewHolder<Note>(itemView) {
    var binding: ItemXmlParsingNoteBinding = DataBindingUtil.bind(itemView)!!

    companion object {
        fun newInstance(parent: ViewGroup): XmlParsingViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_xml_parsing_note, parent, false)
            return XmlParsingViewHolder(view)
        }
    }

    override fun onBindView(position: Int, item: Note) {
        binding.note = item
    }
}