package com.johncole.pianotracker.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johncole.pianotracker.data.PracticeActivityRepository
import com.johncole.pianotracker.data.SessionRepository
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val sessionRepository: SessionRepository,
    private val practiceActivityRepository: PracticeActivityRepository
) : ViewModel() {

    // region Database Functions

    fun deleteAllRecords() {
        viewModelScope.launch {
            sessionRepository.deleteAllSessions()
            practiceActivityRepository.deleteAllPracticeActivities()
        }
    }

    // endregion
}