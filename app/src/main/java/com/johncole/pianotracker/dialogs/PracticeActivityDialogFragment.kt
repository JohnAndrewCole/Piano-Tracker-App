package com.johncole.pianotracker.dialogs

import android.app.Dialog
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.johncole.pianotracker.R
import com.johncole.pianotracker.databinding.DialogPracticeActivityBinding
import com.johncole.pianotracker.utilities.InjectorUtils
import com.johncole.pianotracker.viewmodels.PracticeActivityViewModel

class PracticeActivityDialogFragment : DialogFragment() {

    private var _binding: DialogPracticeActivityBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: PracticeActivityViewModel by viewModels {
        InjectorUtils.providePracticeActivityViewModelFactory(requireContext())
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        _binding = DialogPracticeActivityBinding.inflate(LayoutInflater.from(context), null, false)
        binding.viewModel = viewModel

        val args = PracticeActivityDialogFragmentArgs.fromBundle(requireArguments())
        viewModel.sessionId = args.sessionId
        if (args.isViewingPracticeActivity) {
            binding.isViewingPracticeActivity = true
            viewModel.practiceActivityId = args.practiceActivityId
            viewModel.getPracticeActivityById()
            binding.btnSavePracticeActivity.isEnabled = true
        }

        binding.isTechnicalWorkNotSelected = false

        val dialog = activity?.let {
            val builder = AlertDialog.Builder(it, R.style.AppTheme_FullScreenDialog)

            builder.setView(binding.root)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")

        // region Bindings

        binding.btnSavePracticeActivity.setOnClickListener {
            if (args.isViewingPracticeActivity) {
                viewModel.updatePracticeActivity()
            } else {
                viewModel.savePracticeActivity()
            }
            findNavController(requireParentFragment())
                .navigate(
                    PracticeActivityDialogFragmentDirections.actionPracticeActivityDialogFragmentToSessionFragment(
                        true,
                        viewModel.sessionId
                    )
                )
        }

        binding.btnDeletePracticeActivity.let {
            it.setOnClickListener {
                viewModel.deleteByPracticeActivityId()
                findNavController(requireParentFragment())
                    .navigate(
                        PracticeActivityDialogFragmentDirections.actionPracticeActivityDialogFragmentToSessionFragment(
                            true,
                            viewModel.sessionId
                        )
                    )
            }
        }

        binding.toolbar.let {
            it.title = if (args.isViewingPracticeActivity) {
                getString(R.string.heading_practice_activity)
            } else {
                getString(R.string.heading_new_practice_activity)
            }

            it.setNavigationOnClickListener {
                dismiss()
            }
        }

        // region Spinner Set-up

        binding.spinnerSelectPracticeActivity.let {

            val adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.practice_type_array,
                R.layout.dropdown_menu_popup_item
            )
            it.setAdapter(adapter)
            it.isFocusableInTouchMode = false
            it.isLongClickable = false
            it.inputType = InputType.TYPE_NULL

            it.onItemClickListener =
                AdapterView.OnItemClickListener { parent, _, pos, _ ->
                    val selectedItem = parent?.getItemAtPosition(pos)
                    viewModel.practiceActivityType.value = selectedItem.toString()
                    binding.isTechnicalWorkNotSelected = selectedItem.toString() == "Technical Work"
                    binding.btnSavePracticeActivity.isEnabled = true
                }
        }

        binding.spinnerSelectTechnicalWorkType.let {

            val adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.technical_work_type_array,
                R.layout.dropdown_menu_popup_item
            )

            it.setAdapter(adapter)
            it.isFocusableInTouchMode = false
            it.isLongClickable = false
            it.inputType = InputType.TYPE_NULL

            it.onItemClickListener =
                AdapterView.OnItemClickListener { parent, _, pos, _ ->
                    val selectedItem = parent?.getItemAtPosition(pos)
                    viewModel.technicalWorkType.value = selectedItem.toString()
                }
        }

        binding.spinnerSelectKey.let {

            val adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.key_array,
                R.layout.dropdown_menu_popup_item
            )

            it.setAdapter(adapter)
            it.isFocusableInTouchMode = false
            it.isLongClickable = false
            it.inputType = InputType.TYPE_NULL

            it.onItemClickListener =
                AdapterView.OnItemClickListener { parent, _, pos, _ ->
                    val selectedItem = parent?.getItemAtPosition(pos)
                    viewModel.keySelected.value = selectedItem.toString()
                }
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

        viewModel.practiceActivityType.observe(
            viewLifecycleOwner,
            { activityType ->
                binding.spinnerSelectPracticeActivity.setText(activityType, false)
                binding.isTechnicalWorkNotSelected = activityType == "Technical Work"
            }
        )

        viewModel.technicalWorkType.observe(
            viewLifecycleOwner,
            { technicalWorkType ->
                binding.spinnerSelectTechnicalWorkType.setText(technicalWorkType, false)
            }
        )

        viewModel.keySelected.observe(
            viewLifecycleOwner,
            { keySelected ->
                binding.spinnerSelectKey.setText(keySelected, false)
            }
        )

        // endregion

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
