package com.johncole.pianotracker.models.session

data class PracticeActivityModel(val _id: Int?,
                                 var sessionId: Int?,
                                 val practiceActivityType: String?,
                                 val technicalWorkType: String?,
                                 val key: String?,
                                 val bpm: Int?,
                                 val notes: String?,
                                 val lengthInSeconds: Int?)