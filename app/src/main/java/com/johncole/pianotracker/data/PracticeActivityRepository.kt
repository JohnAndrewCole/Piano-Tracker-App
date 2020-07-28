package com.johncole.pianotracker.data

import androidx.lifecycle.LiveData

class PracticeActivityRepository private constructor(private val practiceActivityDao: PracticeActivityDao) {

    fun getPracticeActivitiesBySessionId(sessionId: Long): LiveData<List<PracticeActivity>> {
        return practiceActivityDao.getPracticeActivitiesBySessionId(sessionId)
    }

    suspend fun getPracticeActivityById(practiceActivityId: Long): PracticeActivity {
        return practiceActivityDao.getPracticeActivityById(practiceActivityId)
    }

    suspend fun insertNewPracticeActivity(newPracticeActivity: PracticeActivity) {
        practiceActivityDao.insertNewPracticeActivity(newPracticeActivity)
    }

    suspend fun updatePracticeActivity(
        practiceActivityId: Long,
        sessionId: String,
        practiceActivityType: String,
        technicalWorkType: String?,
        key: String?,
        bpm: String?,
        notes: String?
    ) {
        practiceActivityDao.updateSession(
            practiceActivityId,
            sessionId,
            practiceActivityType,
            technicalWorkType,
            key,
            bpm,
            notes
        )
    }

    suspend fun deletePracticeActivityById(practiceActivityId: Long) {
        practiceActivityDao.deletePracticeActivityById(practiceActivityId)
    }

    suspend fun deletePracticeActivitiesBySessionId(sessionId: Long) {
        practiceActivityDao.deletePracticeActivitiesBySessionId(sessionId)
    }

    suspend fun deleteAllPracticeActivities() {
        practiceActivityDao.deleteAllPracticeActivities()
    }

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: PracticeActivityRepository? = null

        fun getInstance(practiceActivityDao: PracticeActivityDao) =
            instance ?: synchronized(this) {
                instance ?: PracticeActivityRepository(practiceActivityDao).also { instance = it }
            }
    }
}