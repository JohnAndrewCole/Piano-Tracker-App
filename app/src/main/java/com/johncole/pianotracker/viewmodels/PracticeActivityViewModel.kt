package com.johncole.pianotracker.viewmodels

import androidx.lifecycle.MutableLiveData
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

    //region LiveData

    private val _practiceActivityType = MutableLiveData<String>()
    val practiceActivityType: MutableLiveData<String>
        get() = _practiceActivityType

    private val _technicalWorkType = MutableLiveData<String>()
    val technicalWorkType: MutableLiveData<String>
        get() = _technicalWorkType

    private val _keySelected = MutableLiveData<String>()
    val keySelected: MutableLiveData<String>
        get() = _keySelected

    private val _bpmSelected = MutableLiveData<String>()
    val bpmSelected: MutableLiveData<String>
        get() = _bpmSelected

    private val _notes = MutableLiveData<String>()
    val notes: MutableLiveData<String>
        get() = _notes

    //endregion

    // endregion

    // region Functions

    fun savePracticeActivity() {

        val newPracticeActivity = PracticeActivity(
            sessionId,
            practiceActivityType.value!!,
            technicalWorkType.value!!,
            keySelected.value!!,
            bpmSelected.value!!,
            notes.value!!
        )

        viewModelScope.launch {
            practiceActivityRepository.insertNewPracticeActivity(newPracticeActivity)
        }
    }

    fun getPracticeActivityById(practiceActivityId: Long) {

        viewModelScope.launch {
            val result = practiceActivityRepository.getPracticeActivityById(practiceActivityId)

            _practiceActivityType.value = result.practiceActivityType

            if (!result.technicalWorkType.isNullOrEmpty()) {
                _technicalWorkType.value = result.technicalWorkType
            }

            if (!result.key.isNullOrEmpty()) {
                _keySelected.value = result.key
            }

            if (!result.bpm.isNullOrEmpty()) {
                _bpmSelected.value = result.bpm
            }

            if (!result.notes.isNullOrEmpty()) {
                _notes.value = result.notes
            }
        }
    }

    // endregion
}