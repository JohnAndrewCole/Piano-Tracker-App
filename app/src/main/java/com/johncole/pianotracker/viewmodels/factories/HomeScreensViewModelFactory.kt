package com.johncole.pianotracker.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.johncole.pianotracker.data.GoalRepository
import com.johncole.pianotracker.data.SessionRepository
import com.johncole.pianotracker.viewmodels.HomeScreensViewModel

/**
 * Factory for creating a [HomeScreensViewModelFactory] with a constructor that takes a
 * [SessionRepository] and a [GoalRepository].
 */
class HomeScreensViewModelFactory(
    private val sessionRepository: SessionRepository,
    private val goalRepository: GoalRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeScreensViewModel(
            sessionRepository,
            goalRepository
        ) as T
    }
}
