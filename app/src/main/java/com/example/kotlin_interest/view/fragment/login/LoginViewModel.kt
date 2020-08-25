package com.example.kotlin_interest.view.fragment.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_interest.model.JwtTokens
import com.example.kotlin_interest.model.User
import com.example.kotlin_interest.retrofit.LoginRetrofitService
import com.example.kotlin_interest.util.SessionManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val loginRetrofitService: LoginRetrofitService,
    private val sessionManager: SessionManager,
    var loginInfo: LoginInfo
) : ViewModel() {

    private var responseJwt: MutableLiveData<JwtTokens> = MutableLiveData()
    private var errorMsg: MutableLiveData<String> = MutableLiveData()

    fun getErrorMsg() : LiveData<String> = errorMsg
    fun getTokenResponse() : LiveData<JwtTokens> = responseJwt

    fun signIn() {
        checkLoginInfo(loginInfo)?.let {
            errorMsg.postValue(it)
            return
        }
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = loginRetrofitService.postCreateAuthToken(loginInfo, sessionManager.getFingerprint())
                if (response.isSuccessful) {
                    val body = response.body()!!
                    responseJwt.postValue(body.jwtTokens)
                    // Save tokens
                    sessionManager.saveTokens(body.jwtTokens)
                    sessionManager.setLoggedIn(true)
                    // Save user
                    sessionManager.saveUser(body.user)
                } else {
                    val error = response.errorBody()!!.string()
                    errorMsg.postValue(error)
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