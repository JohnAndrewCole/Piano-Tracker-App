package com.johncole.pianotracker.data

import androidx.lifecycle.LiveData

class PracticeActivityRepository private constructor(private val practiceActivityDao: PracticeActivityDao) {

    fun getPracticeActivitiesBySessionId(sessionId: Long): LiveData<List<PracticeActivity>> {
        return practiceActivityDao.getPracticeActivitiesBySessionId(sessionId)
    }

    suspend fun insertNewPracticeActivity(newPracticeActivity: PracticeActivity) {
        practiceActivityDao.insertNewPracticeActivity(newPracticeActivity)
    }

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: PracticeActivityRepository? = null

        fun getInstance(practiceActivityDao: PracticeActivityDao) =
            instance ?: synchronized(this) {
                instance ?: PracticeActivityRepository(practiceActivityDao).also { instance = it }
            }
    }
}