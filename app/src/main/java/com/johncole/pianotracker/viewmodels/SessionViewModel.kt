package com.johncole.pianotracker.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.johncole.pianotracker.data.PracticeActivityRepository
import com.johncole.pianotracker.data.SessionRepository
import java.time.LocalDate
import java.time.LocalTime

class SessionViewModel (
private val sessionRepository: SessionRepository,
private val practiceActivityRepository: PracticeActivityRepository
) : ViewModel() {

    private val _sessionDate = MutableLiveData<LocalDate>()
    val sessionDate: LiveData<LocalDate>
        get() = _sessionDate

    private val _sessionStartTime = MutableLiveData<LocalTime>()
    val sessionStartTime: LiveData<LocalTime>
        get() = _sessionStartTime

    fun setDate(date: LocalDate) {
        _sessionDate.value = date
    }

    fun setStartTime(newTime: LocalTime) {
        _sessionStartTime.value = newTime
    }

    fun storeSession() {
//        val goal = "The fox jumped"
//        val length = 2490L
//        val session = Session(_sessionDate.value, _sessionStartTime.value, length, goal)
//        viewModelScope.launch {
//            val id = sessionRepository.createNewSession(session)
//            Log.i("SessionViewModel", "Shit has been saved brosef. ID is $id")
//        }
        Log.i("SessionViewModel", "Fix this shit brosef")
    }
}