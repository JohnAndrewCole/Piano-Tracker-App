package com.johncole.pianotracker.utilities

import android.content.Context
import com.johncole.pianotracker.data.AppDatabase
import com.johncole.pianotracker.data.PracticeActivityRepository
import com.johncole.pianotracker.data.SessionRepository
import com.johncole.pianotracker.viewmodels.SessionListViewModelFactory
import com.johncole.pianotracker.viewmodels.SessionViewModelFactory

/**
 * Static methods used to inject classes needed for various Activities and Fragments.
 */
object InjectorUtils {

    private fun getSessionRepository(context: Context): SessionRepository {
        return SessionRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).sessionDao())
    }

    private fun getPracticeActivityRepository(context: Context): PracticeActivityRepository {
        return PracticeActivityRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).practiceActivityDao())
    }

    fun provideSessionViewModelFactory(context: Context): SessionViewModelFactory {
        return SessionViewModelFactory(getSessionRepository(context), getPracticeActivityRepository(context))
    }

    fun provideSessionListViewModelFactory(context: Context) : SessionListViewModelFactory {
        return SessionListViewModelFactory(getSessionRepository(context))
    }
}
