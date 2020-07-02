package com.johncole.pianotracker.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johncole.pianotracker.data.PracticeActivityRepository
import com.johncole.pianotracker.data.Session
import com.johncole.pianotracker.data.SessionRepository
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime

class SessionViewModel(
    private val sessionRepository: SessionRepository,
    private val practiceActivityRepository: PracticeActivityRepository
) : ViewModel() {

    private val _sessionDate = MutableLiveData<LocalDate>()
    val sessionDate: LiveData<LocalDate>
        get() = _sessionDate

    private val _sessionStartTime = MutableLiveData<LocalTime>()
    val sessionStartTime: LiveData<LocalTime>
        get() = _sessionStartTime

    private val _sessionGoal = MutableLiveData<String>()
    val sessionGoal: MutableLiveData<String>
        get() = _sessionGoal

    fun setSessionGoal(goal: String) {
        if (goal != sessionGoal.value) {
            _sessionGoal.value = goal
        }
        return
    }

    fun setDate(date: LocalDate) {
        _sessionDate.value = date
    }

    fun setStartTime(newTime: LocalTime) {
        _sessionStartTime.value = newTime
    }

    fun getSessionById(sessionId: Long) {
        viewModelScope.launch {
            val session = sessionRepository.getSessionById(sessionId)
            _sessionDate.value = LocalDate.parse(session.date)
            _sessionStartTime.value = LocalTime.parse(session.startTime)
            _sessionGoal.value = session.sessionGoal
        }
    }

    fun storeSession() {

        val session = Session(
            sessionDate.value.toString(),
            sessionStartTime.value.toString(),
            sessionGoal.value
        )

        viewModelScope.launch {
            sessionRepository.createNewSession(session)
        }
    }

    fun clearBindings() {
        _sessionGoal.value = ""
    }
}