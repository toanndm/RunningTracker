package com.example.runningtracker.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RunningDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRunning(running: Running)

    @Delete
    fun deleteRunning(running: Running)

    @Query("SELECT * FROM running_db ORDER BY timestamp DESC")
    fun getAllRunningSortedByDate(): LiveData<List<Running>>

    @Query("SELECT * FROM running_db ORDER BY timeInMillis DESC")
    fun getAllRunningSortedByTime(): LiveData<List<Running>>

    @Query("SELECT * FROM running_db ORDER BY distance DESC")
    fun getAllRunningSortedByDistance(): LiveData<List<Running>>

    @Query("SELECT * FROM running_db ORDER BY calories DESC")
    fun getAllRunningSortedByCalories(): LiveData<List<Running>>

    @Query("SELECT * FROM running_db ORDER BY avgSpeed DESC")
    fun getAllRunningSortedBySpeed(): LiveData<List<Running>>

    @Query("SELECT SUM(calories) FROM running_db")
    fun getTotalCalories(): LiveData<Int>

    @Query("SELECT SUM(timeInMillis) FROM running_db")
    fun getTotalTime(): LiveData<Long>

    @Query("SELECT SUM(distance) FROM running_db")
    fun getTotalDistance(): LiveData<Int>

    @Query("SELECT AVG(avgSpeed) FROM running_db")
    fun getAvgSpeed(): LiveData<Float>
}