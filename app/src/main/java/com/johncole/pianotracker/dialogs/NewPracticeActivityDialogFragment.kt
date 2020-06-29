package com.johncole.pianotracker.dialogs
//
//import android.annotation.SuppressLint
//import android.app.Dialog
//import android.content.Intent
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.*
//import android.widget.AdapterView.OnItemSelectedListener
//import androidx.appcompat.app.AlertDialog
//import androidx.fragment.app.DialogFragment
//import androidx.fragment.app.viewModels
//import com.johncole.pianotracker.R
//import com.johncole.pianotracker.utilities.InjectorUtils
//import com.johncole.pianotracker.utilities.PRACTICE_ACTIVITY_BUNDLE_KEY
//import com.johncole.pianotracker.utilities.SESSION_FRAGMENT_REQUEST_CODE
//import com.johncole.pianotracker.viewmodels.SessionViewModel
//
//class NewPracticeActivityDialogFragment : DialogFragment() {
//
//    private val viewModel: SessionViewModel by viewModels {
//        InjectorUtils.provideSessionViewModelFactory(requireActivity())
//    }
//
//    // variables for setup of dialog
//    private var dialogView: View? = null
//    private var technicalWorkTypeSpinner: Spinner? = null
//    private var technicalWorkTypeHeading: TextView? = null
//    private var bpmSeekBar: SeekBar? = null
//    private var bpmTextView: TextView? = null
//
//    // variables for capturing data
//    private var technicalWorkTypeSelected: String? = null
//    private var practiceWorkTypeSelected: String? = null
//    private var keySelected: String? = null
//    private var bpmSelected: Int? = null
//
//    @SuppressLint("InflateParams")
//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        val dialog = activity?.let {
//            val builder = AlertDialog.Builder(it)
//            // Get the layout inflater
//            val inflater = requireActivity().layoutInflater
//
//            // Inflate the dialog's layout - pass null as the parent view because it's going in the dialog layout
//            dialogView = inflater.inflate(R.layout.dialog_new_practice_activity, null)
//
//            builder.setView(dialogView)
//                // Add action buttons
//                .setPositiveButton(R.string.save) { _, _ ->
//                    viewModel.createSession()
//                    val intent = Intent()
//                    intent.putExtra(PRACTICE_ACTIVITY_BUNDLE_KEY, true)
//                    targetFragment?.onActivityResult(targetRequestCode, SESSION_FRAGMENT_REQUEST_CODE, intent)
//                    dialog?.dismiss()
//                }
//                .setNegativeButton(R.string.cancel) { _, _ ->
//                    dialog?.dismiss()
//                }
//            builder.create()
//        } ?: throw IllegalStateException("Activity cannot be null")
//
//        setUpAdapterForPracticeTypeSpinner(dialogView!!)
//        setUpAdapterForTechnicalWorkTypeSpinner(dialogView!!)
//        setUpAdapterForKeySelectSpinner(dialogView!!)
//        technicalWorkTypeSpinner = dialogView!!.findViewById(R.id.spinner_select_technical_work_type)
//        technicalWorkTypeHeading = dialogView!!.findViewById(R.id.txtV_select_technical_work_type)
//
//        bpmTextView = dialogView!!.findViewById(R.id.txtV_bpm_display)
//        setUpSeekBar(dialogView!!)
//
//        return dialog
//    }
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        return dialogView
//    }
//
//    override fun onDestroyView() {
//        dialogView = null
//        super.onDestroyView()
//    }
//
//    private fun setUpAdapterForPracticeTypeSpinner(view: View) {
//        val practiceActivityTypeSpinner: Spinner = view.findViewById(R.id.spinner_select_practice_activity)
//        // Create an ArrayAdapter using the string array and a default spinner layout
//        context?.let {
//            ArrayAdapter.createFromResource(
//                it,
//                R.array.practice_type_array,
//                android.R.layout.simple_spinner_item
//            ).also { adapter ->
//                // Specify the layout to use when the list of choices appears
//                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//                // Apply the adapter to the spinner
//                practiceActivityTypeSpinner.adapter = adapter
//            }
//        }
//
//        practiceActivityTypeSpinner.onItemSelectedListener = object : OnItemSelectedListener {
//            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
//                // An item was selected. You can retrieve the selected item using
//                val selectedItem = parent.getItemAtPosition(pos)
//                practiceWorkTypeSelected = selectedItem.toString()
//                if (selectedItem.toString() == "Technical Work") {
//                    technicalWorkTypeSpinner?.visibility = View.VISIBLE
//                    technicalWorkTypeHeading?.visibility = View.VISIBLE
//                } else {
//                    technicalWorkTypeSpinner?.visibility = View.GONE
//                    technicalWorkTypeHeading?.visibility = View.GONE
//                }
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>) {
//                // Another interface callback
//            }
//        }
//    }
//
//    private fun setUpAdapterForTechnicalWorkTypeSpinner(view: View) {
//        val technicalWorkTypeSpinner: Spinner = view.findViewById(R.id.spinner_select_technical_work_type)
//        // Create an ArrayAdapter using the string array and a default spinner layout
//        context?.let {
//            ArrayAdapter.createFromResource(
//                it,
//                R.array.technical_work_type_array,
//                android.R.layout.simple_spinner_item
//            ).also { adapter ->
//                // Specify the layout to use when the list of choices appears
//                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//                // Apply the adapter to the spinner
//                technicalWorkTypeSpinner.adapter = adapter
//            }
//        }
//
//        technicalWorkTypeSpinner.onItemSelectedListener = object : OnItemSelectedListener {
//            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
//                // An item was selected. You can retrieve the selected item using
//                val selectedItem = parent.getItemAtPosition(pos)
//                technicalWorkTypeSelected = selectedItem.toString()
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>) {
//                // Another interface callback
//            }
//        }
//    }
//
//    private fun setUpAdapterForKeySelectSpinner(view: View) {
//        val keySelectSpinner: Spinner = view.findViewById(R.id.spinner_select_key)
//        // Create an ArrayAdapter using the string array and a default spinner layout
//        context?.let {
//            ArrayAdapter.createFromResource(
//                it,
//                R.array.key_array,
//                android.R.layout.simple_spinner_item
//            ).also { adapter ->
//                // Specify the layout to use when the list of choices appears
//                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//                // Apply the adapter to the spinner
//                keySelectSpinner.adapter = adapter
//            }
//        }
//
//        keySelectSpinner.onItemSelectedListener = object : OnItemSelectedListener {
//            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
//                // An item was selected. You can retrieve the selected item using
//                val selectedItem = parent.getItemAtPosition(pos)
//                keySelected = selectedItem.toString()
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>) {
//                // Another interface callback
//            }
//        }
//    }
//
//    private fun setUpSeekBar(view: View) {
//        bpmSeekBar = view.findViewById(R.id.seekBar_bpm)
//        bpmTextView = view.findViewById(R.id.txtV_bpm_display)
//        var bpmProgress = 20
//
//        bpmTextView?.text = getString(R.string.seek_bar_bpm_display, bpmProgress)
//
//        bpmSeekBar?.setOnSeekBarChangeListener(object :
//            SeekBar.OnSeekBarChangeListener {
//            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
//                bpmProgress  = seek.progress
//                bpmSelected = bpmProgress
//                bpmTextView?.text = getString(R.string.seek_bar_bpm_display, bpmProgress)
//            }
//
//            override fun onStartTrackingTouch(seek: SeekBar) {
//                // write custom code for progress is started
//            }
//
//            override fun onStopTrackingTouch(seek: SeekBar) {
//                // write custom code for progress is stopped
//            }
//        })
//    }
//}