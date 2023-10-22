package com.example.runningtracker.other

import android.app.PendingIntent
import android.os.Build
import androidx.compose.ui.graphics.Color

object Constant {
    const val DATABASE_NAME: String = "running_db"
    const val KEY_PREFERENCES_NAME = "KEY_PREFERENCES_NAME"
    const val KEY_FIRST_TOGGLE = "KEY_FIRST_TOGGLE"
    const val KEY_NAME = "KEY_NAME"
    const val KEY_WEIGHT = "KEY_WEIGHT"
    const val REQUEST_PERMISSION_CODE: Int = 0
    const val ACTION_SHOW_TRACKING_FRAGMENT = "ACTION_SHOW_TRACKING_FRAGMENT"
    const val ACTION_START_RESUME_SERVICE = "START_RESUME_SERVICE"
    const val ACTION_PAUSE_SERVICE = "PAUSE_SERVICE"
    const val ACTION_STOP_SERVICE = "STOP_SERVICE"
    const val NOTIFICATION_CHANEL_ID = "Tracking_chanel"
    const val NOTIFICATION_CHANEL_NAME = "Tracking"
    const val NOTIFICATION_ID = 1
    const val LOCATION_UPDATE_INTERVAL = 5000L
    const val FASTEST_LOCATION_INTERVAL = 2000L
    const val POLYLINE_COLOR = android.graphics.Color.RED
    const val POLYLINE_WIDTH = 8f
    const val MAP_ZOOM = 15f
    const val TIMER_UPDATE_INTERVAL = 50L
    val FLAG_PENDING_INTENT = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S) PendingIntent.FLAG_IMMUTABLE
            else PendingIntent.FLAG_UPDATE_CURRENT
    const val CANCEL_TRACKING_DIALOG_TAG = "Cancel Tracking"
}