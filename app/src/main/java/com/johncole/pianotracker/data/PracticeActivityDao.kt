package com.johncole.pianotracker.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PracticeActivityDao {

    @Query("SELECT * FROM practice_activity WHERE session_id = :sessionId")
    fun getPracticeActivitiesBySessionId(sessionId: Long): LiveData<List<PracticeActivity>>

    @Query("SELECT * FROM practice_activity WHERE id = :practiceActivityId")
    suspend fun getPracticeActivityById(practiceActivityId: Long): PracticeActivity

    @Insert
    suspend fun insertNewPracticeActivity(newPracticeActivity: PracticeActivity)
}
