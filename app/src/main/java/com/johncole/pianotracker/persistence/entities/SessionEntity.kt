package com.johncole.pianotracker.persistence.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.OffsetDateTime

/**
 * The OffsetDateTime value in this table, and its accompanying
 * DAO interface methods and OffsetDateTimeTypeConverters, are taken from this article: https://medium.com/androiddevelopers/room-time-2b4cf9672b98
 */

@Entity(tableName = "session")
data class SessionEntity(
    @PrimaryKey val _id: Int,
    @ColumnInfo(name = "date") val date: OffsetDateTime,
    @ColumnInfo(name = "total_length_in_seconds") val totalLengthInSeconds: Int
)