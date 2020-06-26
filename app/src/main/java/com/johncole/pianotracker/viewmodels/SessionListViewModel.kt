package com.johncole.pianotracker.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.johncole.pianotracker.data.Session
import com.johncole.pianotracker.data.SessionRepository
import java.text.SimpleDateFormat
import java.util.*

class SessionListViewModel internal constructor(
    sessionRepository: SessionRepository
) : ViewModel() {
    val sessions: LiveData<List<Session>> =
        sessionRepository.getAllSessions()


    val newTestItem: Session = Session(Calendar.getInstance(), 560, "Test Session", "To Test Database")

    companion object {
        private val dateFormat = SimpleDateFormat("MMM d, yyyy", Locale.US)
    }
}
