package com.johncole.pianotracker.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.johncole.pianotracker.data.Session
import com.johncole.pianotracker.data.SessionRepository

class SessionListViewModel internal constructor(
    sessionRepository: SessionRepository
) : ViewModel() {
    val sessions: LiveData<List<Session>> =
        sessionRepository.getAllSessions()
}
