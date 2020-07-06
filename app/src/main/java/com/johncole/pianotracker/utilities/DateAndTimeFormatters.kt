package com.johncole.pianotracker.utilities

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
 * Converts two numbers representing a duration, in hours and minutes, to a string
 * representing the total duration in minutes. This is for database storage.
 * @param hours the hours captured in the hour number picker
 * @param minutes the minutes captured in the minute number picker
 * @return returns a string conversion of the int value of the minutes summed
 * with the hours converted to minutes
 */
fun convertDurationToString(hours: Int, minutes: Int): String {
    val hoursToMinutes = hours * 60
    val durationInMinutes = hoursToMinutes + minutes
    return durationInMinutes.toString()
}

/**
 * Takes a string value, representing the duration of a session as measured in minutes,
 * and returns a list of integers, representing the hours and minutes spent.
 * @param length the duration of the session, measured in minutes
 * @return returns a list of integers, where the 0 index is the hours and the 1 index
 * is the minutes spent on the session.
 */
fun convertStringDurationToHours(length: String): Int {
    val hours = length.toInt() / 60
    if (hours < 1) {
        return 0
    }
    return hours
}

fun convertStringDurationToMinutes(length: String): Int {
    return length.toInt() % 60
}