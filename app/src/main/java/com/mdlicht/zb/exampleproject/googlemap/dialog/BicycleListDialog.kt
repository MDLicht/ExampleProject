package com.mdlicht.zb.exampleproject.googlemap.dialog

import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mdlicht.zb.exampleproject.R
import com.mdlicht.zb.exampleproject.common.FullScreenDialog
import com.mdlicht.zb.exampleproject.databinding.FragmentBicycleListDialogBinding
import com.mdlicht.zb.exampleproject.googlemap.adapter.BicycleRecyclerAdapter
import com.mdlicht.zb.exampleproject.googlemap.model.Row

class BicycleListDialog : FullScreenDialog() {
    lateinit var binding: FragmentBicycleListDialogBinding

    private var dataSet: Array<Row>? = null

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            dataSet = it.getParcelableArray(KEY_DATA_SET) as Array<Row>?
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bicycle_list_dialog, container, false)
        binding.apply {
            rvList.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = BicycleRecyclerAdapter()
                addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            }
            this.dataSet = this@BicycleListDialog.dataSet?.toList()
        }
        return binding.root
    }

    companion object {
        const val KEY_DATA_SET = "dataSet"

        @JvmStatic
        fun newInstance(dataSet: Collection<Row>?) =
            BicycleListDialog().apply {
                arguments = Bundle().apply {
                    putParcelableArray(KEY_DATA_SET, dataSet?.toTypedArray())
                }
            }
    }
}
