package theshaybi.androidinvertormonitor.ui.screens

import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ekn.gruzer.gaugelibrary.Range
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.LimitLine.LimitLabelPosition
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IFillFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.Utils
import theshaybi.androidinvertormonitor.R
import theshaybi.androidinvertormonitor.databinding.FirstScreenFragmentBinding
import theshaybi.androidinvertormonitor.util.MyMarkerView

class FirstScreenFragment : Fragment(), OnChartValueSelectedListener {
    private var mViewModel: FirstScreenViewModel? = null
    private var _binding: FirstScreenFragmentBinding? = null
    private var tfRegular: Typeface? = null
    private var tfLight: Typeface? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FirstScreenFragmentBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = ViewModelProvider(this)[FirstScreenViewModel::class.java]
        tfRegular = Typeface.createFromAsset(requireActivity().assets, "OpenSans-Regular.ttf")
        tfLight = Typeface.createFromAsset(requireActivity().assets, "OpenSans-Light.ttf")

        // set listeners
        _binding?.lineChart?.setOnChartValueSelectedListener(this)

        _binding?.apply {
            progressBar.progress = 50
            progressBar.max = 100

            progressBar1.progress = 50
            progressBar1.max = 100

            progressBar2.progress = 50
            progressBar2.max = 100

            //https://androidrepo.com/repo/anastr-SpeedView-android-android-ui-library
            // change speed to 140 Km/h
            imageSpeedometer.speedTo(140F)

            // move to 50 Km/s
            //speedometer.speedTo(50F)

            //By default, speed change duration between last speed and new one is 2000 ms. You can pass your duration by this method :
            // move to 50 Km/s with Duration = 4 sec
            speedView.speedTo(50F, 4000)
            //speedometer.withTremble = false
        }
        setHalfGuage()
        lineChart()
    }

    private fun lineChart() {
        // // Chart Style // //

        _binding?.apply {
            // background color
            lineChart.setBackgroundColor(Color.WHITE)

            // disable description text
            lineChart.description.isEnabled = false

            // enable touch gestures
            lineChart.setTouchEnabled(true)

            lineChart.setDrawGridBackground(false)

            // create marker to display box when values are selected

            // create marker to display box when values are selected
            val mv = MyMarkerView(requireActivity(), R.layout.custom_marker_view)

            // Set the marker to the chart
            mv.chartView = lineChart
            lineChart.marker = mv

            // enable scaling and dragging
            lineChart.isDragEnabled = true
            lineChart.setScaleEnabled(true)

            // chart.setScaleXEnabled(true);
            // chart.setScaleYEnabled(true);

            // force pinch zoom along both axis
            // chart.setScaleXEnabled(true);
            // chart.setScaleYEnabled(true);

            // force pinch zoom along both axis
            lineChart.setPinchZoom(true)
        }

        // // Create Limit Lines // //
        val llXAxis = LimitLine(9f, "Index 10")
        llXAxis.lineWidth = 4f
        llXAxis.enableDashedLine(10f, 10f, 0f)
        llXAxis.labelPosition = LimitLabelPosition.RIGHT_BOTTOM
        llXAxis.textSize = 10f
        llXAxis.typeface = tfRegular

        val upperLimit = LimitLine(150f, "Upper Limit")
        upperLimit.lineWidth = 4f
        upperLimit.enableDashedLine(10f, 10f, 0f)
        upperLimit.labelPosition = LimitLabelPosition.RIGHT_TOP
        upperLimit.textSize = 10f
        upperLimit.typeface = tfRegular

        val lowerLimit = LimitLine(-30f, "Lower Limit")
        lowerLimit.lineWidth = 4f
        lowerLimit.enableDashedLine(10f, 10f, 0f)
        lowerLimit.labelPosition = LimitLabelPosition.RIGHT_BOTTOM
        lowerLimit.textSize = 10f
        lowerLimit.typeface = tfRegular

        // // Y-Axis Style // //
        val yAxis: YAxis? = _binding?.lineChart?.axisLeft

        // disable dual axis (only use LEFT axis)
        _binding?.lineChart?.axisRight?.isEnabled = false

        // horizontal grid lines
        yAxis?.enableGridDashedLine(10f, 10f, 0f)

        // axis range
        yAxis?.axisMaximum = 200f
        yAxis?.axisMinimum = -50f


        // X-Axis Style
        val xAxis: XAxis = _binding?.lineChart?.xAxis!!

        // vertical grid lines
        xAxis.enableGridDashedLine(10f, 10f, 0f)


        // draw limit lines behind data instead of on top
        yAxis?.setDrawLimitLinesBehindData(true)
        xAxis.setDrawLimitLinesBehindData(true)

        // add limit lines
        yAxis?.addLimitLine(upperLimit)
        yAxis?.addLimitLine(lowerLimit)
        //xAxis.addLimitLine(llXAxis);

        setData(45, 180f)
    }

    private fun setData(count: Int, range: Float) {
        val values = ArrayList<Entry>()
        for (i in 0 until count) {
            val `val` = (Math.random() * range).toFloat() - 30
            values.add(Entry(i.toFloat(), `val`, resources.getDrawable(R.drawable.star)))
        }
        var set1: LineDataSet
        if (_binding?.lineChart?.data != null && _binding?.lineChart?.data?.dataSetCount!! > 0) {
            set1 = _binding!!.lineChart.data.getDataSetByIndex(0) as LineDataSet
            set1.values = values
            set1.notifyDataSetChanged()
            _binding?.lineChart?.data!!.notifyDataChanged()
            _binding?.lineChart?.notifyDataSetChanged()
        } else {
            // create a dataset and give it a type
            set1 = LineDataSet(values, "DataSet 1")
            set1.setDrawIcons(false)

            // draw dashed line
            set1.enableDashedLine(10f, 5f, 0f)

            // black lines and points
            set1.color = Color.BLACK
            set1.setCircleColor(Color.BLACK)

            // line thickness and point size
            set1.lineWidth = 1f
            set1.circleRadius = 3f

            // draw points as solid circles
            set1.setDrawCircleHole(false)

            // customize legend entry
            set1.formLineWidth = 1f
            set1.formLineDashEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
            set1.formSize = 15f

            // text size of values
            set1.valueTextSize = 9f

            // draw selection line as dashed
            set1.enableDashedHighlightLine(10f, 5f, 0f)

            // set the filled area
            set1.setDrawFilled(true)
            set1.fillFormatter = IFillFormatter { dataSet, dataProvider -> _binding!!.lineChart.axisLeft.axisMinimum }

            // set color of filled area
            if (Utils.getSDKInt() >= 18) {
                // drawables only supported on api level 18 and above
                val drawable = ContextCompat.getDrawable(requireActivity(), R.drawable.fade_red)
                set1.fillDrawable = drawable
            } else {
                set1.fillColor = Color.BLACK
            }
            val dataSets = ArrayList<ILineDataSet>()
            dataSets.add(set1) // add the data sets

            // create a data object with the data sets
            val data = LineData(dataSets)

            // set data
            _binding!!.lineChart.data = data
        }
    }


    //https://github.com/Gruzer/simple-gauge-android
    private fun setHalfGuage() {
        val range = Range()
        range.color = Color.parseColor("#ce0000")
        range.from = 0.0
        range.to = 50.0

        val range2 = Range()
        range2.color = Color.parseColor("#E3E500")
        range2.from = 50.0
        range2.to = 100.0

        val range3 = Range()
        range3.color = Color.parseColor("#00b20b")
        range3.from = 100.0
        range3.to = 150.0

        //add color ranges to gauge
        _binding?.apply {
            halfGauge.addRange(range)
            halfGauge.addRange(range2)
            halfGauge.addRange(range3)

            //set min max and current value
            halfGauge.minValue = 0.0
            halfGauge.maxValue = 150.0
            halfGauge.value = 0.0
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        Log.i("Entry selected", e.toString())
        Log.i("LOW HIGH", "low: " + _binding?.lineChart?.lowestVisibleX + ", high: " + _binding?.lineChart?.highestVisibleX)
        Log.i("MIN MAX", "xMin: " + _binding?.lineChart?.xChartMin + ", xMax: " + _binding?.lineChart?.xChartMax + ", yMin: " + _binding?.lineChart?.yChartMin
                + ", yMax: " + _binding?.lineChart?.yChartMax)

    }

    override fun onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.")
    }
}