package com.johncole.pianotracker.persistence.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.johncole.pianotracker.helpers.typeConverters.OffsetDateTimeTypeConverters
import com.johncole.pianotracker.persistence.dao.PracticeActivityDao
import com.johncole.pianotracker.persistence.dao.SessionDao
import com.johncole.pianotracker.persistence.entities.PracticeActivityEntity
import com.johncole.pianotracker.persistence.entities.SessionEntity

@Database(entities = [SessionEntity::class, PracticeActivityEntity::class], version = 1)
@TypeConverters(OffsetDateTimeTypeConverters::class)
abstract class PianoTrackerDatabase : RoomDatabase() {
    abstract fun sessionDao(): SessionDao
    abstract fun practiceActivityDao(): PracticeActivityDao
}
