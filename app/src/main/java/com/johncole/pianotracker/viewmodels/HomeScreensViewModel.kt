package com.johncole.pianotracker.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johncole.pianotracker.data.PracticeActivityRepository
import com.johncole.pianotracker.data.Session
import com.johncole.pianotracker.data.SessionRepository
import com.johncole.pianotracker.utilities.convertLocalDateToEpochDay
import kotlinx.coroutines.launch
import java.time.LocalDate

class HomeScreensViewModel(
    private val sessionRepository: SessionRepository,
    private val practiceActivityRepository: PracticeActivityRepository
) : ViewModel() {

    // region Properties

    val currentDateEpochDay: Float
        get() {
            return convertLocalDateToEpochDay(LocalDate.now()).toFloat()
        }

    // region Live Data

    val sessions: LiveData<List<Session>> =
        sessionRepository.getAllSessions()

    private val _sessionsToBeDisplayed = MutableLiveData<List<Session>>()
    val sessionsToBeDisplayed: LiveData<List<Session>>
        get() = _sessionsToBeDisplayed

    private val _durationOfStats = MutableLiveData<String>()
    val durationOfStats: MutableLiveData<String>
        get() = _durationOfStats

    // endregion

    // endregion

    // region Functions

    // region Database Functions

    fun deleteAllRecords() {
        viewModelScope.launch {
            sessionRepository.deleteAllSessions()
            practiceActivityRepository.deleteAllPracticeActivities()
        }
    }

    fun getSessionsToBeDisplayed() {
        viewModelScope.launch {
            val currentEpochDay = convertLocalDateToEpochDay(LocalDate.now())
            val startEpochDay = when (durationOfStats.value) {
                "Week" -> currentEpochDay - 7
                "Month" -> currentEpochDay - 31
                "Year" -> currentEpochDay - 365
                "All" -> 0
                // Setting this to the same as week because the spinner that sets this value
                // defaults to the "Week" option.
                else -> currentEpochDay - 7
            }
            _sessionsToBeDisplayed.value =
                sessionRepository.getAllSessionsInRange(startEpochDay, currentEpochDay)
        }
    }

    // endregion

    // endregion
}