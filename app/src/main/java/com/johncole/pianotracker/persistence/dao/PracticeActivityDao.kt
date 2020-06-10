package com.johncole.pianotracker.persistence.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.johncole.pianotracker.persistence.entities.PracticeActivityEntity

@Dao
interface PracticeActivityDao {
    @Query("SELECT * FROM practice_activity")
    fun getAll(): List<PracticeActivityEntity>

    @Query("SELECT * FROM practice_activity WHERE _id IN (:practiceActivityIds)")
    fun loadAllByIds(practiceActivityIds: IntArray): List<PracticeActivityEntity>

    @Insert
    fun insertAll(vararg users: PracticeActivityEntity)

    @Delete
    fun delete(user: PracticeActivityEntity)
}
