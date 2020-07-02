package com.johncole.pianotracker.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "session",
    indices = [Index("session_date")]
)
data class Session(
    /**
     * Sets the date of the [Session].
     */
    @ColumnInfo(name = "session_date") val date: String? = null,

    /**
     * Sets the starting time of the [Session].
     */
    @ColumnInfo(name = "session_start_time") val startTime: String? = null,

    /**
     * Indicates an optional goal for the [Session]
     */
    @ColumnInfo(name="session_goal") val sessionGoal: String? = null
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var sessionId: Long = 0
}