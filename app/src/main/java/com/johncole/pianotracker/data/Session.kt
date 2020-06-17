package com.johncole.pianotracker.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    tableName = "session",
    indices = [Index("session_date"), Index ("session_length")]
)
data class Session(
    /**
     * Sets the date of the [Session]. This is only set when the user begins a practice session.
     */
    @ColumnInfo(name = "session_date") val date: Calendar? = Calendar.getInstance(),

    /**
     * Indicates the length of the [Session], measured in seconds.
     */
    @ColumnInfo(name = "session_length") val sessionLength: Int? = 0
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var sessionId: Long = 0
}