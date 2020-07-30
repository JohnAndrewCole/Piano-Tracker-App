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

class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    private val viewModel: SessionViewModel by navGraphViewModels(R.id.sessionNavigation) {
        InjectorUtils.provideSessionViewModelFactory(requireContext())
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val localTime = LocalTime.now()

        // Use the current time as the default values for the picker
        var hour = localTime.hour
        var minute = localTime.minute

        viewModel.sessionStartTime.value.let {
            if (it != null) {
                hour = it.hour
                minute = it.minute
            }
        }

        // Create a new instance of TimePickerDialog and return it
        return TimePickerDialog(activity, this, hour, minute, DateFormat.is24HourFormat(activity))
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        view.setIs24HourView(true)
        viewModel.setStartTime(LocalTime.of(hourOfDay, minute))
    }
}
