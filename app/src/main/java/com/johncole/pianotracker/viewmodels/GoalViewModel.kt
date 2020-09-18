package com.johncole.pianotracker.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johncole.pianotracker.data.Goal
import com.johncole.pianotracker.data.GoalRepository
import com.johncole.pianotracker.utilities.convertHoursAndMinutesToDurationLong
import com.johncole.pianotracker.utilities.convertLongDurationToHours
import com.johncole.pianotracker.utilities.convertLongDurationToMinutes
import kotlinx.coroutines.launch

class GoalViewModel(
    private val goalRepository: GoalRepository
) : ViewModel() {

    // region Properties

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

    private val _goalHours = MutableLiveData<String>()
    val goalHours: MutableLiveData<String>
        get() = _goalHours

    private val _goalMinutes = MutableLiveData<String>()
    val goalMinutes: MutableLiveData<String>
        get() = _goalMinutes

    //endregion

    // endregion

    // region Database Functions

    fun saveGoal() {

        val newGoal = Goal(
            sessionId.toString(),
            goalCategoryType.value!!,
            technicalWorkType.value,
            keySelected.value,
            bpmSelected.value?.trimStart('0'),
            notes.value,
            convertHoursAndMinutesToDurationLong(goalHours.value, goalMinutes.value)
        )

        viewModelScope.launch {
            goalRepository.insertNewGoal(newGoal)
        }
    }

    fun getGoalById() {
        viewModelScope.launch {
            val goal = goalRepository.getGoalById(goalId)

            _goalCategoryType.value = goal.goalCategory
            goal.technicalWorkType?.let { _technicalWorkType.value = it }
            goal.key?.let { _keySelected.value = it }
            goal.bpm?.let { _bpmSelected.value = it }
            goal.notes?.let { _notes.value = it }
            goal.goalDuration?.let {
                _goalHours.value = convertLongDurationToHours(it)
                _goalMinutes.value = convertLongDurationToMinutes(it)
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
                notes.value,
                convertHoursAndMinutesToDurationLong(goalHours.value, goalMinutes.value)
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
