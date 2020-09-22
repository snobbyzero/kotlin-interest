package com.example.kotlin_interest.di.module

import android.app.Application
import androidx.room.Room
import com.example.kotlin_interest.database.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(application: Application) : AppDatabase =
    Room.databaseBuilder(
        application,
        AppDatabase::class.java,
        "interestDB"
    ).build()

    @Singleton
    @Provides
    fun provideInterestCategoryDao(database: AppDatabase) = database.interestCategoryDao()

    @Singleton
    @Provides
    fun provideUserDao(database: AppDatabase) = database.userDao()
}