package com.johncole.pianotracker.data

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface SessionDao {
    @Insert
    suspend fun createNewSession(session: Session): Long
}
