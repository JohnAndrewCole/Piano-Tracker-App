package com.johncole.pianotracker.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.johncole.pianotracker.data.PracticeActivityRepository
import com.johncole.pianotracker.data.SessionRepository

/**
 * Factory for creating a [StatsViewModel] with a constructor that takes a
 * [SessionRepository] and a [PracticeActivityRepository].
 */
class StatsViewModelFactory(
    private val sessionRepository: SessionRepository,
    private val practiceActivityRepository: PracticeActivityRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return StatsViewModel(sessionRepository, practiceActivityRepository) as T
    }
}