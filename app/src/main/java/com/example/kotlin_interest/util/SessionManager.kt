package com.example.kotlin_interest.util

import android.content.Context
import android.content.SharedPreferences
import com.example.kotlin_interest.model.JwtResponse
import com.example.kotlin_interest.model.User
import com.google.gson.Gson
import javax.inject.Inject
import javax.inject.Singleton

class SessionManager @Inject constructor(private val sharedPreferences: SharedPreferences, private val gson: Gson) {

    fun getAccessToken() : String? = sharedPreferences.getString(ACCESS_TOKEN, null)

    fun getRefreshToken() : String? = sharedPreferences.getString(REFRESH_TOKEN, null)

    fun getUser() : User? {
        val json = sharedPreferences.getString(USER, null);
        json.let { return gson.fromJson(it, User::class.java) }
    }

    fun saveTokens(jwtResponse: JwtResponse) {
        val editor = sharedPreferences.edit()
        editor.putString(ACCESS_TOKEN, jwtResponse.accessToken)
        editor.putString(REFRESH_TOKEN, jwtResponse.refreshToken)
        editor.apply()
    }

    fun saveUser(user: User) = sharedPreferences.edit().putString(USER, gson.toJson(user))

    companion object {
        const val ACCESS_TOKEN = "ACCESS_TOKEN"
        const val REFRESH_TOKEN = "REFRESH_TOKEN"
        const val USER = "USER"
    }
}