package com.johncole.pianotracker.dialogs

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import androidx.navigation.navGraphViewModels
import com.johncole.pianotracker.R
import com.johncole.pianotracker.utilities.InjectorUtils
import com.johncole.pianotracker.viewmodels.SessionViewModel
import java.time.LocalTime

/**
 * See the [DatePickerFragment] for details on this implementation.
 */
private const val EXTRA_TIME = "date"

class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    private lateinit var localTime: LocalTime

    private val viewModel: SessionViewModel by navGraphViewModels(R.id.sessionNavigation) {
        InjectorUtils.provideSessionViewModelFactory(requireContext())
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        localTime = arguments?.getSerializable(EXTRA_TIME) as LocalTime? ?: LocalTime.now()

        // Use the current time as the default values for the picker
        val hour = localTime.hour
        val minute = localTime.minute

        // Create a new instance of TimePickerDialog and return it
        return TimePickerDialog(activity, this, hour, minute, DateFormat.is24HourFormat(activity))
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {

        view.setIs24HourView(true)
        localTime = LocalTime.of(hourOfDay, minute)
        viewModel.setStartTime(localTime)
    }
}
