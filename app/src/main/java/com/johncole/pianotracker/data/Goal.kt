package com.johncole.pianotracker.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * [Goal] represents a single defined part of a practice session. It includes
 * information that is useful when tracking past practices, such as dates and time spent
 * on the activity.
 *
 * Declaring the column info allows for the renaming of variables without implementing a
 * database migration, as the column name would not change.
 */
@Entity(
    tableName = "goal",
    indices = [Index("session_id")]
)
data class Goal(
    /**
     * Sets the session ID of the [Goal]. This allows the app to
     * figure out which [Goal]s should be displayed in a [Session].
     * This is a REQUIRED field.
     */
    @ColumnInfo(name = "session_id") var sessionId: String,

    /**
     * Sets the type of [Goal], i.e. Technical Work, Improvisation, etc.
     * This is a REQUIRED field.
     */
    @ColumnInfo(name = "goal_category") var goalCategory: String,

    /**
     * Describes the type of technical work done. This is only required when the
     * practice activity type is set to "Technical Work". Otherwise, it will be
     * null.
     * When the [goalCategory] is set to "Technical Work", this is a REQUIRED
     * type. Otherwise, this field will be set to NULL.
     */
    @ColumnInfo(name = "technical_work_type") var technicalWorkType: String? = null,

    /**
     * Describes the key of the [Goal]. A user will not always be
     * practicing with a certain key in mind and, as such, this is a nullable field.
     * This is an OPTIONAL field.
     */
    @ColumnInfo(name = "key") var key: String? = null,

    /**
     * The BPM of the [Goal]. This is nullable for the same reasoning
     * as making the [key] field nullable.
     * This is an OPTIONAL field.
     */
    @ColumnInfo(name = "bpm") var bpm: String? = null,

    /**
     * Stores any notes that the user records to describe the [Goal].
     * This is an OPTIONAL field.
     */
    @ColumnInfo(name = "notes") var notes: String? = null,

    /**
     * Indicates the length of the [Goal], measured in seconds. This
     * is nullable at first, and will only be set when the user begins timing
     * a [Session].
     * This is an OPTIONAL field.
     */
    @ColumnInfo(name = "length") var length: String? = null
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var goalId: Long = 0
}
