package com.mdlicht.zb.exampleproject.main.viewmodel

import androidx.lifecycle.ViewModel
import android.content.Intent
import android.view.View
import com.mdlicht.zb.exampleproject.R
import com.mdlicht.zb.exampleproject.baserecyclerview.activity.BaseRecyclerViewActivity
import com.mdlicht.zb.exampleproject.collapsingtoolbar.activity.CollapsingToolbarActivity
import com.mdlicht.zb.exampleproject.common.showToast
import com.mdlicht.zb.exampleproject.constraintlayout.activity.ConstraintLayoutActivity
import com.mdlicht.zb.exampleproject.databinding.activity.DatabindingExampleActivity
import com.mdlicht.zb.exampleproject.exoplayer.acitivity.ExoPlayerActivity
import com.mdlicht.zb.exampleproject.googlemap.activity.GoogleMapActivity
import com.mdlicht.zb.exampleproject.koinexample.activity.KoinExampleActivity
import com.mdlicht.zb.exampleproject.mvppractice.activity.MvpPracticeActivity
import com.mdlicht.zb.exampleproject.opencv.activity.OpenCvActivity
import com.mdlicht.zb.exampleproject.rangechart.activity.RangeChartActivity
import com.mdlicht.zb.exampleproject.recyclerviewwithad.activity.RecyclerViewWithAdActivity
import com.mdlicht.zb.exampleproject.room.activity.RoomActivity
import com.mdlicht.zb.exampleproject.rxbasic.activity.RxBasicActivity
import com.mdlicht.zb.exampleproject.simpletranslator.activity.SimpleTranslatorActivity
import com.mdlicht.zb.exampleproject.xmlparsing.activity.XmlParsingActivity
import com.mdlicht.zb.exampleproject.zigzagfilter.activity.ZigzagFilterActivity

class ItemMainViewModel : ViewModel() {
    /**
     * @param view : Clicked view
     * @param title : Title on Clicked item
     */
    fun onItemClick(view: View, title: String) {
        val context = view.context
        val item = title.substring(title.indexOf(".")+1).trim()
        when(item) {
            context.getString(R.string.item_title_social_login) -> {
                view.context.showToast(view.context.getString(R.string.msg_example_deleted))
            }
            context.getString(R.string.item_title_rx_basic) -> {
                context.startActivity(Intent(context, RxBasicActivity::class.java))
            }
            context.getString(R.string.item_title_databinding_example) -> {
                context.startActivity(Intent(context, DatabindingExampleActivity::class.java))
            }
            context.getString(R.string.item_title_simple_translator) -> {
                context.startActivity(Intent(context, SimpleTranslatorActivity::class.java))
            }
            context.getString(R.string.item_title_google_map) -> {
                context.startActivity(Intent(context, GoogleMapActivity::class.java))
            }
            context.getString(R.string.item_title_recyclerview_with_ad) -> {
                context.startActivity(Intent(context, RecyclerViewWithAdActivity::class.java))
            }
            context.getString(R.string.item_title_constraintlayout) -> {
                context.startActivity(Intent(context, ConstraintLayoutActivity::class.java))
            }
            context.getString(R.string.item_title_collapsing_toolbar) -> {
                context.startActivity(Intent(context, CollapsingToolbarActivity::class.java))
            }
            context.getString(R.string.item_title_range_chart) -> {
                context.startActivity(Intent(context, RangeChartActivity::class.java))
            }
            context.getString(R.string.item_title_zigzag_filter) -> {
                context.startActivity(Intent(context, ZigzagFilterActivity::class.java))
            }
            context.getString(R.string.item_title_room_example) -> {
                context.startActivity(Intent(context, RoomActivity::class.java))
            }
            context.getString(R.string.item_title_base_recylcerview) -> {
                context.startActivity(Intent(context, BaseRecyclerViewActivity::class.java))
            }
            context.getString(R.string.item_title_xml_parsing) -> {
                context.startActivity(Intent(context, XmlParsingActivity::class.java))
            }
            context.getString(R.string.item_title_exo_player) -> {
                context.startActivity(Intent(context, ExoPlayerActivity::class.java))
            }
            context.getString(R.string.item_title_mvp_practice) -> {
                context.startActivity(Intent(context, MvpPracticeActivity::class.java))
            }
            context.getString(R.string.item_title_opencv) -> {
                context.startActivity(Intent(context, OpenCvActivity::class.java))
            }
            context.getString(R.string.item_title_koin) -> {
                context.startActivity(Intent(context, KoinExampleActivity::class.java))
            }
        }
    }
}