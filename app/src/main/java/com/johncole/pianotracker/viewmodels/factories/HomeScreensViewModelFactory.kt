package com.johncole.pianotracker.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.johncole.pianotracker.data.PracticeActivityRepository
import com.johncole.pianotracker.data.SessionRepository
import com.johncole.pianotracker.viewmodels.HomeScreensViewModel

/**
 * Factory for creating a [HomeScreensViewModelFactory] with a constructor that takes a
 * [SessionRepository] and a [PracticeActivityRepository].
 */
class HomeScreensViewModelFactory(
    private val sessionRepository: SessionRepository,
    private val practiceActivityRepository: PracticeActivityRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeScreensViewModel(
            sessionRepository,
            practiceActivityRepository
        ) as T
    }
}
