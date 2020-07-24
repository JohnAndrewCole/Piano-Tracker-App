package com.johncole.pianotracker.utilities

import android.content.Context
import com.johncole.pianotracker.data.AppDatabase
import com.johncole.pianotracker.data.PracticeActivityRepository
import com.johncole.pianotracker.data.SessionRepository
import com.johncole.pianotracker.viewmodels.factories.HomeScreensViewModelFactory
import com.johncole.pianotracker.viewmodels.factories.PracticeActivityViewModelFactory
import com.johncole.pianotracker.viewmodels.factories.SessionViewModelFactory

/**
 * Static methods used to inject classes needed for various Activities and Fragments.
 */
object InjectorUtils {

    private fun getSessionRepository(context: Context): SessionRepository {
        return SessionRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).sessionDao()
        )
    }

    private fun getPracticeActivityRepository(context: Context): PracticeActivityRepository {
        return PracticeActivityRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).practiceActivityDao()
        )
    }

    fun provideHomeScreensViewModelFactory(context: Context): HomeScreensViewModelFactory {
        return HomeScreensViewModelFactory(
            getSessionRepository(context),
            getPracticeActivityRepository(context)
        )
    }

    fun provideSessionViewModelFactory(context: Context): SessionViewModelFactory {
        return SessionViewModelFactory(
            getSessionRepository(context),
            getPracticeActivityRepository(context)
        )
    }

    fun providePracticeActivityViewModelFactory(context: Context): PracticeActivityViewModelFactory {
        return PracticeActivityViewModelFactory(
            getPracticeActivityRepository(context)
        )
    }
}
