package com.johncole.pianotracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.johncole.pianotracker.databinding.FragmentSessionBinding
import com.johncole.pianotracker.dialogs.DatePickerFragment
import com.johncole.pianotracker.dialogs.TimePickerFragment
import com.johncole.pianotracker.utilities.InjectorUtils
import com.johncole.pianotracker.utilities.convertDateToFormattedString
import com.johncole.pianotracker.utilities.convertTimeToFormattedString
import com.johncole.pianotracker.viewmodels.SessionViewModel


class SessionFragment : Fragment() {

    private lateinit var binding: FragmentSessionBinding

    private val viewModel: SessionViewModel by activityViewModels {
        InjectorUtils.provideSessionViewModelFactory(requireContext())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSessionBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel

        viewModel.sessionDate.observe(viewLifecycleOwner, Observer { newDate ->
            binding.sessionDateEditText.setText(convertDateToFormattedString(newDate))
        })

        viewModel.sessionStartTime.observe(viewLifecycleOwner, Observer { newTime ->
            binding.sessionTimeEditText.setText(convertTimeToFormattedString(newTime))
        })

        binding.sessionDateEditText.setOnClickListener {
            DatePickerFragment().show(parentFragmentManager,"datePicker")
        }

        binding.sessionTimeEditText.setOnClickListener {
            TimePickerFragment().show(parentFragmentManager,"timePicker")
        }

        binding.sessionSaveButton.setOnClickListener { onSave() }

        return binding.root
    }

    private fun onSave() {
        viewModel.storeSession()
    }
}