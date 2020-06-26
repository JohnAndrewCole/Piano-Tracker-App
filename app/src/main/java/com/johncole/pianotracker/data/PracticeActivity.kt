package com.johncole.pianotracker.data

import androidx.room.*

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
    foreignKeys = [ForeignKey(entity = Session::class, parentColumns = ["id"], childColumns = ["session_id"])
    ],
    indices = [Index("session_id")]
)
data class PracticeActivity(
    @ColumnInfo(name = "session_id") var sessionId: Long,

    @ColumnInfo(name = "practice_activity_type") var practiceActivityType: String,

    @ColumnInfo(name = "technical_work_type") var technicalWorkType: String?,

    @ColumnInfo(name = "key") var key: String?,

    @ColumnInfo(name = "bpm") var bpm: String?,

    @ColumnInfo(name = "notes") var notes: String?,

    /**
     * Indicates the length of the [PracticeActivity], measured in seconds.
     */
    @ColumnInfo(name = "length") var length: Long? = null
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var practiceActivityId: Long = 0
}