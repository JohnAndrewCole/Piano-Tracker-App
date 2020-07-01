package com.johncole.pianotracker.data

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface SessionDao {
//
//    @Query("SELECT * FROM session")
//    fun getAllSessions(): LiveData<List<Session>>

    @Insert
    suspend fun createNewSession(session: Session): Long
}
