package com.mdlicht.zb.exampleproject.zigzagfilter.fragment


import android.databinding.DataBindingUtil
import android.databinding.ObservableInt
import android.graphics.Color
import android.graphics.Rect
import android.graphics.RectF
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.db.chart.model.LineSet
import com.db.chart.renderer.AxisRenderer
import com.db.chart.view.LineChartView
import com.mdlicht.zb.exampleproject.R
import com.mdlicht.zb.exampleproject.databinding.FragmentFilterPriceBinding
import com.mdlicht.zb.exampleproject.rangechart.view.CustomRangeSeekBar

class FilterPriceFragment : FilterBaseFragment(), CustomRangeSeekBar.OnCustomSeekBarListener {
    lateinit var binding: FragmentFilterPriceBinding

    val minPrice: ObservableInt = ObservableInt(0)
    val maxPrice: ObservableInt = ObservableInt(100)

    var onPriceChangedListener: OnPriceChangedListener? = null

    public interface OnPriceChangedListener {
        public fun onPriceChanged(minPrice: Int, maxPrice: Int)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
        if(parentFragment is OnPriceChangedListener)
            onPriceChangedListener = parentFragment as OnPriceChangedListener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_filter_price, container, false)
        binding.fragment = this

        binding.rangebar.apply {
            setOnCustomRangeSeekbarChangeListener(this@FilterPriceFragment)
            setMinValue(0f)
            setMaxValue(100f)
            setMinStartValue(0f)
            setMaxStartValue(100f)
        }

        val halfWidth = binding.rangebar.getThumbWidthValue() / 2
        binding.chartWrapper.setPadding(halfWidth.toInt(), 0, halfWidth.toInt(), 0)

        binding.chartview.apply {
            setChartView(this, Color.parseColor("#8C8C8C"))
        }
        binding.highlightchartview.apply {
            setChartView(this, resources.getColor(R.color.colorPrimary))
        }

        return binding.root
    }

    override fun onThumbChanged(minThumb: RectF, maxThumb: RectF) {
        setMask(minThumb, maxThumb)
    }

    override fun valueChanged(minValue: Number, maxValue: Number) {
        minPrice.set(minValue.toInt())
        maxPrice.set(maxValue.toInt())

        onPriceChangedListener?.onPriceChanged(minValue.toInt(), maxValue.toInt())
    }

    private fun setMask(minThumb: RectF, maxThumb: RectF) {
        binding.mask.layoutParams = (binding.mask.layoutParams as FrameLayout.LayoutParams).apply {
            width = FrameLayout.LayoutParams.MATCH_PARENT
            leftMargin = minThumb.centerX().toInt()
            rightMargin = binding.container.width - maxThumb.centerX().toInt()
        }
        val padding = binding.chartWrapper.paddingLeft
        binding.highlightchartview.clipBounds = Rect(minThumb.centerX().toInt() - padding, 0, maxThumb.centerX().toInt() - padding, binding.chartview.height)
    }

    private fun setChartView(chartView: LineChartView, color: Int) {
        chartView?.apply {
            val chartData = LineSet()
            chartData.addPoint("", 40f)
            chartData.addPoint("", 10f)
            chartData.addPoint("", 80f)
            chartData.addPoint("", 20f)
            chartData.addPoint("", 20f)
            chartData.addPoint("", 50f)
            chartData.addPoint("", 40f)
            chartData.addPoint("", 0f)
            chartData.addPoint("", 90f)
            chartData.addPoint("", 10f)
            chartData.addPoint("", 100f)
            chartData.setFill(color)
            setXAxis(false)
            setYAxis(false)
            addData(chartData)
            setXLabels(AxisRenderer.LabelPosition.NONE)
            setYLabels(AxisRenderer.LabelPosition.NONE)
            show()
        }
    }

    override fun clearFilter() {
        binding.rangebar.setMinStartValue(0F).setMaxStartValue(100F).apply()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            FilterPriceFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}
