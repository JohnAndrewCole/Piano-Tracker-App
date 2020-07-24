package com.johncole.pianotracker.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johncole.pianotracker.data.PracticeActivityRepository
import com.johncole.pianotracker.data.Session
import com.johncole.pianotracker.data.SessionRepository
import com.johncole.pianotracker.utilities.convertLocalDateToUnixTimestamp
import kotlinx.coroutines.launch
import java.time.LocalDate

class HomeScreensViewModel(
    private val sessionRepository: SessionRepository,
    private val practiceActivityRepository: PracticeActivityRepository
) : ViewModel() {

    // region Properties

    val sessions: LiveData<List<Session>> =
        sessionRepository.getAllSessions()

    // region Live Data

    val listOfSessionsBeforeCurrentDate: LiveData<List<Session>>
        get() {
            return sessionRepository.getAllSessionsBeforePresent(
                convertLocalDateToUnixTimestamp(
                    LocalDate.now()
                )
            )
        }

    // endregion

    // endregion

    // region Database Functions

//    fun getAllSessionsBeforePresent() {
//        val currentDate = convertLocalDateToUnixTimestamp(LocalDate.now())
//        viewModelScope.launch {
//            listOfSessionsBeforeCurrentDate = sessionRepository.getAllSessionsBeforePresent(currentDate)
//        }
//    }

    fun deleteAllRecords() {
        viewModelScope.launch {
            sessionRepository.deleteAllSessions()
            practiceActivityRepository.deleteAllPracticeActivities()
        }
    }


    // endregion
}