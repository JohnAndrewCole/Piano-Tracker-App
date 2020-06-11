package com.johncole.pianotracker.persistence.dao

import androidx.room.*
import com.johncole.pianotracker.models.session.SessionModel
import com.johncole.pianotracker.persistence.entities.SessionEntity

@Dao
interface SessionDao {
    // See the SessionEntity class for explanation on DateTime implementation.
    @Query("SELECT * FROM session ORDER BY datetime(date)")
    suspend fun getSessionsOrderedByDate(): List<SessionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = SessionEntity::class)
    suspend fun createNewSession(sessionModel: SessionModel): Long

    @Delete
    suspend fun delete(user: SessionEntity)
}
