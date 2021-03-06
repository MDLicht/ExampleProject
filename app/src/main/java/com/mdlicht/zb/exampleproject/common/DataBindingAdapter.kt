package com.mdlicht.zb.exampleproject.common

import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import android.graphics.Bitmap
import androidx.appcompat.widget.AppCompatSpinner
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mdlicht.zb.exampleproject.R
import com.mdlicht.zb.exampleproject.databinding.adapter.DatabindingExampleSpinnerAdapter
import com.mdlicht.zb.exampleproject.databinding.model.Color
import com.mdlicht.zb.exampleproject.googlemap.adapter.BicycleRecyclerAdapter
import com.mdlicht.zb.exampleproject.googlemap.model.Row
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
    fun setImageUrl(view: ImageView, url: String?) {
        Glide.with(view)
            .asBitmap()
            .load(url)
            .apply(RequestOptions().centerCrop().placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher))
            .thumbnail(0.1f)
            .into(view)
    }

    /**
     * BindingAdapter for setting image url on ImageView
     */
    @JvmStatic
    @BindingAdapter("imageBitmap")
    fun setImageBitmap(view: ImageView, bitmap: Bitmap?) {
        Glide.with(view)
            .asBitmap()
            .load(bitmap)
            .apply(RequestOptions().placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher))
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

    /**
     * BindingAdapter for setting data set on RecyclerView Adapter
     */
    @JvmStatic
    @BindingAdapter("bicycleDataSet")
    fun setBicycleDataSet(rv: RecyclerView, dataSet: List<Row>?) {
        (rv.adapter as BicycleRecyclerAdapter).setDataSet(dataSet)
    }
}