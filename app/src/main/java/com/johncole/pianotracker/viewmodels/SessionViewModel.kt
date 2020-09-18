package com.johncole.pianotracker.viewmodels

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
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.util.concurrent.TimeUnit

class SessionViewModel(
    private val sessionRepository: SessionRepository,
    private val goalRepository: GoalRepository
) : ViewModel() {

    //region Properties

    var sessionId: Long = 0
    private val timer = Timer()

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

    private val _sessionDuration = MutableLiveData<String>()
    val sessionDuration: MutableLiveData<String>
        get() = _sessionDuration

    //endregion

    //region Functions

    //region Property-setting Functions

    fun setDate(date: LocalDate) {
        _sessionDate.value = date
    }

    fun setStartTime(newTime: LocalTime) {
        _sessionStartTime.value = newTime
    }

    private fun repeatFun(): Job {
        return viewModelScope.launch {
            while (isActive) {
                val timeElapsed = timer.getElapsedTime()
                _sessionDuration.value = String.format(
                    "%02d:%02d:%02d",
                    TimeUnit.MILLISECONDS.toHours(timeElapsed),
                    TimeUnit.MILLISECONDS.toMinutes(timeElapsed) % TimeUnit.HOURS.toMinutes(1),
                    TimeUnit.MILLISECONDS.toSeconds(timeElapsed) % TimeUnit.MINUTES.toSeconds(1)
                )
                delay(1000)
            }
        }
    }

    fun startTimer() {
        timer.start()

        val repeatFun = repeatFun()
        // Start the loop
        repeatFun.start()
    }

    fun stopTimer() {
        timer.stop()
        val repeatFun = repeatFun()
        // Cancel the loop
        repeatFun.cancel()

        val timeElapsed = timer.getElapsedTimeSecs()

        val minutesElapsed = TimeUnit.SECONDS.toMinutes(timeElapsed).toString()
        val hoursElapsed = TimeUnit.SECONDS.toHours(timeElapsed).toString()

        _sessionMinutes.value = when (minutesElapsed) {
            "0" -> ""
            else -> minutesElapsed
        }
        _sessionHours.value = when (hoursElapsed) {
            "0" -> ""
            else -> hoursElapsed
        }
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
