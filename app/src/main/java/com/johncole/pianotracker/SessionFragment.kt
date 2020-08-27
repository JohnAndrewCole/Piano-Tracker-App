package com.johncole.pianotracker

import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.johncole.pianotracker.adapters.PracticeActivityListAdapter
import com.johncole.pianotracker.databinding.FragmentSessionBinding
import com.johncole.pianotracker.dialogs.DatePickerFragment
import com.johncole.pianotracker.dialogs.TimePickerFragment
import com.johncole.pianotracker.utilities.InjectorUtils
import com.johncole.pianotracker.utilities.TimeInputFilterMinMax
import com.johncole.pianotracker.utilities.convertDateToFormattedString
import com.johncole.pianotracker.utilities.convertTimeToFormattedString
import com.johncole.pianotracker.utilities.setDivider
import com.johncole.pianotracker.viewmodels.SessionViewModel

class SessionFragment : Fragment() {

    private val viewModel: SessionViewModel by navGraphViewModels(R.id.sessionNavigation) {
        InjectorUtils.provideSessionViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSessionBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val adapter = PracticeActivityListAdapter()
        binding.practiceActivityList.let {
            it.layoutManager = LinearLayoutManager(requireContext())
            it.adapter = adapter
            it.setDivider(R.drawable.recycler_view_divider)
        }

        binding.isCreatingSession = true

        val args = SessionFragmentArgs.fromBundle(requireArguments())
        viewModel.sessionId = args.sessionId
        if (args.isViewingSession) {
            setHasOptionsMenu(true)
            viewModel.sessionId = args.sessionId
            viewModel.getSessionById()
            binding.isCreatingSession = false
        }

        //region LiveData Observers

        viewModel.practiceActivities.observe(
            viewLifecycleOwner,
            { result ->
                binding.hasPracticeActivities = !result.isNullOrEmpty()
                adapter.submitList(result)
            }
        )

        viewModel.sessionDate.observe(
            viewLifecycleOwner,
            { newDate ->
                binding.sessionDateEditText.editText?.setText(convertDateToFormattedString(newDate))
                binding.hasDateEntered = true
            }
        )

        viewModel.sessionStartTime.observe(
            viewLifecycleOwner,
            { newTime ->
                if (newTime != null) {
                    binding.sessionTimeEditText.editText?.setText(
                        convertTimeToFormattedString(
                            newTime
                        )
                    )
                }
            }
        )

        //endregion

        //region Bindings

        binding.sessionDateEditText.editText?.setOnClickListener {
            DatePickerFragment().show(parentFragmentManager, "datePicker")
        }

        binding.sessionTimeEditText.editText?.setOnClickListener {
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

        binding.btnSaveSession.setOnClickListener {
            viewModel.insertSession()
            view?.findNavController()
                ?.navigate(
                    SessionFragmentDirections.actionSessionFragmentToSessionListFragment()
                )
        }

        binding.btnStartTimer.setOnClickListener {
            viewModel.startTimer()
        }

        //endregion

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (viewModel.sessionId > 0) {
            when (item.title) {
                getString(R.string.delete) -> {
                    viewModel.deleteSession()
                    view?.findNavController()
                        ?.navigate(
                            SessionFragmentDirections.actionSessionFragmentToSessionListFragment()
                        )
                }
                getString(R.string.save) -> {
                    viewModel.updateSession()
                    view?.findNavController()
                        ?.navigate(
                            SessionFragmentDirections.actionSessionFragmentToSessionListFragment()
                        )
                }
            }
        }
        return false
    }
}
