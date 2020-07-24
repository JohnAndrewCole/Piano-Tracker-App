package com.johncole.pianotracker

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.johncole.pianotracker.databinding.FragmentStatsBinding
import com.johncole.pianotracker.utilities.InjectorUtils
import com.johncole.pianotracker.viewmodels.HomeScreensViewModel


class StatsFragment : Fragment() {

    private val viewModel: HomeScreensViewModel by activityViewModels {
        InjectorUtils.provideHomeScreensViewModelFactory(requireContext())
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
        // TODO: Validate that there are sessions before performing this
        for (session in viewModel.sessions.value!!) {
            values.add(
                Entry(
                    session.sessionDateTimestamp.toFloat(),
                    session.sessionDuration.toFloat()
                )
            )
        }

        val dataSet = LineDataSet(values, "Time series")
        dataSet.color = Color.DKGRAY
        dataSet.valueTextColor = Color.RED
        dataSet.valueTextSize = 12F

        lineChart.legend.isEnabled = false
        lineChart.axisRight.isEnabled = false
        lineChart.description.text = ""

        val xAxis = lineChart.xAxis
        xAxis.axisMinimum = 1593561600f
        xAxis.axisMaximum = 1595583235f
        xAxis.position = XAxisPosition.BOTTOM
        xAxis.setDrawLabels(true)
        xAxis.axisLineColor = Color.BLUE

        // TODO: Use this to set dates
        // xAxis.valueFormatter = MyValueFormatter()

        val yAxis = lineChart.axisLeft
        yAxis.axisLineColor = Color.BLUE
        yAxis.axisMinimum = 0f // start at zero
        yAxis.axisMaximum = 6f // the axis maximum is 100
        yAxis.granularity = 0.5f // interval 1


        val lineData = LineData(dataSet)
        lineChart.data = lineData
        lineChart.invalidate()

        // endregion

        return binding.root
    }
}