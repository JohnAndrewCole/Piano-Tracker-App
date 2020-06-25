package com.johncole.pianotracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.johncole.pianotracker.databinding.FragmentStatsBinding
import com.johncole.pianotracker.viewmodels.StatsViewModel

class StatsFragment : Fragment() {

    companion object {
        fun newInstance() = StatsFragment()
    }
    private val viewModel: StatsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentStatsBinding.inflate(inflater, container, false)

        return binding.root
    }
}