package com.johncole.pianotracker.data

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalTime

/**
 * Type converters to allow Room to reference complex data types.
 */
class Converters {
    @TypeConverter
    fun dateFromTimestamp(value: String?): LocalDate? {
        return LocalDate.parse(value)
    }

    @TypeConverter
    fun timeFromTimestamp(value: String?): LocalTime? {
        return LocalTime.parse(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDate?): String? {
        return date.toString()
    }

    @TypeConverter
    fun timeToTimestamp(startTime: LocalTime): String? {
        return startTime.toString()
    }
}
