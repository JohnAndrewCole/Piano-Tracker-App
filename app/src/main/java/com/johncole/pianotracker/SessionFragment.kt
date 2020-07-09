package com.johncole.pianotracker

import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.johncole.pianotracker.adapters.PracticeActivityListAdapter
import com.johncole.pianotracker.databinding.FragmentSessionBinding
import com.johncole.pianotracker.dialogs.DatePickerFragment
import com.johncole.pianotracker.dialogs.TimePickerFragment
import com.johncole.pianotracker.utilities.InjectorUtils
import com.johncole.pianotracker.utilities.TimeInputFilterMinMax
import com.johncole.pianotracker.utilities.convertDateToFormattedString
import com.johncole.pianotracker.utilities.convertTimeToFormattedString
import com.johncole.pianotracker.viewmodels.SessionViewModel

class SessionFragment : Fragment() {

    private var _binding: FragmentSessionBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: SessionViewModel by activityViewModels {
        InjectorUtils.provideSessionViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSessionBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val adapter = PracticeActivityListAdapter()
        binding.practiceActivityList.layoutManager = LinearLayoutManager(requireContext())
        binding.practiceActivityList.adapter = adapter

        binding.isCreatingSession = true

        val args = SessionFragmentArgs.fromBundle(requireArguments())
        viewModel.sessionId = args.sessionId
        if (args.isViewingSession) {
            viewModel.sessionId = args.sessionId
            viewModel.getSessionById(args.sessionId)
            binding.isCreatingSession = false
        }

        //region LiveData Observers

        viewModel.practiceActivities.observe(viewLifecycleOwner, Observer { result ->
            binding.hasPracticeActivities = !result.isNullOrEmpty()
            adapter.submitList(result)
        })

        viewModel.sessionDate.observe(viewLifecycleOwner, Observer { newDate ->
            binding.sessionDateEditText.setText(convertDateToFormattedString(newDate))
            binding.hasDateEntered = true
        })

        viewModel.sessionStartTime.observe(viewLifecycleOwner, Observer { newTime ->
            binding.sessionTimeEditText.setText(convertTimeToFormattedString(newTime))
        })

        //endregion

        //region Bindings

        binding.sessionDateEditText.setOnClickListener {
            DatePickerFragment().show(parentFragmentManager, "datePicker")
        }

        binding.sessionTimeEditText.setOnClickListener {
            TimePickerFragment().show(parentFragmentManager, "timePicker")
        }

        binding.btnAddPracticeActivity.setOnClickListener {
            view?.findNavController()
                ?.navigate(
                    SessionFragmentDirections.actionSessionFragmentToPracticeActivityDialogFragment(
                        false,
                        args.sessionId,
                        0
                    )
                )
        }

        binding.txtEHours.filters = arrayOf<InputFilter>(TimeInputFilterMinMax(0.0F, 24.0F))
        binding.txtEMinutes.filters = arrayOf<InputFilter>(TimeInputFilterMinMax(0.0F, 59.0F))

        binding.sessionSaveButton.setOnClickListener {
            viewModel.storeSession()
            view?.findNavController()?.navigate(R.id.action_sessionFragment_to_sessionListFragment)
        }

        //endregion

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activity?.viewModelStore?.clear()
        _binding = null
    }
}