package com.johncole.pianotracker.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.SeekBar
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.johncole.pianotracker.R
import com.johncole.pianotracker.databinding.DialogNewPracticeActivityBinding
import com.johncole.pianotracker.utilities.InjectorUtils
import com.johncole.pianotracker.viewmodels.PracticeActivityViewModel

class NewPracticeActivityDialogFragment : DialogFragment() {

    private var _binding: DialogNewPracticeActivityBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: PracticeActivityViewModel by activityViewModels {
        InjectorUtils.providePracticeActivityViewModelFactory(requireContext())
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        _binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_new_practice_activity,
            null,
            false
        )

        binding.viewModel = viewModel

        val args = NewPracticeActivityDialogFragmentArgs.fromBundle(requireArguments())
        viewModel.sessionId = args.sessionId.toString()
        if (args.isViewingPracticeActivity) {
            viewModel.getPracticeActivity()
        }

        val dialog = activity?.let {
            val builder = AlertDialog.Builder(it)

            builder.setView(binding.root)
                // Add action buttons
                .setPositiveButton(R.string.save) { _, _ ->
                    view?.findNavController()
                        ?.navigate(
                            NewPracticeActivityDialogFragmentDirections.actionNewPracticeActivityDialogFragmentToSessionFragment(
                                true,
                                args.sessionId,
                                true
                            )
                        )
                    viewModel.savePracticeActivity()
                    dialog?.dismiss()
                }
                .setNegativeButton(R.string.cancel) { _, _ ->
                    dialog?.dismiss()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")

        // region Bindings

        binding.spinnerSelectPracticeActivity.let {

            // Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter.createFromResource(
                requireContext(),
                R.array.practice_type_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                it.adapter = adapter
            }

            it.onItemSelectedListener = object : OnItemSelectedListener {

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Another interface callback
                }

                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    pos: Int,
                    id: Long
                ) {
                    // An item was selected. You can retrieve the selected item using
                    val selectedItem = parent.getItemAtPosition(pos)
                    viewModel.practiceActivityType = selectedItem.toString()
                    if (selectedItem.toString() == "Technical Work") {
                        binding.spinnerSelectTechnicalWorkType.visibility = View.VISIBLE
                        binding.txtVSelectTechnicalWorkType.visibility = View.VISIBLE
                    } else if (selectedItem.toString() != "Technical Work") {
                        binding.spinnerSelectTechnicalWorkType.visibility = View.GONE
                        binding.txtVSelectTechnicalWorkType.visibility = View.GONE
                    }
                }
            }
        }

        binding.spinnerSelectTechnicalWorkType.let {

            // Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter.createFromResource(
                requireContext(),
                R.array.technical_work_type_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                it.adapter = adapter
            }

            it.onItemSelectedListener = object : OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    pos: Int,
                    id: Long
                ) {
                    // An item was selected. You can retrieve the selected item using
                    val selectedItem = parent.getItemAtPosition(pos)
                    viewModel.technicalWorkType = selectedItem.toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Another interface callback
                }
            }

        }

        binding.spinnerSelectKey.let {

            // Create an ArrayAdapter using the string array and a default spinner layout

            ArrayAdapter.createFromResource(
                requireContext(),
                R.array.key_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                it.adapter = adapter
            }

            it.onItemSelectedListener = object : OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    pos: Int,
                    id: Long
                ) {
                    // An item was selected. You can retrieve the selected item using
                    val selectedItem = parent.getItemAtPosition(pos)
                    viewModel.keySelected = selectedItem.toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Another interface callback
                }
            }
        }

        binding.seekBarBpm.let {
            val bpmTextView = binding.txtVBpmDisplay
            var bpmProgress = 20

            bpmTextView.text = getString(R.string.seek_bar_bpm_display, bpmProgress)

            it.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                    bpmProgress = seek.progress
                    viewModel.bpmSelected = bpmProgress
                    bpmTextView.text = getString(R.string.seek_bar_bpm_display, bpmProgress)
                }

                override fun onStartTrackingTouch(seek: SeekBar) {
                    // write custom code for progress is started
                }

                override fun onStopTrackingTouch(seek: SeekBar) {
                    // write custom code for progress is stopped
                }
            })
        }

        //endregion

        return dialog
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}