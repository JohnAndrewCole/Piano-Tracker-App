package com.johncole.pianotracker.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johncole.pianotracker.data.Goal
import com.johncole.pianotracker.data.GoalRepository
import com.johncole.pianotracker.data.Session
import com.johncole.pianotracker.data.SessionRepository
import com.johncole.pianotracker.utilities.Timer
import com.johncole.pianotracker.utilities.convertHoursAndMinutesToDurationLong
import com.johncole.pianotracker.utilities.convertLocalDateToEpochDay
import com.johncole.pianotracker.utilities.convertLongDurationToHours
import com.johncole.pianotracker.utilities.convertLongDurationToMinutes
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime

class SessionViewModel(
    private val sessionRepository: SessionRepository,
    private val goalRepository: GoalRepository
) : ViewModel() {

    //region Properties

    private val timer = Timer()
    var sessionId: Long = 0

    //endregion

    //region LiveData

    val goals: LiveData<List<Goal>>
        get() {
            return goalRepository.getGoalsBySessionId(sessionId)
        }

    private val _sessionDate = MutableLiveData<LocalDate>()
    val sessionDate: LiveData<LocalDate>
        get() = _sessionDate

    private val _sessionStartTime = MutableLiveData<LocalTime>()
    val sessionStartTime: LiveData<LocalTime>
        get() = _sessionStartTime

    private val _sessionNotes = MutableLiveData<String>()
    val sessionNotes: MutableLiveData<String>
        get() = _sessionNotes

    private val _sessionHours = MutableLiveData<String>()
    val sessionHours: MutableLiveData<String>
        get() = _sessionHours

    private val _sessionMinutes = MutableLiveData<String>()
    val sessionMinutes: MutableLiveData<String>
        get() = _sessionMinutes

    //endregion

    //region Functions

    fun startTimer() {
        Log.d("SessionViewModel", "Timer has been started.")
        timer.start()
    }

    fun stopTimer() {
        timer.stop()
        Log.d(
            "SessionViewModel",
            "Timer has been stopped at a length of ${timer.getElapsedTimeSecs()}."
        )
    }

    //region Property-setting Functions

    fun setDate(date: LocalDate) {
        _sessionDate.value = date
    }

    fun setStartTime(newTime: LocalTime) {
        _sessionStartTime.value = newTime
    }

    //endregion

    //region Database Functions

    fun getSessionById() {
        viewModelScope.launch {
            val session = sessionRepository.getSessionById(sessionId)
            _sessionDate.value = LocalDate.parse(session.date)
            _sessionStartTime.value =
                if (session.startTime != null) LocalTime.parse(session.startTime) else null
            session.sessionNotes?.let { _sessionNotes.value = it }
            session.sessionDuration?.let {
                _sessionHours.value = convertLongDurationToHours(it)
                _sessionMinutes.value = convertLongDurationToMinutes(it)
            }
        }
    }

    fun insertSession() {

        val newSessionStartTime =
            if (sessionStartTime.value != null) sessionStartTime.value.toString() else null

        val session = Session(
            sessionDate.value.toString(),
            convertLocalDateToEpochDay(sessionDate.value!!),
            newSessionStartTime,
            sessionNotes.value,
            convertHoursAndMinutesToDurationLong(sessionHours.value, sessionMinutes.value)
        )

        viewModelScope.launch {
            sessionRepository.insertSession(session)
        }
    }

    fun updateSession() {

        val newSessionStartTime =
            if (sessionStartTime.value != null) sessionStartTime.value.toString() else null

        viewModelScope.launch {
            sessionRepository.updateSession(
                sessionId,
                sessionDate.value.toString(),
                convertLocalDateToEpochDay(sessionDate.value!!),
                newSessionStartTime,
                sessionNotes.value,
                convertHoursAndMinutesToDurationLong(sessionHours.value, sessionMinutes.value)
            )
        }
    }

    fun deleteSession() {
        viewModelScope.launch {
            sessionRepository.deleteSession(sessionId)
            goalRepository.deleteGoalsBySessionId(sessionId)
        }
    }

    //endregion

    //endregion
}
