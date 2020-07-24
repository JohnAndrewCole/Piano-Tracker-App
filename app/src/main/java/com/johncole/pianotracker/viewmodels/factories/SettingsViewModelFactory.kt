package com.johncole.pianotracker.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.johncole.pianotracker.data.PracticeActivityRepository
import com.johncole.pianotracker.data.SessionRepository
import com.johncole.pianotracker.viewmodels.SettingsViewModel

/**
 * Factory for creating a [SettingsViewModel] with a constructor that takes a
 * [SessionRepository] and a [PracticeActivityRepository].
 */
class SettingsViewModelFactory(
    private val sessionRepository: SessionRepository,
    private val practiceActivityRepository: PracticeActivityRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SettingsViewModel(
            sessionRepository,
            practiceActivityRepository
        ) as T
    }
}