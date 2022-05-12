package theshaybi.androidinvertormonitor.ui.inverter

import android.app.Dialog
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import theshaybi.androidinvertormonitor.R
import theshaybi.androidinvertormonitor.databinding.InverterListFragmentBinding

class InverterListFragment : Fragment() {
    private var mViewModel: InverterListViewModel? = null
    private lateinit var _binding: InverterListFragmentBinding
    private var switchATSPDialog: Dialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        _binding = InverterListFragmentBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProvider(this)[InverterListViewModel::class.java]
        _binding.btnFirstInverter.setOnClickListener {
            inverterSelectionDialog()
            /*val action = InverterListFragmentDirections.actionInverterListFragmentToFirstScreenFragment()
            it.findNavController().navigate(action)*/
        }

        _binding.btnSecondInverter.setOnClickListener {
            val action = InverterListFragmentDirections.actionInverterListFragmentToSecondScreenFragment()
            it.findNavController().navigate(action)
        }
        _binding.btnThirdInverter.setOnClickListener {
            val action = InverterListFragmentDirections.actionInverterListFragmentToSettingsFragment()
            it.findNavController().navigate(action)
        }
    }

    private fun inverterSelectionDialog() {
        try {
            if (switchATSPDialog != null && switchATSPDialog!!.isShowing) switchATSPDialog!!.dismiss()
            val inflater = activity?.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val layout: View = inflater.inflate(R.layout.dialog_inverters, null)
            switchATSPDialog = Dialog(requireActivity())
            switchATSPDialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
            switchATSPDialog?.window?.decorView?.setBackgroundResource(android.R.color.transparent)
            switchATSPDialog?.setContentView(layout)
            switchATSPDialog?.setCancelable(false)

            val btnScreen1 = layout.findViewById<TextView>(R.id.btn_screen1)
            val btnScreen2 = layout.findViewById<TextView>(R.id.btn_screen2)

            btnScreen1.setOnClickListener {
                val action = InverterListFragmentDirections.actionInverterListFragmentToFirstScreenFragment()
                findNavController().navigate(action)
                switchATSPDialog?.dismiss()
            }
            btnScreen2.setOnClickListener {
                val action = InverterListFragmentDirections.actionInverterListFragmentToSecondScreenFragment()
                findNavController().navigate(action)
                switchATSPDialog?.dismiss()
            }
            switchATSPDialog?.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}