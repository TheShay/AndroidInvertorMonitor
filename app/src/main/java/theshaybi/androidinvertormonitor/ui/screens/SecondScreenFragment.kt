package theshaybi.androidinvertormonitor.ui.screens

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ColorTemplate
import theshaybi.androidinvertormonitor.databinding.SecondScreenFragmentBinding

class SecondScreenFragment : Fragment(), OnChartValueSelectedListener {
    private lateinit var _binding: SecondScreenFragmentBinding
    private var mViewModel: SecondScreenViewModel? = null

    // variable for our bar data.
    var barData: BarData? = null

    // variable for our bar data set.
    var barDataSet: BarDataSet? = null

    // array list for storing entries.
    private lateinit var barEntriesArrayList: ArrayList<BarEntry>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = SecondScreenFragmentBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding.barChartSecond.setOnChartValueSelectedListener(this)
        barChart()
    }

    private fun barChart() {
        try {
            _binding.apply {
                // calling method to get bar entries.
                getBarEntries()

                // creating a new bar data set.
                barDataSet = BarDataSet(barEntriesArrayList, "Geeks for Geeks")

                // creating a new bar data and
                // passing our bar data set.
                barData = BarData(barDataSet)

                // below line is to set data
                // to our bar chart.
                barChartSecond.data = barData

                // adding color to our bar data set.
                barDataSet!!.color = ColorTemplate.MATERIAL_COLORS[3]

                // setting text color.
                barDataSet!!.valueTextColor = Color.BLACK

                // setting text size

                // setting text size
                barDataSet!!.valueTextSize = 16f
                barChartSecond.description.isEnabled = false
            }
            //setBarChartData(12, 50f)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun getBarEntries() {
        // creating a new array list
        barEntriesArrayList = ArrayList()

        // adding new entry to our array list with bar
        // entry and passing x and y axis value to it.
        barEntriesArrayList.add(BarEntry(1f, 4f))
        barEntriesArrayList.add(BarEntry(2f, 6f))
        barEntriesArrayList.add(BarEntry(3f, 8f))
        barEntriesArrayList.add(BarEntry(4f, 2f))
        barEntriesArrayList.add(BarEntry(5f, 4f))
        barEntriesArrayList.add(BarEntry(6f, 1f))
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProvider(this).get(SecondScreenViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        TODO("Not yet implemented")
    }

    override fun onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.")
    }
}