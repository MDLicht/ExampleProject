package com.mdlicht.zb.exampleproject.xmlparsing.activity

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.mdlicht.zb.exampleproject.R
import com.mdlicht.zb.exampleproject.baserecyclerview.adapter.BaseRecyclerAdapter
import com.mdlicht.zb.exampleproject.databinding.ActivityXmlParsingBinding
import com.mdlicht.zb.exampleproject.xmlparsing.adapter.XmlParsingRvAdapter
import com.mdlicht.zb.exampleproject.xmlparsing.model.Note
import com.mdlicht.zb.exampleproject.xmlparsing.viewmodel.XmlParsingViewModel

class XmlParsingActivity : AppCompatActivity() {
    lateinit var binding: ActivityXmlParsingBinding

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_xml_parsing)
        binding.apply {
            rvParsing.apply {
                layoutManager = LinearLayoutManager(this@XmlParsingActivity)
                adapter = XmlParsingRvAdapter<Note>()
            }
            vm = ViewModelProviders.of(this@XmlParsingActivity)[XmlParsingViewModel::class.java].apply {
                note.observe(this@XmlParsingActivity, Observer {
                    it?.let { note ->
                        (rvParsing.adapter as BaseRecyclerAdapter<Note>).setDataSet(listOf(note))
                    }
                })
            }
            lifecycleOwner = this@XmlParsingActivity
        }
    }
}
