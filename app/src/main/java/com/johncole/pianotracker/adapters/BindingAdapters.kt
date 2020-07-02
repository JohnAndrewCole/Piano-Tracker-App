package com.johncole.pianotracker.adapters

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.johncole.pianotracker.data.Session
import com.johncole.pianotracker.utilities.convertDateToFormattedString
import com.johncole.pianotracker.utilities.convertTimeToFormattedString
import java.time.LocalDate
import java.time.LocalTime

@BindingAdapter("isGone")
fun bindIsGone(view: View, isGone: Boolean) {
    view.visibility = if (isGone) {
        View.GONE
    } else {
        View.VISIBLE
    }
}

@BindingAdapter("sessionDateString")
fun TextView.setStartDateString(item: Session) {
    text = item.date?.let { convertDateToFormattedString(LocalDate.parse(it)) }
}

@BindingAdapter("sessionTimeString")
fun TextView.setStartTimeString(item: Session) {
    text = item.startTime?.let { convertTimeToFormattedString(LocalTime.parse(it)) }
}

@BindingAdapter("sessionGoalString")
fun TextView.setGoalString(item: Session) {
    text = item.sessionGoal
}