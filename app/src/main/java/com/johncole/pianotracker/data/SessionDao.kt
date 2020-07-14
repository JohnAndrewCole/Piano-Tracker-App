package com.johncole.pianotracker.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SessionDao {

    @Query("SELECT * FROM session")
    fun getAllSessions(): LiveData<List<Session>>

    @Query("SELECT * FROM session WHERE id = :sessionId LIMIT 1")
    suspend fun getSessionById(sessionId: Long): Session

    @Query("DELETE FROM session WHERE id = :sessionId")
    suspend fun deleteBySessionId(sessionId: Long)

    @Query("UPDATE session SET session_date = :sessionDate, session_start_time = :sessionStartTime, session_goal = :sessionGoal, session_duration = :sessionDuration WHERE id = :sessionId")
    suspend fun updateSession(
        sessionId: Long,
        sessionDate: String,
        sessionStartTime: String,
        sessionGoal: String,
        sessionDuration: String
    )

    @Insert
    suspend fun insertSession(session: Session): Long
}
