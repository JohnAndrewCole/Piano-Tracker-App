package com.johncole.pianotracker.persistence.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.johncole.pianotracker.persistence.entities.SessionEntity
import java.time.OffsetDateTime

@Dao
interface SessionDao {
    @Query("SELECT * FROM session")
    fun getAll(): List<SessionEntity>

    @Query("SELECT * FROM session WHERE _id IN (:sessionIds)")
    fun loadAllByIds(sessionIds: IntArray): List<SessionEntity>

    @Query("SELECT * FROM session WHERE date LIKE :first")
    fun findByName(first: OffsetDateTime): SessionEntity

    // See the SessionEntity class for explanation on DateTime implementation.
    @Query("SELECT * FROM session ORDER BY datetime(date)")
    fun getSessionsOrderedByDate(): List<SessionEntity>

    @Insert
    fun insertAll(vararg users: SessionEntity)

    @Delete
    fun delete(user: SessionEntity)
}
