package com.johncole.pianotracker.viewmodels

import android.util.Log
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

    var sessionGoal = MutableLiveData<String>()

    fun setDate(date: LocalDate) {
        _sessionDate.value = date
    }

    fun setStartTime(newTime: LocalTime) {
        _sessionStartTime.value = newTime
    }

    fun storeSession() {

        // Why tf this isn't accepting sessionLength.value????
        val session = Session(sessionDate.value, sessionStartTime.value, sessionGoal.value)

        viewModelScope.launch {
            val id = sessionRepository.createNewSession(session)
            Log.i("SessionViewModel", "Shit has been saved brosef. ID is $id")
        }
    }
}