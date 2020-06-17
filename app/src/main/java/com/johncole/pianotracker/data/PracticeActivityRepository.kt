package com.johncole.pianotracker.data

class PracticeActivityRepository private constructor(private val practiceActivityDao: PracticeActivityDao) {

    suspend fun createNewPracticeActivity(newPracticeActivity: PracticeActivity) {
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