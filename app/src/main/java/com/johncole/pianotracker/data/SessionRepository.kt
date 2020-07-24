package com.johncole.pianotracker.data

class SessionRepository private constructor(private val sessionDao: SessionDao) {

    fun getAllSessions() = sessionDao.getAllSessions()

    fun getAllSessionsBeforePresent(currentDate: Long) =
        sessionDao.getSessionsBeforeCurrentDate(currentDate)

    suspend fun getSessionById(sessionId: Long): Session {
        return sessionDao.getSessionById(sessionId)
    }

    suspend fun insertSession(session: Session): Long {
        return sessionDao.insertSession(session)
    }

    suspend fun updateSession(
        sessionId: Long,
        sessionDate: String,
        sessionDateTimestamp: Long,
        sessionStartTime: String?,
        sessionGoal: String?,
        sessionDuration: Long
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