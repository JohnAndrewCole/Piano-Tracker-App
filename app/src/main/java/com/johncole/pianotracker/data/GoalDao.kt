package com.johncole.pianotracker.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GoalDao {

    @Query("SELECT * FROM goal WHERE session_id = :sessionId")
    fun getGoalsBySessionId(sessionId: Long): LiveData<List<Goal>>

    @Query("SELECT * FROM goal WHERE id = :goalId")
    suspend fun getGoalById(goalId: Long): Goal

    @Query("DELETE FROM goal WHERE id = :goalId")
    suspend fun deleteGoalById(goalId: Long)

    @Query("DELETE FROM goal WHERE session_id = :sessionId")
    suspend fun deleteGoalsBySessionId(sessionId: Long)

    @Query("UPDATE goal SET session_id = :sessionId, goal_category = :goalCategory, technical_work_type = :technicalWorkType, key = :key, bpm = :bpm, notes = :notes, goal_duration = :goalDuration WHERE id = :goalId")
    suspend fun updateSession(
        goalId: Long,
        sessionId: String,
        goalCategory: String,
        technicalWorkType: String?,
        key: String?,
        bpm: String?,
        notes: String?,
        goalDuration: Long?
    )

    @Insert
    suspend fun insertNewGoal(newGoal: Goal)

    @Query("DELETE FROM goal")
    suspend fun deleteAllGoals()
}
