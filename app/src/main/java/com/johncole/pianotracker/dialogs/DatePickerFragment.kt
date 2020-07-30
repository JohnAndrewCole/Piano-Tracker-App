package com.johncole.pianotracker.dialogs

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.navigation.navGraphViewModels
import com.johncole.pianotracker.R
import com.johncole.pianotracker.utilities.InjectorUtils
import com.johncole.pianotracker.viewmodels.SessionViewModel
import java.time.LocalDate

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    private val viewModel: SessionViewModel by navGraphViewModels(R.id.sessionNavigation) {
        InjectorUtils.provideSessionViewModelFactory(requireContext())
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val localDate = LocalDate.now()

        // Use the current date as the default date in the picker
        var year = localDate.year
        var month = localDate.monthValue - 1
        var day = localDate.dayOfMonth

        viewModel.sessionDate.value.let {
            if (it != null) {
                year = it.year
                month = it.monthValue - 1
                day = it.dayOfMonth
            }
        }

        return DatePickerDialog(requireContext(), this, year, month, day)
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        view.updateDate(year, month, day)

        viewModel.setDate(LocalDate.of(year, month + 1, day))
    }
}
