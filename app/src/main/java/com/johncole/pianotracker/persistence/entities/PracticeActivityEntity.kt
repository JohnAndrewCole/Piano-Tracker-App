package com.johncole.pianotracker.persistence.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "practice_activity")
data class PracticeActivityEntity(
    @PrimaryKey val _id: Int,
    @ColumnInfo(name = "practice_activity_type") val practiceActivityType: String?,
    @ColumnInfo(name = "technical_work_type") val technicalWorkType: String?,
    @ColumnInfo(name = "key") val key: String?,
    @ColumnInfo(name = "bpm") val bpm: String?,
    @ColumnInfo(name = "notes") val notes: String?,
    @ColumnInfo(name = "length_in_seconds") val lengthInSeconds: Int?
)