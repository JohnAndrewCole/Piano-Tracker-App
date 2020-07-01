package com.johncole.pianotracker.dialogs

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.johncole.pianotracker.utilities.InjectorUtils
import com.johncole.pianotracker.viewmodels.SessionViewModel
import java.time.LocalDate

/**
 * Both this [DatePickerFragment] and the [TimePickerFragment] use the more up-to-date
 * java.time.LocalDate rather than the util Date (Calendar) library. This was built using
 * the example found at this link:
 * https://code.luasoftware.com/tutorials/android/android-date-picker-dialog-with-java-time/
 */

private const val EXTRA_DATE = "date"

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    private lateinit var localDate: LocalDate

    private val viewModel: SessionViewModel by activityViewModels {
        InjectorUtils.provideSessionViewModelFactory(requireContext())
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        localDate = arguments?.getSerializable(EXTRA_DATE) as LocalDate? ?: LocalDate.now()

        // Use the current date as the default date in the picker
        val year = localDate.year
        val month = localDate.monthValue - 1
        val day = localDate.dayOfMonth

        // Create a new instance of DatePickerDialog and return it
        return DatePickerDialog(requireContext(), this, year, month, day)
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        view.updateDate(year, month, day)
        localDate = LocalDate.of(year, month + 1, day)

        viewModel.setDate(localDate)
    }
}
