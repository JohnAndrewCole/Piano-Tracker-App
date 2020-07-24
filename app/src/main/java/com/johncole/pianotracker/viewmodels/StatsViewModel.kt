package com.johncole.pianotracker.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johncole.pianotracker.data.PracticeActivityRepository
import com.johncole.pianotracker.data.Session
import com.johncole.pianotracker.data.SessionRepository
import com.johncole.pianotracker.utilities.convertLocalDateToUnixTimestamp
import kotlinx.coroutines.launch
import java.time.LocalDate

class StatsViewModel(
    private val sessionRepository: SessionRepository,
    private val practiceActivityRepository: PracticeActivityRepository
) : ViewModel() {

    // region Properties

    var listOfSessionsBeforeCurrentDate = listOf<Session>()

    // endregion

    // region Database Functions

    fun getAllSessionsBeforePresent() {
        val currentDate = convertLocalDateToUnixTimestamp(LocalDate.now())
        viewModelScope.launch {
            listOfSessionsBeforeCurrentDate =
                sessionRepository.getAllSessionsBeforePresent(currentDate)
        }
    }

    // endregion
}