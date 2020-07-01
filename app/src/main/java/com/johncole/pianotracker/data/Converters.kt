package com.johncole.pianotracker.data

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId

/**
 * Type converters to allow Room to reference complex data types.
 */
class Converters {
    @TypeConverter
    fun dateFromTimestamp(value: Long?): LocalDate? {
        return value?.let { LocalDate.ofEpochDay(it) }
    }

    @TypeConverter
    fun timeFromTimestamp(value: Long?): LocalTime? {
        return value?.let { LocalTime.ofSecondOfDay(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDate?): Long? {
        return date?.atStartOfDay(ZoneId.systemDefault())?.toInstant()?.toEpochMilli()
    }

    @TypeConverter
    fun timeToTimestamp(time: LocalTime): Long? {
        return time.toSecondOfDay().toLong()
    }
}
