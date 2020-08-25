package com.johncole.pianotracker

import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.utils.EntryXComparator
import com.johncole.pianotracker.databinding.FragmentStatsBinding
import com.johncole.pianotracker.utilities.DateValueFormatter
import com.johncole.pianotracker.utilities.InjectorUtils
import com.johncole.pianotracker.utilities.convertLocalDateToEpochDay
import com.johncole.pianotracker.viewmodels.HomeScreensViewModel
import java.time.LocalDate
import java.util.Collections

class StatsFragment : Fragment() {

    private val viewModel: HomeScreensViewModel by viewModels {
        InjectorUtils.provideHomeScreensViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentStatsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.getSessionsToBeDisplayed()

        viewModel.sessions.observe(
            viewLifecycleOwner,
            { result ->
                binding.hasSessions = result.isNullOrEmpty()
            }
        )

        // Observer for line chart.
        viewModel.sessionsToBeDisplayed.observe(
            viewLifecycleOwner,
            { sessionsBeforeCurrentDate ->

                viewModel.getTimeStatsForCurrentSessions()

                val values = arrayListOf<Entry>()

                Collections.sort(values, EntryXComparator())

                val dataSet = LineDataSet(values, "Time series").apply {
                    color = Color.DKGRAY
                    valueTextColor = Color.RED
                    valueTextSize = 12F
                }

                for (session in sessionsBeforeCurrentDate) {
                    if (session.sessionDuration == null) {
                        values.add(
                            Entry(
                                session.sessionDateTimestamp.toFloat(),
                                0F
                            )
                        )
                    } else {
                        values.add(
                            Entry(
                                session.sessionDateTimestamp.toFloat(),
                                (session.sessionDuration / 60).toFloat()
                            )
                        )
                    }
                }
                dataSet.notifyDataSetChanged()

                binding.overTimeLineChart.apply {

                    legend.isEnabled = false
                    axisRight.isEnabled = false
                    description.text = ""

                    xAxis.apply {
                        valueFormatter = DateValueFormatter()
                        when (viewModel.durationOfStats.value) {
                            "Week" -> {
                                axisMinimum = viewModel.currentDateEpochDay - 7
                                granularity = 1F
                            }
                            "Month" -> {
                                axisMinimum = viewModel.currentDateEpochDay - 31
                                granularity = 5F
                            }
                            "Year" -> {
                                axisMinimum = viewModel.currentDateEpochDay - 365
                                granularity = 30F
                            }
                            "All" -> {
                                axisMinimum = values[0].x
                            }
                        }
                        axisMaximum = convertLocalDateToEpochDay(LocalDate.now()).toFloat()
                        position = XAxis.XAxisPosition.BOTTOM
                        setDrawLabels(true)
                        axisLineColor = Color.BLUE
                    }

                    axisLeft.apply {
                        axisLineColor = Color.BLUE
                        axisMinimum = 0F
                    }

                    when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                        // Night mode is not active, we're using the light theme
                        Configuration.UI_MODE_NIGHT_NO -> {
                            xAxis.textColor = Color.BLACK
                            axisLeft.textColor = Color.BLACK
                        }
                        // Night mode is active, we're using dark theme
                        Configuration.UI_MODE_NIGHT_YES -> {
                            xAxis.textColor = Color.WHITE
                            axisLeft.textColor = Color.WHITE
                        }
                    }

                    data = LineData(dataSet)
                    invalidate()
                }
            }
        )

        // Setting binding for spinner that sets duration over which to view stats.
        binding.spnStatsDuration.let {

            ArrayAdapter.createFromResource(
                requireContext(),
                R.array.stats_duration_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                it.adapter = adapter
            }

            it.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Another interface callback
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    pos: Int,
                    id: Long
                ) {
                    val selectedItem = parent?.getItemAtPosition(pos)
                    viewModel.durationOfStats.value = selectedItem.toString()
                    viewModel.getSessionsToBeDisplayed()
                }
            }
        }

        return binding.root
    }
}
