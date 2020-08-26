package com.johncole.pianotracker.utilities

/**
 * I used the code found on this page:
 * https://github.com/cgoldberg/netplot/blob/0cdac581460f70a3d39929ffb5a2635a9eb16320/src/Stopwatch.java
 * to make this class.
 */

class Timer {
    private var startTime: Long = 0
    private var stopTime: Long = 0
    private var running = false

    fun start() {
        startTime = System.currentTimeMillis()
        running = true
    }

    fun stop() {
        stopTime = System.currentTimeMillis()
        running = false
    }

    fun getElapsedTime(): Long {
        return if (running) {
            System.currentTimeMillis() - startTime
        } else stopTime - startTime
    }

    fun getElapsedTimeSecs(): Long {
        return if (running) {
            (System.currentTimeMillis() - startTime) / 1000
        } else (stopTime - startTime) / 1000
    }
}
