package com.example.kotlin_interest.di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.kotlin_interest.util.NetworkUtil
import com.example.kotlin_interest.util.SessionManager
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideManager(sharedPreferences: SharedPreferences, gson: Gson): SessionManager =
        SessionManager(sharedPreferences, gson)

    @Singleton
    @Provides
    fun provideNetworkUtil(): NetworkUtil = NetworkUtil()

    @Singleton
    @Provides
    fun provideSharedPreferences(application: Application): SharedPreferences =
        application.getSharedPreferences("interest", Context.MODE_PRIVATE)
}