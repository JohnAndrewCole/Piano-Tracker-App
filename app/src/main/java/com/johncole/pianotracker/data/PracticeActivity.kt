package com.johncole.pianotracker.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * [PracticeActivity] represents a single defined part of a practice session. It includes
 * information that is useful when tracking past practices, such as dates and time spent
 * on the activity.
 *
 * Declaring the column info allows for the renaming of variables without implementing a
 * database migration, as the column name would not change.
 */
@Entity(
    tableName = "practice_activity",
    indices = [Index("session_id")]
)
data class PracticeActivity(
    /**
     * Sets the session ID of the [PracticeActivity].
     * This is a REQUIRED field.
     */
    @ColumnInfo(name = "session_id") var sessionId: String,

    /**
     * Sets the type of [PracticeActivity], i.e. Technical Work, Improvisation, etc.
     * This is a REQUIRED field.
     */
    @ColumnInfo(name = "practice_activity_type") var practiceActivityType: String,

    /**
     * Describes the type of technical work done. This is only required when the
     * practice activity type is set to "Technical Work". Otherwise, it will be
     * null.
     * When the [practiceActivityType] is set to "Technical Work", this is a REQUIRED
     * type. Otherwise, this field will be set to NULL.
     */
    @ColumnInfo(name = "technical_work_type") var technicalWorkType: String? = null,

    /**
     * Describes the key of the [PracticeActivity]. A user will not always be
     * practicing with a certain key in mind and, as such, this is a nullable field.
     * This is an OPTIONAL field.
     */
    @ColumnInfo(name = "key") var key: String? = null,

    /**
     * The BPM of the [PracticeActivity]. This is nullable for the same reasoning
     * as making the [key] field nullable.
     * This is an OPTIONAL field.
     */
    @ColumnInfo(name = "bpm") var bpm: String? = null,

    /**
     * Stores any notes that the user records to describe the [PracticeActivity].
     * This is an OPTIONAL field.
     */
    @ColumnInfo(name = "notes") var notes: String? = null,

    /**
     * Indicates the length of the [PracticeActivity], measured in seconds. This
     * is nullable at first, and will only be set when the user begins timing
     * a [Session].
     * This is an OPTIONAL field.
     */
    @ColumnInfo(name = "length") var length: String? = null
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var practiceActivityId: Long = 0
}