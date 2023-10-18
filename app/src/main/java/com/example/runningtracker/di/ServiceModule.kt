package com.example.runningtracker.di

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import com.example.runningtracker.R
import com.example.runningtracker.other.Constant
import com.example.runningtracker.other.Constant.FLAG_PENDING_INTENT
import com.example.runningtracker.ui.MainActivity
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ServiceScoped

@Module
@InstallIn(ServiceComponent::class)
object ServiceModule {

    @ServiceScoped
    @Provides
    fun provideFusedLocationProviderClient(
        @ApplicationContext app: Context
    ) = LocationServices.getFusedLocationProviderClient(app)

    @ServiceScoped
    @Provides
    fun provideMainActivityPendingIntent (
        @ApplicationContext app: Context
    ): PendingIntent {
        return PendingIntent.getActivity(
            app,
            0,
            Intent(app, MainActivity::class.java).also {
                it.action = Constant.ACTION_SHOW_TRACKING_FRAGMENT
            },
            FLAG_PENDING_INTENT
        )
    }

    @ServiceScoped
    @Provides
    fun provideNotificationManager(
        @ApplicationContext app: Context
    ) = app.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    @ServiceScoped
    @Provides
    fun provideBaseNotificationBuilder(
        @ApplicationContext app: Context,
        pendingIntent : PendingIntent,
        notificationManager: NotificationManager
    ) : NotificationCompat.Builder {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                Constant.NOTIFICATION_CHANEL_ID,
                Constant.NOTIFICATION_CHANEL_NAME,
                NotificationManager.IMPORTANCE_LOW
            )
            notificationManager.createNotificationChannel(channel)
        }
        return NotificationCompat.Builder(app, Constant.NOTIFICATION_CHANEL_ID)
            .setAutoCancel(false)
            .setOngoing(true)
            .setSmallIcon(R.drawable.baseline_directions_run_24)
            .setContentTitle("Running")
            .setContentText("00:00:00")
            .setContentIntent(pendingIntent)
    }


}