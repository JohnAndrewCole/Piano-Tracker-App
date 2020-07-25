package com.johncole.pianotracker

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
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

        viewModel.getSessionsToBeDisplayed()

        // Observer for line chart.
        viewModel.sessionsToBeDisplayed.observe(viewLifecycleOwner) { sessionsBeforeCurrentDate ->

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

                xAxis.apply {
                    valueFormatter = DateValueFormatter()
                    position = XAxis.XAxisPosition.BOTTOM
                    setDrawLabels(true)
                    axisLineColor = Color.BLUE

                    axisLeft.apply {
                        axisLineColor = Color.BLUE
//                        axisMinimum = 0f
//                        axisMaximum = 6f
//                        granularity = 0.5f
                    }

                    legend.isEnabled = false
                    axisRight.isEnabled = false
                    description.text = ""

                    data = LineData(dataSet)
                    invalidate()
                }
            }
        }

        // Setting binding for spinner that sets duration over which to view stats.
        binding.spnStatsDuration.let {

            // Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter.createFromResource(
                requireContext(),
                R.array.stats_duration_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                it.adapter = adapter
            }

            it.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Another interface callback
                }

                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    pos: Int,
                    id: Long
                ) {
                    // An item was selected. You can retrieve the selected item using
                    val selectedItem = parent.getItemAtPosition(pos)
                    viewModel.durationOfStats.value = selectedItem.toString()
                    viewModel.getSessionsToBeDisplayed()

                    // TODO: Get these from data! Results will vary according to the duration set by the spinner.

                    val mostPracticedDay = "Tuesday"
                    binding.txtVDayMostPracticedOn.text =
                        getString(R.string.day_of_week_most_practiced_on, mostPracticedDay)

                    val mostPracticedTime = "10:30 AM"
                    binding.txtVTimeMostPracticedAt.text =
                        getString(R.string.time_most_practiced_at, mostPracticedTime)

                    val mostPracticedActivity = "Scales"
                    binding.txtVMostPracticedActivity.text =
                        getString(R.string.favourite_practice_activity, mostPracticedActivity)

                    val averageDurationTime = "1 hour 15 minutes"
                    val forThePast = if (selectedItem.toString() == "All") {
                        "since you started using this app!"
                    } else {
                        "for the past ${selectedItem.toString().toLowerCase(Locale.ROOT)}"
                    }
                    binding.txtVAverageSessionDuration.text = getString(
                        R.string.average_session_time_spent_practicing,
                        averageDurationTime,
                        forThePast
                    )

                    val totalTimeSpentPlaying = "30 hours"
                    binding.txtVTotalTimeSpent.text =
                        getString(R.string.total_time_spent_playing, totalTimeSpentPlaying)

                }
            }
        }


        return binding.root
    }
}