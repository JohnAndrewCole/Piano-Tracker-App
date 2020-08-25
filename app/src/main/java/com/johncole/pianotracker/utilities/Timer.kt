package com.johncole.pianotracker.utilities

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