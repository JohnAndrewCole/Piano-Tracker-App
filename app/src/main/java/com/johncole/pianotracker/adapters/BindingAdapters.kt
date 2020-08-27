package com.johncole.pianotracker.adapters

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.johncole.pianotracker.R
import com.johncole.pianotracker.data.Goal
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

@BindingAdapter("sessionIdString")
fun TextView.setSessionIdString(item: Session) {
    text = context.getString(R.string.recycler_text_session_id_hash, item.sessionId.toString())
}

@BindingAdapter("sessionTimeString")
fun TextView.setStartTimeString(item: Session) {
    text = if (item.startTime.isNullOrEmpty()) {
        context.getString(R.string.recycler_text_no_start_time_set)
    } else {
        convertTimeToFormattedString(LocalTime.parse(item.startTime))
    }
}

@BindingAdapter("sessionNotesString")
fun TextView.setNotesString(item: Session) {
    text = if (item.sessionNotes.isNullOrEmpty()) {
        context.getString(R.string.recycler_text_no_notes_added)
    } else {
        item.sessionNotes
    }
}

// endregion

// region Goal List Binding Adapters

@BindingAdapter("goalName")
fun TextView.setGoalName(item: Goal) {
    text = item.goalCategory
}

@BindingAdapter("goalKey")
fun TextView.setGoalKey(item: Goal) {
    text = if (item.key.isNullOrEmpty()) {
        context.getString(R.string.recycler_text_no_key_set)
    } else {
        item.key
    }
}

@BindingAdapter("goalBpm")
fun TextView.setGoalBpm(item: Goal) {
    text = if (item.bpm.isNullOrEmpty()) {
        context.getString(R.string.recycler_text_no_bpm_set)
    } else {
        item.bpm
    }
}

// endregion
