package com.johncole.pianotracker.utilities

import android.content.Context
import com.johncole.pianotracker.data.AppDatabase
import com.johncole.pianotracker.data.GoalRepository
import com.johncole.pianotracker.data.SessionRepository
import com.johncole.pianotracker.viewmodels.factories.GoalViewModelFactory
import com.johncole.pianotracker.viewmodels.factories.HomeScreensViewModelFactory
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

    private fun getGoalRepository(context: Context): GoalRepository {
        return GoalRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).goalDao()
        )
    }

    fun provideHomeScreensViewModelFactory(context: Context): HomeScreensViewModelFactory {
        return HomeScreensViewModelFactory(
            getSessionRepository(context),
            getGoalRepository(context)
        )
    }

    fun provideSessionViewModelFactory(context: Context): SessionViewModelFactory {
        return SessionViewModelFactory(
            getSessionRepository(context),
            getGoalRepository(context)
        )
    }

    fun provideGoalViewModelFactory(context: Context): GoalViewModelFactory {
        return GoalViewModelFactory(
            getGoalRepository(context)
        )
    }
}
