package com.johncole.pianotracker.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.johncole.pianotracker.data.GoalRepository
import com.johncole.pianotracker.data.SessionRepository
import com.johncole.pianotracker.viewmodels.SessionViewModel

/**
 * Factory for creating a [SessionViewModel] with a constructor that takes a
 * [SessionRepository] and a [GoalRepository].
 */
class SessionViewModelFactory(
    private val sessionRepository: SessionRepository,
    private val goalRepository: GoalRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SessionViewModel(
            sessionRepository,
            goalRepository
        ) as T
    }
}
