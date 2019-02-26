package com.mdlicht.zb.exampleproject.common

import android.databinding.BindingAdapter
import android.databinding.InverseBindingAdapter
import android.databinding.InverseBindingListener
import android.support.v7.widget.AppCompatSpinner
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.maps.SupportMapFragment
import com.mdlicht.zb.exampleproject.R
import com.mdlicht.zb.exampleproject.databinding.adapter.DatabindingExampleSpinnerAdapter
import com.mdlicht.zb.exampleproject.databinding.model.Color
import com.mdlicht.zb.exampleproject.googlemap.model.Response
import com.mdlicht.zb.exampleproject.googlemap.viewmodel.GoogleMapViewModel
import com.mdlicht.zb.exampleproject.room.adapter.UserRvAdapter
import com.mdlicht.zb.exampleproject.room.model.User
import com.mdlicht.zb.exampleproject.rxbasic.adapter.RxBasicRecyclerAdapter
import com.mdlicht.zb.exampleproject.rxbasic.model.GitHubData

object DataBindingAdapter {
    /**
     * BindingAdapter for setting data set on RecyclerView Adapter
     */
    @JvmStatic
    @BindingAdapter("gitHubDataSet")
    fun setGitHubDataSet(rv: RecyclerView, dataSet: List<GitHubData>?) {
        (rv.adapter as RxBasicRecyclerAdapter).setDataSet(dataSet)
    }

    @JvmStatic
    @BindingAdapter("userDataSet")
    fun setUserDataSet(rv: RecyclerView, dataSet: List<User>?) {
        (rv.adapter as UserRvAdapter).setDataSet(dataSet)
    }

    /**
     * BindingAdapter for setting image url on ImageView
     */
    @JvmStatic
    @BindingAdapter("imageUrl")
    fun setImageUrl(view: ImageView, url: String) {
        Glide.with(view)
            .asBitmap()
            .load(url)
            .apply(RequestOptions().centerCrop().placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher))
            .thumbnail(0.1f)
            .into(view)
    }

    @JvmStatic
    @BindingAdapter(value = ["selectedValue", "selectedValueAttrChanged"], requireAll = false)
    fun setSpinnerData(
        spinner: AppCompatSpinner,
        newSelectedValue: Color?,
        newTextAttrChanged: InverseBindingListener?
    ) {
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // DO NOTHING
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                newTextAttrChanged?.onChange()
            }
        }
        if (newSelectedValue != null) {
            spinner.setSelection((spinner.adapter as DatabindingExampleSpinnerAdapter).getPosition(newSelectedValue))
        }
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "selectedValue", event = "selectedValueAttrChanged")
    fun captureSelectedValue(spinner: AppCompatSpinner) : Color? {
        return spinner.selectedItem as Color
    }
}