package com.example.runningtracker.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_LOW
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import com.example.runningtracker.R
import com.example.runningtracker.other.Constant.ACTION_PAUSE_SERVICE
import com.example.runningtracker.other.Constant.ACTION_SHOW_TRACKING_FRAGMENT
import com.example.runningtracker.other.Constant.ACTION_START_RESUME_SERVICE
import com.example.runningtracker.other.Constant.ACTION_STOP_SERVICE
import com.example.runningtracker.other.Constant.NOTIFICATION_CHANEL_ID
import com.example.runningtracker.other.Constant.NOTIFICATION_CHANEL_NAME
import com.example.runningtracker.other.Constant.NOTIFICATION_ID
import com.example.runningtracker.ui.MainActivity
import timber.log.Timber


class TrackingService: LifecycleService() {
    var isFirstRun = true
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        intent?.let {
            when(it.action) {
                ACTION_START_RESUME_SERVICE -> {
                    if (isFirstRun) {
                        startForegroundService()
                        isFirstRun = false
                    } else {
                        Timber.d("resume")
                    }

                }
                ACTION_PAUSE_SERVICE -> {
                    Timber.d("Paused service")
                }
                ACTION_STOP_SERVICE -> {
                    Timber.d("Stopped service")
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)

    }

    private fun startForegroundService() {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager)
        }
        val notification = NotificationCompat.Builder(this, NOTIFICATION_CHANEL_ID)
            .setAutoCancel(false)
            .setOngoing(true)
            .setSmallIcon(R.drawable.baseline_directions_run_24)
            .setContentTitle("Running")
            .setContentText("00:00:00")
            .setContentIntent(getActivityPendingIntent())
            .build()

        startForeground(NOTIFICATION_ID, notification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val channel = NotificationChannel(
            NOTIFICATION_CHANEL_ID,
            NOTIFICATION_CHANEL_NAME,
            IMPORTANCE_LOW
        )
        notificationManager.createNotificationChannel(channel)
    }

    private fun getActivityPendingIntent(): PendingIntent {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S) {
            return PendingIntent.getActivity(
                this,
                0,
                Intent(this, MainActivity::class.java).also {
                    it.action = ACTION_SHOW_TRACKING_FRAGMENT
                },
                FLAG_IMMUTABLE
            )
        }
        return PendingIntent.getActivity(
            this,
            0,
            Intent(this, MainActivity::class.java).also {
                it.action = ACTION_SHOW_TRACKING_FRAGMENT
            },
            FLAG_UPDATE_CURRENT
        )
    }


}