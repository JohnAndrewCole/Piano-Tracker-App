package com.johncole.pianotracker.persistence.dao

import androidx.room.*
import com.johncole.pianotracker.models.session.PracticeActivityModel
import com.johncole.pianotracker.persistence.entities.PracticeActivityEntity

@Dao
interface PracticeActivityDao {
    @Query("SELECT * FROM practice_activity")
    suspend fun getAll(): List<PracticeActivityEntity>

    @Query("SELECT * FROM practice_activity WHERE _id IN (:practiceActivityIds)")
    suspend fun loadAllByIds(practiceActivityIds: IntArray): List<PracticeActivityEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = PracticeActivityEntity::class)
    suspend fun insertNewPracticeActivity(newPracticeActivity: PracticeActivityModel)

    @Delete
    suspend fun delete(practiceActivity: PracticeActivityEntity)
}
