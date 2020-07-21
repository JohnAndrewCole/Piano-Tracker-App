package com.johncole.pianotracker

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.johncole.pianotracker.databinding.FragmentStatsBinding
import com.johncole.pianotracker.utilities.InjectorUtils
import com.johncole.pianotracker.viewmodels.StatsViewModel


class StatsFragment : Fragment() {

    private val viewModel: StatsViewModel by viewModels {
        InjectorUtils.provideStatsViewModel(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentStatsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        // region Line Chart

        val lineChart = binding.overTimeLineChart

        val values: ArrayList<Entry> = ArrayList()
        values.add(Entry(1F, 50F))
        values.add(Entry(2F, 100F))

        val dataSet = LineDataSet(values, "ThisIsALabel") // add entries to data set
        dataSet.color = Color.DKGRAY
        dataSet.valueTextColor = Color.RED

        val lineData = LineData(dataSet)
        lineChart.data = lineData
        lineChart.invalidate()

        // endregion

        return binding.root
    }
}