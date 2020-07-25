package com.johncole.pianotracker

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.utils.EntryXComparator
import com.johncole.pianotracker.databinding.FragmentStatsBinding
import com.johncole.pianotracker.utilities.DateValueFormatter
import com.johncole.pianotracker.utilities.InjectorUtils
import com.johncole.pianotracker.viewmodels.HomeScreensViewModel
import java.util.*


class StatsFragment : Fragment() {

    private val viewModel: HomeScreensViewModel by viewModels {
        InjectorUtils.provideHomeScreensViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentStatsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val lineChart = binding.overTimeLineChart

        viewModel.listOfSessionsBeforeCurrentDate.observe(viewLifecycleOwner) { sessionsBeforeCurrentDate ->

            val values = arrayListOf<Entry>()

            Collections.sort(values, EntryXComparator())

            val dataSet = LineDataSet(values, "Time series").apply {
                color = Color.DKGRAY
                valueTextColor = Color.RED
                valueTextSize = 12F
            }

            for (session in sessionsBeforeCurrentDate) {
                values.add(
                    Entry(
                        session.sessionDateTimestamp.toFloat(),
                        (session.sessionDuration / 60).toFloat()
                    )
                )
            }
            dataSet.notifyDataSetChanged()

            lineChart.apply {

                xAxis.apply {
                    valueFormatter = DateValueFormatter()
                    position = XAxis.XAxisPosition.BOTTOM
                    setDrawLabels(true)
                    axisLineColor = Color.BLUE

                    axisLeft.apply {
                        axisLineColor = Color.BLUE
                        axisMinimum = 0f
                        axisMaximum = 6f
                        granularity = 0.5f
                    }

                    legend.isEnabled = false
                    axisRight.isEnabled = false
                    description.text = ""

                    data = LineData(dataSet)
                    invalidate()
                }
            }
        }

        return binding.root
    }
}