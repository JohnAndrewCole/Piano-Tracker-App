package com.johncole.pianotracker.ui.session

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.johncole.pianotracker.models.session.PracticeActivityModel
import com.johncole.pianotracker.models.session.SessionModel
import com.johncole.pianotracker.persistence.database.PianoTrackerDatabase
import kotlinx.coroutines.launch
import java.time.OffsetDateTime

private const val TAG = "SessionViewModel"

class SessionViewModel(application: Application) : AndroidViewModel(application) {
    private val isNewSession = true
    private var sessionId: Int = 0

    private val appDatabase: PianoTrackerDatabase by lazy {
        Room.databaseBuilder(application,
        PianoTrackerDatabase::class.java,
        "piano-tracker-database").build()
    }

    private fun createNewSession(): Int {
        viewModelScope.launch {
            val newEntity = SessionModel(null, OffsetDateTime.now(), null)
            sessionId = appDatabase.sessionDao().createNewSession(newEntity).toInt()
        }

        return sessionId
    }

    fun insertNewPracticeActivity(practiceActivityModel: PracticeActivityModel) {
        if (isNewSession) {
            createNewSession()
        }
        practiceActivityModel.sessionId = sessionId

        viewModelScope.launch {
            Log.d(TAG, "Inserting new practice activity $practiceActivityModel")
            try {
                appDatabase.practiceActivityDao().insertNewPracticeActivity(practiceActivityModel)
            } catch (e: Exception) {
                println(e)
            }
        }
    }
}