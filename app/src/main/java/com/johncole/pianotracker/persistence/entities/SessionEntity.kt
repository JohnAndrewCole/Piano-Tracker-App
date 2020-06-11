package com.johncole.pianotracker.persistence.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.OffsetDateTime

/**
 * The OffsetDateTime value in this table, and its accompanying
 * DAO interface methods and OffsetDateTimeTypeConverters, are taken from this article: https://medium.com/androiddevelopers/room-time-2b4cf9672b98
 */

@Entity(tableName = "session")
data class SessionEntity(
    @PrimaryKey(autoGenerate = true) val _id: Int?,
    val date: OffsetDateTime,
    val totalLengthInSeconds: Int?
)