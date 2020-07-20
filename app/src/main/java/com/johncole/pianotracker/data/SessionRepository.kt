package com.johncole.pianotracker.data

class SessionRepository private constructor(private val sessionDao: SessionDao) {

    fun getAllSessions() = sessionDao.getAllSessions()

    suspend fun getSessionById(sessionId: Long): Session {
        return sessionDao.getSessionById(sessionId)
    }

    suspend fun insertSession(session: Session): Long {
        return sessionDao.insertSession(session)
    }

    suspend fun updateSession(
        sessionId: Long,
        sessionDate: String,
        sessionStartTime: String?,
        sessionGoal: String?,
        sessionDuration: String?
    ) {
        sessionDao.updateSession(
            sessionId,
            sessionDate,
            sessionStartTime,
            sessionGoal,
            sessionDuration
        )
    }

    suspend fun deleteSession(sessionId: Long) {
        sessionDao.deleteBySessionId(sessionId)
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