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

    @Query("SELECT * FROM session WHERE session_date_timestamp BETWEEN :startEpochDay and :currentEpochDay ORDER BY session_date_timestamp ASC")
    suspend fun getAllSessionsInRange(startEpochDay: Long, currentEpochDay: Long): List<Session>

    @Query("SELECT session_duration FROM session WHERE session_duration IS NOT NULL BETWEEN :startEpochDay and :currentEpochDay")
    suspend fun getDurationsOfAllSessionsInRange(
        startEpochDay: Long,
        currentEpochDay: Long
    ): List<Long>?

    @Query("DELETE FROM session WHERE id = :sessionId")
    suspend fun deleteBySessionId(sessionId: Long)

    @Query("UPDATE session SET session_date = :sessionDate, session_date_timestamp = :sessionDateTimestamp, session_start_time = :sessionStartTime, session_goal = :sessionGoal, session_duration = :sessionDuration WHERE id = :sessionId")
    suspend fun updateSession(
        sessionId: Long,
        sessionDate: String,
        sessionDateTimestamp: Long,
        sessionStartTime: String?,
        sessionGoal: String?,
        sessionDuration: Long?
    )

    @Query("DELETE FROM session")
    suspend fun deleteAllSessions()

    @Insert
    suspend fun insertSession(session: Session): Long
}
