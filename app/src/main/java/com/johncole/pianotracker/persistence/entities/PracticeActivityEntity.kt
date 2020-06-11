package com.johncole.pianotracker.persistence.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "practice_activity")
data class PracticeActivityEntity(
    @PrimaryKey(autoGenerate = true) val _id: Int?,
    val sessionId: Int?,
    val practiceActivityType: String,
    val technicalWorkType: String?,
    val key: String?,
    val bpm: String?,
    val notes: String?,
    val lengthInSeconds: Int?
)