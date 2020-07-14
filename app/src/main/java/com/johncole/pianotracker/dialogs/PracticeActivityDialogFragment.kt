package com.johncole.pianotracker.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.SeekBar
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.johncole.pianotracker.R
import com.johncole.pianotracker.databinding.DialogPracticeActivityBinding
import com.johncole.pianotracker.utilities.InjectorUtils
import com.johncole.pianotracker.viewmodels.PracticeActivityViewModel

class PracticeActivityDialogFragment : DialogFragment() {

    private var _binding: DialogPracticeActivityBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: PracticeActivityViewModel by activityViewModels {
        InjectorUtils.providePracticeActivityViewModelFactory(requireContext())
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        _binding = DialogPracticeActivityBinding.inflate(LayoutInflater.from(context), null, false)
        binding.viewModel = viewModel

        val args = PracticeActivityDialogFragmentArgs.fromBundle(requireArguments())
        viewModel.sessionId = args.sessionId
        if (args.isViewingPracticeActivity) {
            viewModel.practiceActivityId = args.practiceActivityId
            viewModel.getPracticeActivityById()
        }

        val dialog = activity?.let {
            val builder = AlertDialog.Builder(it)

            builder.setView(binding.root)
                // Add action buttons
                .setPositiveButton(R.string.save) { _, _ ->
                    if (args.isViewingPracticeActivity) {
                        viewModel.updatePracticeActivity()
                    } else {
                        viewModel.savePracticeActivity()
                    }
                    findNavController(requireParentFragment())
                        .navigate(
                            PracticeActivityDialogFragmentDirections.actionNewPracticeActivityDialogFragmentToSessionFragment(
                                true,
                                viewModel.sessionId
                            )
                        )
                }
                .setNeutralButton("Delete") { _, _ ->
                    viewModel.deleteByPracticeActivityId()
                    findNavController(requireParentFragment())
                        .navigate(
                            PracticeActivityDialogFragmentDirections.actionNewPracticeActivityDialogFragmentToSessionFragment(
                                true,
                                viewModel.sessionId
                            )
                        )
                }
                .setNegativeButton(R.string.cancel) { _, _ ->
                    findNavController(requireParentFragment())
                        .navigate(
                            PracticeActivityDialogFragmentDirections.actionNewPracticeActivityDialogFragmentToSessionFragment(
                                true,
                                viewModel.sessionId
                            )
                        )
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")

        // region Bindings


        // region Spinner Set-up

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
                    viewModel.practiceActivityType.value = selectedItem.toString()
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
                    viewModel.technicalWorkType.value = selectedItem.toString()
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
                    viewModel.keySelected.value = selectedItem.toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Another interface callback
                }
            }
        }

        // endregion

        // region SeekBars

        binding.seekBarBpm.let {
            val bpmTextView = binding.txtVBpmDisplay
            var bpmProgress = 20

            bpmTextView.text = getString(R.string.seek_bar_bpm_display, bpmProgress)

            it.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                    bpmProgress = seek.progress
                    viewModel.bpmSelected.value = bpmProgress.toString()
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

        // endregion

        //endregion

        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.lifecycleOwner = this

        // region ViewModel Observers

        viewModel.practiceActivityType.observe(viewLifecycleOwner, Observer { practiceActivityType ->
            binding.spinnerSelectPracticeActivity.let {
                if (!practiceActivityType.isNullOrEmpty()) {
                    when (practiceActivityType) {
                        "Technical Work" -> it.setSelection(0)
                        "Playing for Fun" -> it.setSelection(1)
                        "Working on a Piece" -> it.setSelection(2)
                        "Improvising" -> it.setSelection(3)
                        "Composing" -> it.setSelection(4)
                        else -> it.setSelection(0)
                    }
                }
            }
        })

        viewModel.technicalWorkType.observe(viewLifecycleOwner, Observer { technicalWorkType ->

            binding.spinnerSelectTechnicalWorkType.let{
                if (!technicalWorkType.isNullOrEmpty()) {
                    when (technicalWorkType) {
                        "Scales" -> it.setSelection(0)
                        "Modes" -> it.setSelection(1)
                        "Chords" -> it.setSelection(2)
                        "Rhythm" -> it.setSelection(3)
                        "Sheet Reading" -> it.setSelection(4)
                        "Ear Training" -> it.setSelection(5)
                        else -> it.setSelection(0)
                    }
                }
            }
        })

        viewModel.keySelected.observe(viewLifecycleOwner, Observer { keySelected ->

            binding.spinnerSelectKey.let {
                if (!keySelected.isNullOrEmpty()) {
                    when (keySelected) {
                        "C" -> it.setSelection(0)
                        "G" -> it.setSelection(1)
                        "D" -> it.setSelection(2)
                        "A" -> it.setSelection(3)
                        "E" -> it.setSelection(4)
                        "B" -> it.setSelection(5)
                        "G♭" -> it.setSelection(6)
                        "D♭" -> it.setSelection(7)
                        "A♭" -> it.setSelection(8)
                        "E♭" -> it.setSelection(9)
                        "B♭" -> it.setSelection(10)
                        "F" -> it.setSelection(11)
                        "NA" -> it.setSelection(12)
                        else -> it.setSelection(12)
                    }
                }
            }
        })

        viewModel.bpmSelected.observe(viewLifecycleOwner, Observer { bpmSelected ->

            if (!bpmSelected.isNullOrEmpty()) {
                binding.seekBarBpm.progress = bpmSelected.toInt()
            }
        })

        // endregion

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}