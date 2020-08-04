package com.example.kotlin_interest.view.fragment.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_interest.model.JwtResponse
import com.example.kotlin_interest.model.User
import com.example.kotlin_interest.retrofit.LoginRetrofitService
import com.example.kotlin_interest.util.SessionManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val loginRetrofitService: LoginRetrofitService,
    private val sessionManager: SessionManager
) : ViewModel() {

    var responseJwt: MutableLiveData<JwtResponse> = MutableLiveData()
    var errorMsg: MutableLiveData<String> = MutableLiveData()


    fun getErrorMsg() : LiveData<String> = errorMsg
    fun getTokenResponse() : LiveData<JwtResponse> = responseJwt

    fun signIn(loginInfo: LoginInfo, fingerprint: String) {
        checkLoginInfo(loginInfo)?.let {
            errorMsg.postValue(it)
            return
        }
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = loginRetrofitService.postCreateAuthToken(loginInfo, fingerprint)
                if (response.isSuccessful) {
                    val jwtToken = response.body()!!
                    responseJwt.postValue(jwtToken)
                    // Save tokens
                    sessionManager.saveTokens(jwtToken)
                } else {
                    errorMsg.postValue(response.message())
                }
            } catch (e: Exception) {
                errorMsg.postValue(e.message)
            }
        }
    }

    private fun checkLoginInfo(loginInfo: LoginInfo) : String? {
        if (loginInfo.username == "") return "Enter your username"
        if (loginInfo.password == "") return "Enter your password"
        return null
    }
}