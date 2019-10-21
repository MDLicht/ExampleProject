package com.mdlicht.zb.exampleproject.constraintlayout.activity

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.LinearLayoutManager
import android.transition.TransitionManager
import android.view.View
import android.widget.Toast
import com.mdlicht.zb.exampleproject.R
import com.mdlicht.zb.exampleproject.constraintlayout.adapter.ConstraintLayoutRecyclerAdapter
import com.mdlicht.zb.exampleproject.constraintlayout.viewmodel.ConstraintViewModel
import com.mdlicht.zb.exampleproject.databinding.ActivityConstraintLayoutBinding

class ConstraintLayoutActivity : AppCompatActivity() {
    lateinit var binding: ActivityConstraintLayoutBinding
    var constraintToggle: Boolean = false

    val bigConstraintSet: ConstraintSet by lazy {
        ConstraintSet().apply {
            load(this@ConstraintLayoutActivity, R.layout.activity_constraint_layout_2)
        }
    }
    val normalConstraintSet: ConstraintSet by lazy {
        ConstraintSet().apply {
            load(this@ConstraintLayoutActivity, R.layout.activity_constraint_layout)
        }
    }
    val rvAdapter: ConstraintLayoutRecyclerAdapter by lazy {
        ConstraintLayoutRecyclerAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_constraint_layout)
        binding.vm = ViewModelProviders.of(this).get(ConstraintViewModel::class.java)
        binding.vm!!.nextClick.observe(this, Observer {
            if (it!!) {
                Toast.makeText(this, R.string.constraintlayout_msg_final_step, Toast.LENGTH_SHORT).show()
            } else {
                TransitionManager.beginDelayedTransition(binding.constraintLayout)
                bigConstraintSet.applyTo(binding.constraintLayout)
                binding.rvContents.adapter = rvAdapter
            }
        })
        binding.vm!!.cancelClick.observe(this, Observer {
            TransitionManager.beginDelayedTransition(binding.constraintLayout)
            normalConstraintSet.applyTo(binding.constraintLayout)
            binding.rvContents.adapter = null
        })
        binding.rvContents.layoutManager = LinearLayoutManager(this)
    }
}
