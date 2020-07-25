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
     * This is a REQUIRED field.
     */
    @ColumnInfo(name = "session_date") val date: String,

    /**
     * The Epoch day of the date of the [Session], measured in days.
     * This is a REQUIRED field.
     */
    @ColumnInfo(name = "session_date_timestamp") val sessionDateTimestamp: Long,

    /**
     * Sets the starting time of the [Session].
     * This is an OPTIONAL field.
     */
    @ColumnInfo(name = "session_start_time") val startTime: String? = null,

    /**
     * Indicates an optional goal for the [Session]
     * This is an OPTIONAL field.
     */
    @ColumnInfo(name = "session_goal") val sessionGoal: String? = null,

    /**
     * The duration of a [Session], measured in minutes.
     * This is a REQUIRED field. If the user supplies
     * no length or records no length, it will be set
     * to 0.
     */
    @ColumnInfo(name = "session_duration") val sessionDuration: Long? = null
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var sessionId: Long = 0
}