package com.johncole.pianotracker.data

class SessionRepository private constructor(private val sessionDao: SessionDao) {

    suspend fun createNewSession(session: Session): Long {
        return sessionDao.createNewSession(session)
    }

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: SessionRepository? = null

        fun getInstance(sessionDao: SessionDao) =
            instance ?: synchronized(this) {
                instance ?: SessionRepository(sessionDao).also { instance = it }
            }
    }
}