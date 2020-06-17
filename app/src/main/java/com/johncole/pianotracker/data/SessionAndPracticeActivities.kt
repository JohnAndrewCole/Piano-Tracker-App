package com.johncole.pianotracker.data

import androidx.room.Embedded
import androidx.room.Relation

/**
 * This class captures the relationship between a [Session] and a new [PracticeActivity], which
 * is used by Room to fetch the related entities.
 */
data class SessionAndPracticeActivities(
    @Embedded
    val session: Session,

    @Relation(parentColumn = "id", entityColumn = "session_id")
    val practiceActivities: List<PracticeActivity> = emptyList()
)
