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