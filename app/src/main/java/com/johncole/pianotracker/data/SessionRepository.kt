package com.johncole.pianotracker.data

class SessionRepository private constructor(private val sessionDao: SessionDao) {

    fun getAllSessions() = sessionDao.getAllSessions()

    suspend fun getAllSessionsInRange(startEpochDay: Long, currentEpochDay: Long): List<Session> =
        sessionDao.getAllSessionsInRange(startEpochDay, currentEpochDay)

    suspend fun getSessionById(sessionId: Long): Session = sessionDao.getSessionById(sessionId)

    suspend fun insertSession(session: Session): Long = sessionDao.insertSession(session)

    suspend fun updateSession(
        sessionId: Long,
        sessionDate: String,
        sessionDateTimestamp: Long,
        sessionStartTime: String?,
        sessionGoal: String?,
        sessionDuration: Long?
    ) {
        sessionDao.updateSession(
            sessionId,
            sessionDate,
            sessionDateTimestamp,
            sessionStartTime,
            sessionGoal,
            sessionDuration
        )
    }

    suspend fun deleteSession(sessionId: Long) {
        sessionDao.deleteBySessionId(sessionId)
    }

    suspend fun deleteAllSessions() {
        sessionDao.deleteAllSessions()
    }

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: SessionRepository? = null

        fun getInstance(sessionDao: SessionDao) =
            instance ?: synchronized(this) {
                instance ?: SessionRepository(sessionDao).also { instance = it }
            }
    }
}
