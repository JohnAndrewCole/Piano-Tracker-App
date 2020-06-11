package com.johncole.pianotracker.persistence.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.johncole.pianotracker.models.session.PracticeActivityModel
import com.johncole.pianotracker.persistence.entities.PracticeActivityEntity

@Dao
interface PracticeActivityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = PracticeActivityEntity::class)
    suspend fun insertNewPracticeActivity(newPracticeActivity: PracticeActivityModel)

    @Delete
    suspend fun delete(practiceActivity: PracticeActivityEntity)
}
