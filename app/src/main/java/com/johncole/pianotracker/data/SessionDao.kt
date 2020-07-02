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

    @Insert
    suspend fun createNewSession(session: Session): Long
}
