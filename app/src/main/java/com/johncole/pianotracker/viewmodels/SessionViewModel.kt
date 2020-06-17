package com.johncole.pianotracker.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johncole.pianotracker.NewPracticeActivityDialogFragment
import com.johncole.pianotracker.SessionFragment
import com.johncole.pianotracker.data.PracticeActivityRepository
import com.johncole.pianotracker.data.Session
import com.johncole.pianotracker.data.SessionRepository
import kotlinx.coroutines.launch
import java.util.*

/**
 * The ViewModel used in [SessionFragment] and [NewPracticeActivityDialogFragment].
 */
class SessionViewModel (
private val sessionRepository: SessionRepository,
private val practiceActivityRepository: PracticeActivityRepository
) : ViewModel() {

    fun createSession() {
        val newSession = Session(Calendar.getInstance(), 560)
        viewModelScope.launch {
            sessionRepository.createNewSession(newSession)
        }
    }

//    fun addPracticeActivityToSession(newPracticeActivity: PracticeActivity) {
//        newPracticeActivity.sessionId = sessionId
//        viewModelScope.launch {
//            practiceActivityRepository.createNewPracticeActivity(newPracticeActivity)
//        }
//    }
}