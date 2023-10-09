package com.example.runningtracker.services

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_LOW
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Build
import android.os.Looper
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.runningtracker.R
import com.example.runningtracker.other.Constant.ACTION_PAUSE_SERVICE
import com.example.runningtracker.other.Constant.ACTION_SHOW_TRACKING_FRAGMENT
import com.example.runningtracker.other.Constant.ACTION_START_RESUME_SERVICE
import com.example.runningtracker.other.Constant.ACTION_STOP_SERVICE
import com.example.runningtracker.other.Constant.FASTEST_LOCATION_INTERVAL
import com.example.runningtracker.other.Constant.LOCATION_UPDATE_INTERVAL
import com.example.runningtracker.other.Constant.NOTIFICATION_CHANEL_ID
import com.example.runningtracker.other.Constant.NOTIFICATION_CHANEL_NAME
import com.example.runningtracker.other.Constant.NOTIFICATION_ID
import com.example.runningtracker.other.TrackingUtil
import com.example.runningtracker.ui.MainActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import timber.log.Timber

typealias Polyline = MutableList<LatLng>
typealias Polylines = MutableList<Polyline>
class TrackingService: LifecycleService() {
    var isFirstRun = true

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    companion object {
        val isTracking = MutableLiveData<Boolean>()
        val pathPoints = MutableLiveData<Polylines>()
    }

    override fun onCreate() {
        super.onCreate()
        postInitialValues()
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        isTracking.observe(this, Observer {
            updateLocation(it)
        })
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        intent?.let {
            when(it.action) {
                ACTION_START_RESUME_SERVICE -> {
                    if (isFirstRun) {
                        startForegroundService()
                        isFirstRun = false
                    } else {
                        Timber.d("resume")
                        startForegroundService()
                    }

                }
                ACTION_PAUSE_SERVICE -> {
                    pauseService()
                }
                ACTION_STOP_SERVICE -> {
                    Timber.d("Stopped service")
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)

    }

    private fun pauseService() {
        isTracking.postValue(false)
    }

    @SuppressLint("MissingPermission")
    private fun updateLocation(isTracking: Boolean) {
        if (isTracking) {
            if (TrackingUtil.hasLocationPermission(this)) {
                val request = com.google.android.gms.location.LocationRequest.Builder(
                    Priority.PRIORITY_HIGH_ACCURACY,
                    LOCATION_UPDATE_INTERVAL
                ).setMinUpdateIntervalMillis(FASTEST_LOCATION_INTERVAL).build()

                fusedLocationProviderClient.requestLocationUpdates(
                    request,
                    locationCallback,
                    Looper.getMainLooper()
                )
            } else {
                fusedLocationProviderClient.removeLocationUpdates(locationCallback)
            }

        }
    }

    val locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            super.onLocationResult(p0)
            if (isTracking.value!!) {
                for (location in p0.locations) {
                    addPathPoint(location)
                    Timber.d("(${location.latitude}, ${location.longitude}")
                }
            }
        }
    }

    private fun postInitialValues() {
        isTracking.postValue(false)
        pathPoints.postValue(mutableListOf())
    }

    private fun addEmptyPolyline() = pathPoints.value?.apply {
        add(mutableListOf())
        pathPoints.postValue(this)
    } ?: pathPoints.postValue(mutableListOf(mutableListOf()))

    private fun addPathPoint(location: Location?) {
        location?.let {
            val pos = LatLng(location.latitude, location.longitude)
            pathPoints.value?.apply {
                last().add(pos)
                pathPoints.postValue(this)
            }
        }
    }

    private fun startForegroundService() {
        addEmptyPolyline()
        isTracking.postValue(true)
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