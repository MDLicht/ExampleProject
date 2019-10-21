package com.mdlicht.zb.exampleproject.rangechart.activity

import androidx.databinding.DataBindingUtil
import android.graphics.Color
import android.graphics.Rect
import android.graphics.RectF
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.FrameLayout
import com.db.chart.model.LineSet
import com.db.chart.renderer.AxisRenderer
import com.db.chart.view.LineChartView
import com.mdlicht.zb.exampleproject.R
import com.mdlicht.zb.exampleproject.databinding.ActivityRangeChartBinding
import com.mdlicht.zb.exampleproject.rangechart.view.CustomRangeSeekBar

class RangeChartActivity : AppCompatActivity(), CustomRangeSeekBar.OnCustomSeekBarListener {
    lateinit var binding: ActivityRangeChartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_range_chart)

        binding.minPrice.text = 0.toString()
        binding.maxPrice.text = 100.toString()

        binding.rangebar.apply {
            setMinValue(0f)
            setMaxValue(100f)
            setOnCustomRangeSeekbarChangeListener(this@RangeChartActivity)
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
    }

    override fun onThumbChanged(minThumb: RectF, maxThumb: RectF) {
        setMask(minThumb, maxThumb)
    }

    override fun valueChanged(minValue: Number?, maxValue: Number?) {
        binding.minPrice.text = minValue.toString()
        binding.maxPrice.text = maxValue.toString()
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
}
