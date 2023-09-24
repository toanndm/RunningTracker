package com.example.runningtracker.repositories

import com.example.runningtracker.db.Running
import com.example.runningtracker.db.RunningDAO
import javax.inject.Inject

class MainRepository @Inject constructor(
    val runningDAO: RunningDAO
) {
    suspend fun insertRunning(running: Running) = runningDAO.insertRunning(running)

    suspend fun deleteRunning(running: Running) = runningDAO.deleteRunning(running)

    fun getAllRunningSortedByDate() = runningDAO.getAllRunningSortedByDate()

    fun getAllRunningSortedByTime() = runningDAO.getAllRunningSortedByTime()

    fun getAllRunningSortedByDistance() = runningDAO.getAllRunningSortedByDistance()

    fun getAllRunningSortedByCalories() = runningDAO.getAllRunningSortedByCalories()

    fun getAvgSpeed() = runningDAO.getAvgSpeed()

    fun getTotalCalories() = runningDAO.getTotalCalories()

    fun getTotalDistance() = runningDAO.getTotalDistance()

    fun getTotalTime() = runningDAO.getTotalTime()
}