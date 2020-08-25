package com.johncole.pianotracker.adapters

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.johncole.pianotracker.R
import com.johncole.pianotracker.data.PracticeActivity
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

// region Session List Binding Adapters

@BindingAdapter("sessionDateString")
fun TextView.setStartDateString(item: Session) {
    text = convertDateToFormattedString(LocalDate.parse(item.date))
}

@BindingAdapter("sessionTimeString")
fun TextView.setStartTimeString(item: Session) {
    text = if (item.startTime.isNullOrEmpty()) {
        context.getString(R.string.no_start_time_set)
    } else {
        convertTimeToFormattedString(LocalTime.parse(item.startTime))
    }
}

@BindingAdapter("sessionGoalString")
fun TextView.setGoalString(item: Session) {
    text = if (item.sessionGoal.isNullOrEmpty()) {
        context.getString(R.string.no_goal_set)
    } else {
        item.sessionGoal
    }
}

// endregion

// region Practice Activity List Binding Adapters

@BindingAdapter("practiceActivityName")
fun TextView.setPracticeActivityName(item: PracticeActivity) {
    text = item.practiceActivityType
}

@BindingAdapter("practiceActivityKey")
fun TextView.setPracticeActivityKey(item: PracticeActivity) {
    text = if (item.key.isNullOrEmpty()) {
        context.getString(R.string.no_key_set)
    } else {
        item.key
    }
}

@BindingAdapter("practiceActivityBpm")
fun TextView.setPracticeActivityBpm(item: PracticeActivity) {
    text = if (item.bpm.isNullOrEmpty()) {
        context.getString(R.string.no_bpm_set)
    } else {
        item.bpm
    }
}

// endregion
