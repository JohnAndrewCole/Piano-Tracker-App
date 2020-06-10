package com.johncole.pianotracker.ui.session

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.room.Room
import com.johncole.pianotracker.models.session.PracticeActivityModel
import com.johncole.pianotracker.persistence.database.PianoTrackerDatabase

class SessionViewModel(application: Application) : AndroidViewModel(application) {
    private val practiceActivityModel: PracticeActivityModel? = null

    private val appDatabase: PianoTrackerDatabase by lazy {
        Room.databaseBuilder(
        application,
        PianoTrackerDatabase::class.java,
        "piano-tracker-database").build()
    }

    private fun insertNewPracticeActivity(practiceActivityModel: PracticeActivityModel) {
        // TODO: Actually insert the data
    }
}