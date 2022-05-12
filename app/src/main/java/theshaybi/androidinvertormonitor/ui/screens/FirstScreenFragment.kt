package theshaybi.androidinvertormonitor.ui.screens

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ekn.gruzer.gaugelibrary.Range
import theshaybi.androidinvertormonitor.databinding.FirstScreenFragmentBinding

class FirstScreenFragment : Fragment() {
    private var mViewModel: FirstScreenViewModel? = null
    private var _binding: FirstScreenFragmentBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FirstScreenFragmentBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding?.apply {
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
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProvider(this)[FirstScreenViewModel::class.java]
        setHalfGuage()
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
}