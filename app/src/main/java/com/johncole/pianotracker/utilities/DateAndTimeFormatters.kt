package com.johncole.pianotracker.utilities

import android.text.InputFilter
import android.text.Spanned
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.johncole.pianotracker.SessionFragment
import com.johncole.pianotracker.StatsFragment
import java.time.LocalDate
import java.time.LocalTime
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
 * This converts a LocalDate into a long representing the number of days since
 * the Epoch.
 */
fun convertLocalDateToEpochDay(date: LocalDate): Long {
    return date.toEpochDay()
}

/**
 * Converts two numbers representing a duration, in hours and minutes, to a string
 * representing the total duration in minutes. This is for database storage.
 * @param hours the hours captured in the hour number picker
 * @param minutes the minutes captured in the minute number picker
 * @return returns a string conversion of the int value of the minutes summed
 * with the hours converted to minutes
 */
fun convertHoursAndMinutesToDurationLong(hours: String?, minutes: String?): Long? {
    if (hours.isNullOrEmpty() && minutes.isNullOrEmpty()) {
        return null
    } else if (!hours.isNullOrEmpty() && minutes.isNullOrEmpty()) {
        return (hours.toInt() * 60).toLong()
    } else if (hours.isNullOrEmpty() && !minutes.isNullOrEmpty()) {
        return minutes.toLong()
    } else if (!hours.isNullOrEmpty() && !minutes.isNullOrEmpty()) {
        val hoursToMinutes = hours.toInt() * 60
        return (hoursToMinutes + minutes.toInt()).toLong()
    }
    return null
}

fun convertLongDurationToHours(length: Long): String {
    val hours = length / 60
    if (hours < 1) {
        return ""
    }
    return hours.toString()
}

fun convertLongDurationToMinutes(length: Long): String {
    val minutes = length % 60
    if (minutes == 0L) {
        return ""
    }
    return minutes.toString()
}

fun convertTotalLongDurationToHoursAndMinutesFormattedString(totalDuration: Long): String {
    var hours = convertLongDurationToHours(totalDuration)
    var minutes = convertLongDurationToMinutes(totalDuration)

    hours = when (hours) {
        "" -> {
            "0 hr"
        }
        else -> {
            "$hours hr"
        }
    }

    minutes = when (minutes) {
        "" -> {
            "0 min"
        }
        else -> {
            "$minutes min"
        }
    }

    return "$hours $minutes"
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
            val input = (
                dest.subSequence(0, dstart).toString() + source + dest.subSequence(
                    dend,
                    dest.length
                )
                ).toFloat()
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

/**
 * This class provides the filter used in the [StatsFragment] to convert a Unix timestamp
 * to a string date-time.
 */
class DateValueFormatter : ValueFormatter() {

    private val formatter = DateTimeFormatter.ofPattern("dd/MM")

    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        val dateLabel = LocalDate.ofEpochDay(value.toLong())

        return dateLabel.format(formatter)
    }

    override fun getPointLabel(entry: Entry?): String {
        val date = entry?.x?.toLong()
        val dateLabel = date?.let { LocalDate.ofEpochDay(it) }

        val lengthOfSession = entry?.y?.toLong()

        if (lengthOfSession != null) {
            val formattedTime = convertTotalLongDurationToHoursAndMinutesFormattedString(lengthOfSession)
            if (dateLabel != null) {
                return "$formattedTime on ${dateLabel.format(formatter)}"
            }
        }

        if (dateLabel != null) {
            return dateLabel.format(formatter)
        }

        return ""
    }
}
