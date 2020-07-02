package com.johncole.pianotracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.johncole.pianotracker.databinding.FragmentSessionBinding
import com.johncole.pianotracker.dialogs.DatePickerFragment
import com.johncole.pianotracker.dialogs.TimePickerFragment
import com.johncole.pianotracker.utilities.InjectorUtils
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSessionBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        viewModel.sessionDate.observe(viewLifecycleOwner, Observer { newDate ->
            binding.sessionDateEditText.setText(convertDateToFormattedString(newDate))
        })

        viewModel.sessionStartTime.observe(viewLifecycleOwner, Observer { newTime ->
            binding.sessionTimeEditText.setText(convertTimeToFormattedString(newTime))
        })

        viewModel.sessionGoal.observe(viewLifecycleOwner, Observer { goal ->
            viewModel.setSessionGoal(goal)
        })

        binding.sessionHourPicker.let {
            it.minValue = 0
            it.maxValue = 24
        }

        binding.sessionMinutePicker.let {
            it.minValue = 0
            it.maxValue = 59
        }

        binding.sessionDateEditText.setOnClickListener {
            DatePickerFragment().show(parentFragmentManager,"datePicker")
        }

        binding.sessionTimeEditText.setOnClickListener {
            TimePickerFragment().show(parentFragmentManager,"timePicker")
        }

        binding.sessionSaveButton.setOnClickListener {
            viewModel.storeSession()
            // TODO: Validate that the session was successfully saved before navigating back to SessionListViewModel
            view?.findNavController()?.navigate(R.id.action_sessionFragment_to_sessionListFragment)
        }

        val args = SessionFragmentArgs.fromBundle(requireArguments())
        if (args.isViewingSession) {
            viewModel.getSessionById(args.sessionId)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activity?.viewModelStore?.clear()
        _binding = null
    }
}