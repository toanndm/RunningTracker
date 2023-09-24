package com.example.runningtracker.db

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "running_db")
data class Running(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var bitmap: Bitmap? = null,
    var timestamp: Long = 0L,
    var timeInMillis: Long = 0L,
    var avgSpeed: Float = 0f,
    var distance: Int = 0,
    var calories: Int = 0
)