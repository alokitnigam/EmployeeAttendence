package com.example.syncbyte.DI.database

import android.content.Context

import androidx.room.Room

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

@Module
class RoomDBModule {

    @Singleton
    @Provides
    fun provideAudioDatabase(context: Context): EmployeeDatabase {
        return Room.databaseBuilder(
            context,
            EmployeeDatabase::class.java, EmployeeDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Singleton
    @Provides
    fun provideEmployeeDao(employeeDatabase: EmployeeDatabase): EmployeeRepo {
        return employeeDatabase.audioDao()
    }
}