package com.example.kotlin_interest.view.fragment.profile

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.example.kotlin_interest.GlideApp
import com.example.kotlin_interest.model.User
import com.example.kotlin_interest.util.NetworkUtil
import com.example.kotlin_interest.util.SessionManager
import javax.inject.Inject

class ProfileViewModel @Inject constructor(private val sessionManager: SessionManager, private val networkUtil: NetworkUtil, private val sharedPreferences: SharedPreferences, private val app: Application) : ViewModel()  {

    var user: User? = null
    var imagePath: String? = null
    var imageToken: String? = null

    init {
        user = sessionManager.getUser()
        imagePath = networkUtil.getUserImageUrl(user!!.id)
        imageToken = sessionManager.getImageToken()
    }

    fun logout() {
        sharedPreferences.edit().clear().apply()
        GlideApp.get(app).clearMemory()
    }
}