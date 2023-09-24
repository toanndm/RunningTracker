package com.example.runningtracker.di

import android.content.Context
import androidx.room.Room
import com.example.runningtracker.db.RunningDatabase
import com.example.runningtracker.other.Constant.DATABASE_NAME
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

}