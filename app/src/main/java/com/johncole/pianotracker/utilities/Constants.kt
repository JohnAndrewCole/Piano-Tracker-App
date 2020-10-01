package com.johncole.pianotracker.utilities

/**
 * Constants used throughout the app.
 */

const val DATABASE_NAME = "piano-tracker-database"

// These constants are used when determining the length of time over which to search
// for results from the database.
enum class ResultsRange(val resultsRangeLength: Long) {
    Week(7),
    Month(31),
    Year(365),
    All(0)
}
