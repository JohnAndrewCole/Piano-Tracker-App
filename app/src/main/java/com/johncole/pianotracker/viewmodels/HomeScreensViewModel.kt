package com.johncole.pianotracker.viewmodels

import androidx.lifecycle.LiveData
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

    // region Live Data

    val sessions: LiveData<List<Session>> =
        sessionRepository.getAllSessions()

    var listOfSessionsBeforeCurrentDate: LiveData<List<Session>> =
        sessionRepository.getAllSessionsBeforePresent(convertLocalDateToEpochDay(LocalDate.now()))

    // endregion

    // endregion

    // region Database Functions

    fun deleteAllRecords() {
        viewModelScope.launch {
            sessionRepository.deleteAllSessions()
            practiceActivityRepository.deleteAllPracticeActivities()
        }
    }

//    fun getAllSessionsBeforePresent() {
//        viewModelScope.launch {
//            listOfSessionsBeforeCurrentDate = sessionRepository.getAllSessionsBeforePresent(
//                convertLocalDateToEpochDay(LocalDate.now())
//            )
//        }
//    }

    // endregion
}