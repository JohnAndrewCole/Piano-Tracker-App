package com.johncole.pianotracker.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.johncole.pianotracker.data.GoalRepository
import com.johncole.pianotracker.viewmodels.GoalViewModel

class GoalViewModelFactory(
    private val goalRepository: GoalRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GoalViewModel(
            goalRepository
        ) as T
    }
}
