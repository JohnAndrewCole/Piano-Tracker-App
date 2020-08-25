package com.johncole.pianotracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.johncole.pianotracker.databinding.FragmentSettingsBinding
import com.johncole.pianotracker.utilities.InjectorUtils
import com.johncole.pianotracker.viewmodels.HomeScreensViewModel

class SettingsFragment : Fragment() {

    private val viewModel: HomeScreensViewModel by viewModels {
        InjectorUtils.provideHomeScreensViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSettingsBinding.inflate(inflater, container, false)

        binding.btnDeleteAllRecords.setOnClickListener {
            viewModel.deleteAllRecords()
            Toast.makeText(
                requireContext(),
                "All records successfully cleared!",
                Toast.LENGTH_SHORT
            ).show()
        }

        return binding.root
    }
}
