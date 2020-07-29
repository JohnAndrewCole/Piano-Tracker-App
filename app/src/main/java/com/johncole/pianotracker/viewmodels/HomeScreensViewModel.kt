package com.johncole.pianotracker.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johncole.pianotracker.data.PracticeActivityRepository
import com.johncole.pianotracker.data.Session
import com.johncole.pianotracker.data.SessionRepository
import com.johncole.pianotracker.utilities.ResultsRange
import com.johncole.pianotracker.utilities.convertLocalDateToEpochDay
import com.johncole.pianotracker.utilities.convertTotalLongDurationToHoursAndMinutesFormattedString
import kotlinx.coroutines.launch
import java.time.LocalDate

class HomeScreensViewModel(
    private val sessionRepository: SessionRepository,
    private val practiceActivityRepository: PracticeActivityRepository
) : ViewModel() {

    // region Properties

    val currentDateEpochDay: Float
        get() {
            return convertLocalDateToEpochDay(LocalDate.now()).toFloat()
        }

    private val currentEpochDay = convertLocalDateToEpochDay(LocalDate.now())

    // region Live Data

    private val _totalTimeSpentPracticing = MutableLiveData<String>()
    val totalTimeSpentPracticing: MutableLiveData<String>
        get() = _totalTimeSpentPracticing

    private val _averageDurationOfSessions = MutableLiveData<String>()
    val averageDurationOfSessions: MutableLiveData<String>
        get() = _averageDurationOfSessions

    private val _favouritePracticeTime = MutableLiveData<String>()
    val favouritePracticeTime: MutableLiveData<String>
        get() = _favouritePracticeTime

    val sessions: LiveData<List<Session>> =
        sessionRepository.getAllSessions()

    private val _sessionsToBeDisplayed = MutableLiveData<List<Session>>()
    val sessionsToBeDisplayed: LiveData<List<Session>>
        get() = _sessionsToBeDisplayed

    private val _durationOfStats = MutableLiveData<String>()
    val durationOfStats: MutableLiveData<String>
        get() = _durationOfStats

    // endregion

    // endregion

    // region Functions

    fun getTimeStatsForCurrentSessions() {
        viewModelScope.launch {
            var totalDuration = 0L
            var averageDuration = "0 hrs 0 mins"

            if (!sessionsToBeDisplayed.value.isNullOrEmpty()) {

                // For total duration of all sessions
                for (session in sessionsToBeDisplayed.value!!) {
                    if (session.sessionDuration != null) {
                        totalDuration += session.sessionDuration
                    }
                }

                // For average start time of all sessions
                val startTimeMap: List<String?> = sessionsToBeDisplayed.value!!.map { it.startTime }
                val countOfUniqueStartTimes = startTimeMap.groupingBy { it }.eachCount()
                val favouriteStartTime = countOfUniqueStartTimes.maxBy { it.value }?.key.toString()
                _favouritePracticeTime.value = if (favouriteStartTime == "null") {
                    "No Times Set"
                } else {
                    favouriteStartTime
                }

                // For average length of all sessions
                averageDuration =
                    convertTotalLongDurationToHoursAndMinutesFormattedString(totalDuration / sessionsToBeDisplayed.value!!.size)
            }

            _averageDurationOfSessions.value = averageDuration
            _totalTimeSpentPracticing.value =
                convertTotalLongDurationToHoursAndMinutesFormattedString(totalDuration)
        }
    }

    // region Database Functions

    fun deleteAllRecords() {
        viewModelScope.launch {
            sessionRepository.deleteAllSessions()
            practiceActivityRepository.deleteAllPracticeActivities()
        }
    }

    fun getSessionsToBeDisplayed() {
        viewModelScope.launch {
            _sessionsToBeDisplayed.value = when (durationOfStats.value) {
                ResultsRange.Week.name -> {
                    sessionRepository.getAllSessionsInRange(
                        currentEpochDay - ResultsRange.Week.resultsRangeLength,
                        currentEpochDay
                    )
                }
                ResultsRange.Month.name -> {
                    sessionRepository.getAllSessionsInRange(
                        currentEpochDay - ResultsRange.Month.resultsRangeLength,
                        currentEpochDay
                    )
                }
                ResultsRange.Year.name -> {
                    sessionRepository.getAllSessionsInRange(
                        currentEpochDay - ResultsRange.Year.resultsRangeLength,
                        currentEpochDay
                    )
                }
                ResultsRange.All.name -> {
                    sessionRepository.getAllSessionsInRange(
                        ResultsRange.All.resultsRangeLength,
                        currentEpochDay
                    )
                }
                // Setting this to the same as week because the spinner that sets this value
                // defaults to the "Week" option.
                else -> {
                    sessionRepository.getAllSessionsInRange(
                        currentEpochDay - ResultsRange.Week.resultsRangeLength,
                        currentEpochDay
                    )
                }
            }
        }
    }
}

// endregion

// endregion