package com.johncole.pianotracker.utilities

import android.content.res.Resources
import com.johncole.pianotracker.R
import java.util.concurrent.TimeUnit

/**
 * These functions create a formatted string that can be set in a TextView.
 */

private val ONE_MINUTE_SECONDS = TimeUnit.SECONDS.convert(1, TimeUnit.MINUTES)
private val ONE_HOUR_SECONDS = TimeUnit.SECONDS.convert(1, TimeUnit.HOURS)

/**
 * Convert a duration of time to a formatted string for display.
 *
 * Examples:
 *
 * 6 seconds
 * 2 minutes
 * 40 hours
 *
 * @param sessionLength the length of time of the session, measured in seconds
 * @param res resources used to load formatted strings
 */
fun convertDurationToFormatted(sessionLength: Long, res: Resources): String {
    return when {
        sessionLength < ONE_MINUTE_SECONDS -> {
            val seconds = TimeUnit.SECONDS.convert(sessionLength, TimeUnit.SECONDS)
            res.getString(R.string.seconds_length, seconds)
        }
        sessionLength < ONE_HOUR_SECONDS -> {
            val minutes = TimeUnit.MINUTES.convert(sessionLength, TimeUnit.SECONDS)
            res.getString(R.string.minutes_length, minutes)
        }
        else -> {
            val hours = TimeUnit.HOURS.convert(sessionLength, TimeUnit.SECONDS)
            res.getString(R.string.hours_length, hours)
        }
    }
}