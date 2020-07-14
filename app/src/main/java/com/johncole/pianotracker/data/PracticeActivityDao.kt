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

    @Query("DELETE FROM practice_activity WHERE id = :practiceActivityId")
    suspend fun deletePracticeActivityById(practiceActivityId: Long)

    @Query("DELETE FROM practice_activity WHERE session_id = :sessionId")
    suspend fun deletePracticeActivitiesBySessionId(sessionId: Long)

    @Query("UPDATE practice_activity SET session_id = :sessionId, practice_activity_type = :practiceActivityType, technical_work_type = :technicalWorkType, key = :key, bpm = :bpm, notes = :notes WHERE id = :practiceActivityId")
    suspend fun updateSession(
        practiceActivityId: Long,
        sessionId: String,
        practiceActivityType: String,
        technicalWorkType: String,
        key: String,
        bpm: String,
        notes: String
    )

    @Insert
    suspend fun insertNewPracticeActivity(newPracticeActivity: PracticeActivity)
}
