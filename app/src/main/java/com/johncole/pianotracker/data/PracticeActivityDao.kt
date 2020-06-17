package com.johncole.pianotracker.data

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface PracticeActivityDao {
    @Insert
    suspend fun insertNewPracticeActivity(newPracticeActivity: PracticeActivity)
}
