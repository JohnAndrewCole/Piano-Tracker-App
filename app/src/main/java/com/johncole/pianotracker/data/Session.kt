package com.johncole.pianotracker.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime

@Entity(
    tableName = "session",
    indices = [Index("session_date"), Index ("session_length")]
)
data class Session(
    /**
     * Sets the date of the [Session].
     */
    @ColumnInfo(name = "session_date") val date: LocalDate? = LocalDate.now(),

    /**
     * Sets the starting time of the [Session].
     */
    @ColumnInfo(name = "session_start_time") val startTime: LocalTime? = LocalTime.now(),

    /**
     * Indicates the length of the [Session], measured in seconds.
     */
    @ColumnInfo(name = "session_length") val sessionLength: Long = 0,

    /**
     * Indicates an optional goal for the [Session]
     */
    @ColumnInfo(name="session_goal") val sessionGoal: String? = null
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var sessionId: Long = 0
}