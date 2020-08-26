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

    private lateinit var _practiceActivity: PracticeActivity

    var practiceActivityId: Long = 0

    var sessionId: Long = 0

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

    // region Database Functions

    fun savePracticeActivity() {

        val newPracticeActivity = PracticeActivity(
            sessionId.toString(),
            practiceActivityType.value!!,
            technicalWorkType.value,
            keySelected.value,
            bpmSelected.value,
            notes.value
        )

        viewModelScope.launch {
            practiceActivityRepository.insertNewPracticeActivity(newPracticeActivity)
        }
    }

    fun getPracticeActivityById() {
        viewModelScope.launch {
            _practiceActivity =
                practiceActivityRepository.getPracticeActivityById(practiceActivityId)

            _practiceActivityType.value = _practiceActivity.practiceActivityType

            if (!_practiceActivity.technicalWorkType.isNullOrEmpty()) {
                _technicalWorkType.value = _practiceActivity.technicalWorkType!!
            }

            if (!_practiceActivity.key.isNullOrEmpty()) {
                _keySelected.value = _practiceActivity.key!!
            }

            if (!_practiceActivity.bpm.isNullOrEmpty()) {
                _bpmSelected.value = _practiceActivity.bpm!!
            }

            if (!_practiceActivity.notes.isNullOrEmpty()) {
                _notes.value = _practiceActivity.notes!!
            }
        }
    }

    fun updatePracticeActivity() {
        viewModelScope.launch {
            practiceActivityRepository.updatePracticeActivity(
                practiceActivityId,
                sessionId.toString(),
                practiceActivityType.value!!,
                technicalWorkType.value,
                keySelected.value,
                bpmSelected.value,
                notes.value
            )
        }
    }

    fun deleteByPracticeActivityId() {
        viewModelScope.launch {
            practiceActivityRepository.deletePracticeActivityById(practiceActivityId)
        }
    }

    // endregion
}
