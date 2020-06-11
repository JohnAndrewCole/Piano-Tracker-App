package com.johncole.pianotracker.models.session

import java.time.OffsetDateTime

data class SessionModel(val _id: Int?, val date: OffsetDateTime, val totalLengthInSeconds: Int?)