package com.johncole.pianotracker.utilities

import android.text.InputFilter
import android.text.Spanned
import com.johncole.pianotracker.SessionFragment
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

/**
 * Convert a LocalDate into a string format
 *
 * @param date the date to be converted
 * @return returns a string
 *
 * Various formats can be found here:
 * https://developer.android.com/reference/java/time/format/DateTimeFormatter#patterns
 */
fun convertDateToFormattedString(date: LocalDate): String {
    val formatter = DateTimeFormatter.ofPattern("d MMM uuuu")
    return date.format(formatter)
}

/**
 * Convert a LocalTime into a string format
 *
 * @param time the time to be converted
 * @return returns a string
 *
 * Various formats can be found here:
 * https://developer.android.com/reference/java/time/format/DateTimeFormatter#patterns
 */
fun convertTimeToFormattedString(time: LocalTime): String {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    return time.format(formatter)
}

/**
 * This converts a LocalDate into a UnixTimestamp
 */
fun convertLocalDateToUnixTimestamp(date: LocalDate): Long {
    val zoneId = ZoneId.systemDefault() // or: ZoneId.of("Europe/Oslo")
    return date.atStartOfDay(zoneId).toEpochSecond()
}

// TODO: Figure out whether you need this for times, i.e.
//  Do you need to retrieve times for any data visualisation?

/**
 * This converts a LocalDateTime into a UnixTimestamp
 */
//fun convertLocalDateTimeToUnixTimestamp(time: LocalDateTime): Long {
//    val zoneId = ZoneId.systemDefault() // or: ZoneId.of("Europe/Oslo")
//    return time.atZone(zoneId).toEpochSecond()
//}

/**
 * Converts two numbers representing a duration, in hours and minutes, to a string
 * representing the total duration in minutes. This is for database storage.
 * @param hours the hours captured in the hour number picker
 * @param minutes the minutes captured in the minute number picker
 * @return returns a string conversion of the int value of the minutes summed
 * with the hours converted to minutes
 */
fun convertHoursAndMinutesToDurationLong(hours: String?, minutes: String?): Long {
    if (!hours.isNullOrEmpty() && minutes.isNullOrEmpty()) {
        return (hours.toInt() * 60).toLong()
    } else if (hours.isNullOrEmpty() && !minutes.isNullOrEmpty()) {
        return minutes.toLong()
    } else if (!hours.isNullOrEmpty() && !minutes.isNullOrEmpty()) {
        val hoursToMinutes = hours.toInt() * 60
        return (hoursToMinutes + minutes.toInt()).toLong()
    }
    return 0
}

fun convertLongDurationToHours(length: Long): String {
    val hours = length / 60
    if (hours < 1) {
        return 0.toString()
    }
    return hours.toString()
}

fun convertLongDurationToMinutes(length: Long): String {
    return (length.toInt() % 60).toString()
}

/**
 * This class provides the filter used in the [SessionFragment] to limit the user's
 * input to between a minimum and maximum value (so that a number greater than 60
 * minutes isn't possible).
 * Taken from a StackOverflow answer at
 * https://stackoverflow.com/questions/53758285/how-to-set-input-type-and-format-in-edittext-using-kotlin
 */
class TimeInputFilterMinMax(min: Float, max: Float) : InputFilter {
    private var min: Float = 0.0F
    private var max: Float = 0.0F

    init {
        this.min = min
        this.max = max
    }

    override fun filter(
        source: CharSequence,
        start: Int,
        end: Int,
        dest: Spanned,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        try {
            val input = (dest.subSequence(0, dstart).toString() + source + dest.subSequence(
                dend,
                dest.length
            )).toFloat()
            if (isInRange(min, max, input))
                return null
        } catch (nfe: NumberFormatException) {
        }
        return ""
    }

    private fun isInRange(a: Float, b: Float, c: Float): Boolean {
        return if (b > a) c in a..b else c in b..a
    }
}