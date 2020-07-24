package com.johncole.pianotracker.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johncole.pianotracker.data.PracticeActivity
import com.johncole.pianotracker.data.PracticeActivityRepository
import com.johncole.pianotracker.data.Session
import com.johncole.pianotracker.data.SessionRepository
import com.johncole.pianotracker.utilities.convertDurationToString
import com.johncole.pianotracker.utilities.convertLocalDateToUnixTimestamp
import com.johncole.pianotracker.utilities.convertStringDurationToHours
import com.johncole.pianotracker.utilities.convertStringDurationToMinutes
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime

class SessionViewModel(
    private val sessionRepository: SessionRepository,
    private val practiceActivityRepository: PracticeActivityRepository
) : ViewModel() {

    //region Properties

    var sessionId: Long = 0

    //endregion

    //region LiveData

    val practiceActivities: LiveData<List<PracticeActivity>>
        get() {
            return practiceActivityRepository.getPracticeActivitiesBySessionId(sessionId)
        }

    private val _sessionDate = MutableLiveData<LocalDate>()
    val sessionDate: LiveData<LocalDate>
        get() = _sessionDate

    private val _sessionStartTime = MutableLiveData<LocalTime>()
    val sessionStartTime: LiveData<LocalTime>
        get() = _sessionStartTime

    private val _sessionGoal = MutableLiveData<String>()
    val sessionGoal: MutableLiveData<String>
        get() = _sessionGoal

    private val _sessionHours = MutableLiveData<String>()
    val sessionHours: MutableLiveData<String>
        get() = _sessionHours

    private val _sessionMinutes = MutableLiveData<String>()
    val sessionMinutes: MutableLiveData<String>
        get() = _sessionMinutes

    //endregion

    //region Functions

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
            _sessionGoal.value = session.sessionGoal
            if (session.sessionDuration != null) {
                _sessionHours.value = convertStringDurationToHours(session.sessionDuration)
                _sessionMinutes.value = convertStringDurationToMinutes(session.sessionDuration)
            }
        }
    }

    fun insertSession() {

        val newSessionStartTime =
            if (sessionStartTime.value != null) sessionStartTime.value.toString() else null

        val session = Session(
            sessionDate.value.toString(),
            convertLocalDateToUnixTimestamp(sessionDate.value!!),
            newSessionStartTime,
            sessionGoal.value,
            convertDurationToString(sessionHours.value, sessionMinutes.value)
        )

        viewModelScope.launch {
            sessionRepository.insertSession(session)
        }
    }

    fun updateSession() {
        viewModelScope.launch {
            sessionRepository.updateSession(
                sessionId,
                sessionDate.value.toString(),
                convertLocalDateToUnixTimestamp(sessionDate.value!!),
                sessionStartTime.value.toString(),
                sessionGoal.value,
                convertDurationToString(sessionHours.value, sessionMinutes.value)
            )
        }
    }

    fun deleteSession() {
        viewModelScope.launch {
            sessionRepository.deleteSession(sessionId)
            practiceActivityRepository.deletePracticeActivitiesBySessionId(sessionId)
        }
    }

    //endregion

    //endregion
}