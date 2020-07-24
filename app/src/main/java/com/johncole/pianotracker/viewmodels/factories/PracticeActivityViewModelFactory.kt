package com.johncole.pianotracker.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.johncole.pianotracker.data.PracticeActivityRepository
import com.johncole.pianotracker.viewmodels.PracticeActivityViewModel

class PracticeActivityViewModelFactory(
    private val practiceActivityRepository: PracticeActivityRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PracticeActivityViewModel(
            practiceActivityRepository
        ) as T
    }
}