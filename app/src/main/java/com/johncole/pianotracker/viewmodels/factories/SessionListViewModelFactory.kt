package com.johncole.pianotracker.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.johncole.pianotracker.data.SessionRepository
import com.johncole.pianotracker.viewmodels.SessionListViewModel

class SessionListViewModelFactory(
    private val sessionRepository: SessionRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SessionListViewModel(
            sessionRepository
        ) as T
    }
}