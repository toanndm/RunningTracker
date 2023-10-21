package com.example.runningtracker.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.room.Room
import com.example.runningtracker.db.RunningDatabase
import com.example.runningtracker.other.Constant.DATABASE_NAME
import com.example.runningtracker.other.Constant.KEY_FIRST_TOGGLE
import com.example.runningtracker.other.Constant.KEY_NAME
import com.example.runningtracker.other.Constant.KEY_PREFERENCES_NAME
import com.example.runningtracker.other.Constant.KEY_WEIGHT
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Singleton
    @Provides
    fun provideRunningDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, RunningDatabase::class.java, DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideRunningDAO(db: RunningDatabase) = db.getDAO()

    @Singleton
    @Provides
    fun provideSharedPreferences(
        @ApplicationContext app: Context
    ) = app.getSharedPreferences(KEY_PREFERENCES_NAME, MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideName(sharedPreferences: SharedPreferences) = sharedPreferences.getString(
        KEY_NAME, "")
    @Singleton
    @Provides
    fun provideWeight(sharedPreferences: SharedPreferences) = sharedPreferences.getFloat(
        KEY_WEIGHT, 0f)

    @Singleton
    @Provides
    fun provideFirstToggle(sharedPreferences: SharedPreferences) = sharedPreferences.getBoolean(
        KEY_FIRST_TOGGLE, true)


}