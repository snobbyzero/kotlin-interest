package com.example.kotlin_interest.di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.example.kotlin_interest.util.SessionManager
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule() {

    @Singleton
    @Provides
    fun provideManager(sharedPreferences: SharedPreferences, gson: Gson) : SessionManager = SessionManager(sharedPreferences, gson)

    @Singleton
    @Provides
    fun provideSharedPreferences(context: Context) : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

}