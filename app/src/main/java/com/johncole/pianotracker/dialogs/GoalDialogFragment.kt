package com.johncole.pianotracker.dialogs

import android.app.Dialog
import android.os.Bundle
import android.text.InputFilter
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
import com.johncole.pianotracker.databinding.DialogGoalBinding
import com.johncole.pianotracker.utilities.InjectorUtils
import com.johncole.pianotracker.utilities.TimeInputFilterMinMax
import com.johncole.pianotracker.viewmodels.GoalViewModel

class GoalDialogFragment : DialogFragment() {

    private var _binding: DialogGoalBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: GoalViewModel by viewModels {
        InjectorUtils.provideGoalViewModelFactory(requireContext())
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        _binding = DialogGoalBinding.inflate(LayoutInflater.from(context), null, false)
        binding.viewModel = viewModel

        val args = GoalDialogFragmentArgs.fromBundle(requireArguments())
        viewModel.sessionId = args.sessionId
        if (args.isViewingGoal) {
            binding.isViewingGoal = true
            viewModel.goalId = args.goalId
            viewModel.getGoalById()
            binding.btnSaveGoal.isEnabled = true
        }

        binding.isTechnicalWorkNotSelected = false

        val dialog = activity?.let {
            val builder = AlertDialog.Builder(it, R.style.AppTheme_FullScreenDialog)

            builder.setView(binding.root)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")

        // region Bindings

        binding.txtEGoalDurationHours.filters = arrayOf<InputFilter>(TimeInputFilterMinMax(0.0F, 24.0F))
        binding.txtEGoalDurationMinutes.filters = arrayOf<InputFilter>(TimeInputFilterMinMax(0.0F, 59.0F))

        binding.btnSaveGoal.setOnClickListener {
            if (args.isViewingGoal) {
                viewModel.updateGoal()
            } else {
                viewModel.saveGoal()
            }
            findNavController(requireParentFragment())
                .navigate(
                    GoalDialogFragmentDirections.actionGoalDialogFragmentToSessionFragment(
                        true,
                        viewModel.sessionId
                    )
                )
        }

        binding.btnDeleteGoal.let {
            it.setOnClickListener {
                viewModel.deleteGoalById()
                findNavController(requireParentFragment())
                    .navigate(
                        GoalDialogFragmentDirections.actionGoalDialogFragmentToSessionFragment(
                            true,
                            viewModel.sessionId
                        )
                    )
            }
        }

        binding.toolbar.let {
            it.title = if (args.isViewingGoal) {
                getString(R.string.heading_goal)
            } else {
                getString(R.string.heading_new_goal)
            }

            it.setNavigationOnClickListener {
                dismiss()
            }
        }

        // region Spinner Set-up

        binding.spinnerSelectGoalCategory.let {

            val adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.goal_category_array,
                R.layout.dropdown_menu_popup_item
            )
            it.setAdapter(adapter)
            it.isFocusableInTouchMode = false
            it.isLongClickable = false
            it.inputType = InputType.TYPE_NULL

            it.onItemClickListener =
                AdapterView.OnItemClickListener { parent, _, pos, _ ->
                    val selectedItem = parent?.getItemAtPosition(pos)
                    viewModel.goalCategoryType.value = selectedItem.toString()
                    binding.isTechnicalWorkNotSelected = selectedItem.toString() == "Technical Work"
                    binding.btnSaveGoal.isEnabled = true
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

        viewModel.goalCategoryType.observe(
            viewLifecycleOwner,
            { activityType ->
                binding.spinnerSelectGoalCategory.setText(activityType, false)
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
