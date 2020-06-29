package com.johncole.pianotracker.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.johncole.pianotracker.data.PracticeActivityRepository
import com.johncole.pianotracker.data.SessionRepository

class SessionViewModel (
private val sessionRepository: SessionRepository,
private val practiceActivityRepository: PracticeActivityRepository
) : ViewModel() {

    private val _sessionDate = MutableLiveData<String>()
    val sessionDate: LiveData<String>
        get() = _sessionDate

    private val _sessionStartTime = MutableLiveData<String>()
    val sessionStartTime: LiveData<String>
        get() = _sessionStartTime

    fun setDate(newDate: String) {
        _sessionDate.value = newDate
    }

    fun setStartTime(newTime: String) {
        _sessionStartTime.value = newTime
    }
}