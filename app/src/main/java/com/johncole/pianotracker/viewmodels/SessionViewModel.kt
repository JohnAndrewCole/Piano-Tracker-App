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
    val practiceActivities: LiveData<List<PracticeActivity>> = practiceActivityRepository.getPracticeActivitiesBySessionId(sessionId)

    //region LiveData Properties

//    private val _practiceActivities = MutableLiveData<List<PracticeActivity>>()
//    val practiceActivities: LiveData<List<PracticeActivity>>
//        get() = _practiceActivities

    private val _sessionDate = MutableLiveData<LocalDate>()
    val sessionDate: LiveData<LocalDate>
        get() = _sessionDate

    private val _sessionStartTime = MutableLiveData<LocalTime>()
    val sessionStartTime: LiveData<LocalTime>
        get() = _sessionStartTime

    private val _sessionGoal = MutableLiveData<String>()
    val sessionGoal: MutableLiveData<String>
        get() = _sessionGoal

    private val _sessionHours = MutableLiveData<Int>()
    val sessionHours: MutableLiveData<Int>
        get() = _sessionHours

    private val _sessionMinutes = MutableLiveData<Int>()
    val sessionMinutes: MutableLiveData<Int>
        get() = _sessionMinutes

    //endregion

    //endregion

    //region Functions

    //region Property-setting Functions

    fun setSessionHours(newHours: Int) {
        if (newHours != sessionHours.value) {
            _sessionHours.value = newHours
        }
    }

    fun setSessionMinutes(newMinutes: Int) {
        if (newMinutes != sessionMinutes.value) {
            _sessionMinutes.value = newMinutes
        }
    }

    fun setDate(date: LocalDate) {
        _sessionDate.value = date
    }

    fun setStartTime(newTime: LocalTime) {
        _sessionStartTime.value = newTime
    }

    //endregion

    //region Database Functions

//    fun getPracticeActivities(sessionId: Long) {
//        if (sessionId >= 1) {
//                _practiceActivities.value = practiceActivityRepository.getPracticeActivitiesBySessionId(sessionId.toString()).value
//        }
//        return
//    }

    fun getSessionById(sessionId: Long) {
        viewModelScope.launch {
            val session = sessionRepository.getSessionById(sessionId)
            _sessionDate.value = LocalDate.parse(session.date)
            _sessionStartTime.value = LocalTime.parse(session.startTime)
            _sessionGoal.value = session.sessionGoal
            if (session.sessionDuration != null) {
                _sessionHours.value = convertStringDurationToHours(session.sessionDuration)
                _sessionMinutes.value = convertStringDurationToMinutes(session.sessionDuration)
            }
        }
    }

    fun storeSession() {
        val session = Session(
            sessionDate.value.toString(),
            sessionStartTime.value.toString(),
            sessionGoal.value,
            convertDurationToString(sessionHours.value!!, sessionMinutes.value!!)
        )

        viewModelScope.launch {
            sessionRepository.createNewSession(session)
        }
    }

    //endregion

    //endregion
}