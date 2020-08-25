package com.example.kotlin_interest.util

import android.content.SharedPreferences
import com.example.kotlin_interest.model.JwtTokens
import com.example.kotlin_interest.model.User
import com.google.gson.Gson
import java.util.*
import javax.inject.Inject

class SessionManager @Inject constructor(private val sharedPreferences: SharedPreferences, private val gson: Gson) {

    fun getAccessToken() : String = sharedPreferences.getString(ACCESS_TOKEN, "-")!!

    fun getRefreshToken() : String = sharedPreferences.getString(REFRESH_TOKEN, "-")!!

    fun getFingerprint() : String = sharedPreferences.getString(FINGERPRINT, createAndSaveFingerprint())!!

    fun getUser() : User? {
        val json = sharedPreferences.getString(USER, null)
        json.let { return gson.fromJson(it, User::class.java) }
    }

    fun getImageToken() : String? = sharedPreferences.getString(IMAGE_TOKEN, "")

    fun getLoggedIn() : Boolean = sharedPreferences.getBoolean(LOGGED_IN, false)

    fun saveTokens(jwtResponse: JwtTokens) {
        val editor = sharedPreferences.edit()
        editor.putString(ACCESS_TOKEN, jwtResponse.accessToken)
        editor.putString(REFRESH_TOKEN, jwtResponse.refreshToken)
        editor.apply()
    }

    fun saveUser(user: User) = sharedPreferences.edit().putString(USER, gson.toJson(user)).apply()

    fun saveImageToken(token: String) = sharedPreferences.edit().putString(IMAGE_TOKEN, token).apply()

    private fun createAndSaveFingerprint() : String {
        val fingerprint = UUID.randomUUID().toString()
        if (!sharedPreferences.contains(FINGERPRINT)) {
            val editor = sharedPreferences.edit()
            editor.putString(FINGERPRINT, fingerprint)
            editor.apply()
        }
        return fingerprint
    }

    fun setLoggedIn(isLoggedIn: Boolean) {
        sharedPreferences.edit().putBoolean(LOGGED_IN, isLoggedIn).apply()
    }

    companion object {
        const val ACCESS_TOKEN = "ACCESS_TOKEN"
        const val REFRESH_TOKEN = "REFRESH_TOKEN"
        const val USER = "USER"
        const val FINGERPRINT = "FINGERPRINT"
        const val LOGGED_IN = "LOGGED_IN"
        const val IMAGE_TOKEN = "IMAGE_TOKEN"
    }
}