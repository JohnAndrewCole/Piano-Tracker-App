package com.johncole.pianotracker.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PracticeActivityDao {

    @Query("SELECT * FROM practice_activity WHERE session_id = :sessionId")
    fun getPracticeActivitiesBySessionId(sessionId: Long): LiveData<List<PracticeActivity>>

    @Query("SELECT * FROM practice_activity WHERE id = :practiceActivityId")
    suspend fun getPracticeActivityById(practiceActivityId: Long): PracticeActivity

    @Query("DELETE FROM practice_activity WHERE id = :practiceActivityId")
    suspend fun deletePracticeActivityById(practiceActivityId: Long)

    @Query("DELETE FROM practice_activity WHERE session_id = :sessionId")
    suspend fun deletePracticeActivitiesBySessionId(sessionId: Long)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updatePracticeActivity(practiceActivity: PracticeActivity)

    @Insert
    suspend fun insertNewPracticeActivity(newPracticeActivity: PracticeActivity)
}
