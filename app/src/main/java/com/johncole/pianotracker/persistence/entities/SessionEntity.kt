package com.johncole.pianotracker.persistence.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.OffsetDateTime

@Entity(tableName = "session")
data class SessionEntity(
    @PrimaryKey val _id: Int,
    @ColumnInfo(name = "date") val date: OffsetDateTime?,
    @ColumnInfo(name = "total_length_in_seconds") val totalLengthInSeconds: Int?
)