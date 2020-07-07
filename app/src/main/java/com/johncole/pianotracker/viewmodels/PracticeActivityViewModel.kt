package com.johncole.pianotracker.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johncole.pianotracker.data.PracticeActivity
import com.johncole.pianotracker.data.PracticeActivityRepository
import kotlinx.coroutines.launch

class PracticeActivityViewModel(
    private val practiceActivityRepository: PracticeActivityRepository
) : ViewModel() {

    // region Properties

    var sessionId: String = "-1"
    var practiceActivityType: String = ""
    var technicalWorkType: String? = null
    var keySelected: String? = null
    var bpmSelected: Int? = null
    var notes: String? = null

    // endregion

    // region Functions

    fun savePracticeActivity() {

        val newPracticeActivity = PracticeActivity(
            sessionId,
            practiceActivityType,
            technicalWorkType,
            keySelected,
            bpmSelected.toString(),
            notes
        )

        viewModelScope.launch {
            practiceActivityRepository.insertNewPracticeActivity(newPracticeActivity)
        }
    }

    // TODO: Fetch practice activity details
    fun getPracticeActivity() {

    }

    // endregion
}