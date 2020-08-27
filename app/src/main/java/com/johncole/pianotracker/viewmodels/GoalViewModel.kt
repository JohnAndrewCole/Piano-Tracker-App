package com.johncole.pianotracker.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johncole.pianotracker.data.Goal
import com.johncole.pianotracker.data.GoalRepository
import kotlinx.coroutines.launch

class GoalViewModel(
    private val goalRepository: GoalRepository
) : ViewModel() {

    // region Properties

    private lateinit var _goal: Goal

    var goalId: Long = 0

    var sessionId: Long = 0

    //region LiveData

    private val _goalCategoryType = MutableLiveData<String>()
    val goalCategoryType: MutableLiveData<String>
        get() = _goalCategoryType

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

    fun saveGoal() {

        val newGoal = Goal(
            sessionId.toString(),
            goalCategoryType.value!!,
            technicalWorkType.value,
            keySelected.value,
            bpmSelected.value,
            notes.value
        )

        viewModelScope.launch {
            goalRepository.insertNewGoal(newGoal)
        }
    }

    fun getGoalById() {
        viewModelScope.launch {
            _goal =
                goalRepository.getGoalById(goalId)

            _goalCategoryType.value = _goal.goalCategory

            if (!_goal.technicalWorkType.isNullOrEmpty()) {
                _technicalWorkType.value = _goal.technicalWorkType!!
            }

            if (!_goal.key.isNullOrEmpty()) {
                _keySelected.value = _goal.key!!
            }

            if (!_goal.bpm.isNullOrEmpty()) {
                _bpmSelected.value = _goal.bpm!!
            }

            if (!_goal.notes.isNullOrEmpty()) {
                _notes.value = _goal.notes!!
            }
        }
    }

    fun updateGoal() {
        viewModelScope.launch {
            goalRepository.updateGoal(
                goalId,
                sessionId.toString(),
                goalCategoryType.value!!,
                technicalWorkType.value,
                keySelected.value,
                bpmSelected.value,
                notes.value
            )
        }
    }

    fun deleteGoalById() {
        viewModelScope.launch {
            goalRepository.deleteGoalById(goalId)
        }
    }

    // endregion
}
