package com.johncole.pianotracker.data

import androidx.lifecycle.LiveData

class GoalRepository private constructor(private val goalDao: GoalDao) {

    fun getGoalsBySessionId(sessionId: Long): LiveData<List<Goal>> {
        return goalDao.getGoalsBySessionId(sessionId)
    }

    suspend fun getGoalById(practiceActivityId: Long): Goal {
        return goalDao.getGoalById(practiceActivityId)
    }

    suspend fun insertNewGoal(newGoal: Goal) {
        goalDao.insertNewGoal(newGoal)
    }

    suspend fun updateGoal(
        goalId: Long,
        sessionId: String,
        goalCategory: String,
        technicalWorkType: String?,
        key: String?,
        bpm: String?,
        notes: String?
    ) {
        goalDao.updateSession(
            goalId,
            sessionId,
            goalCategory,
            technicalWorkType,
            key,
            bpm,
            notes
        )
    }

    suspend fun deleteGoalById(practiceActivityId: Long) {
        goalDao.deleteGoalById(practiceActivityId)
    }

    suspend fun deleteGoalsBySessionId(sessionId: Long) {
        goalDao.deleteGoalsBySessionId(sessionId)
    }

    suspend fun deleteAllGoals() {
        goalDao.deleteAllGoals()
    }

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: GoalRepository? = null

        fun getInstance(goalDao: GoalDao) =
            instance ?: synchronized(this) {
                instance ?: GoalRepository(goalDao).also { instance = it }
            }
    }
}
