package com.johncole.pianotracker.ui.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.johncole.pianotracker.R

class NewPracticeActivityDialogFragment : DialogFragment(), AdapterView.OnItemSelectedListener {
    private var dialogView: View? = null
    private var technicalWorkTypeSpinner: Spinner? = null
    private var technicalWorkTypeHeading: TextView? = null
    private var bpmSeekBar: SeekBar? = null
    private var bpmTextView: TextView? = null

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = activity?.let {
            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            val inflater = requireActivity().layoutInflater
            dialogView = inflater.inflate(R.layout.dialog_new_practice_activity, null)

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(dialogView)
                // Add action buttons
                .setPositiveButton(R.string.save
                ) { _, _ ->
                    // TODO: Save this dialog's data to the device's SQLite database
                }
                .setNegativeButton(R.string.cancel
                ) { _, _ ->
                    dialog?.cancel()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")

        setUpAdapter(dialogView!!)
        technicalWorkTypeSpinner = dialogView!!.findViewById(R.id.spinner_select_technical_work_type)
        technicalWorkTypeHeading = dialogView!!.findViewById(R.id.txtV_select_technical_work_type)

        bpmTextView = dialogView!!.findViewById(R.id.txtV_bpm_display)
        setUpSeekBar(dialogView!!)

        return dialog
    }

    private fun setUpAdapter(view: View) {
        val parentSpinner: Spinner = view.findViewById(R.id.spinner_select_practice_activity)
        // Create an ArrayAdapter using the string array and a default spinner layout
        context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.practice_type_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                parentSpinner.adapter = adapter
            }
        }

        parentSpinner.onItemSelectedListener = this
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
        // An item was selected. You can retrieve the selected item using
        val selectedItem = parent.getItemAtPosition(pos)
        if (selectedItem.toString() == "Technical Work") {
            technicalWorkTypeSpinner?.visibility = View.VISIBLE
            technicalWorkTypeHeading?.visibility = View.VISIBLE
        } else {
            technicalWorkTypeSpinner?.visibility = View.GONE
            technicalWorkTypeHeading?.visibility = View.GONE
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
    }

    private fun setUpSeekBar(view: View) {
        bpmSeekBar = view.findViewById(R.id.seekBar_bpm)
        bpmTextView = view.findViewById(R.id.txtV_bpm_display)
        var bpmProgress = 20

        bpmTextView?.text = getString(R.string.seek_bar_bpm_display, bpmProgress)

        bpmSeekBar?.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                bpmProgress  = seek.progress
                bpmTextView?.text = getString(R.string.seek_bar_bpm_display, bpmProgress)
            }

            override fun onStartTrackingTouch(seek: SeekBar) {
                // write custom code for progress is started
            }

            override fun onStopTrackingTouch(seek: SeekBar) {
                // write custom code for progress is stopped
            }
        })
    }
}