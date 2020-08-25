package com.example.kotlin_interest.view.fragment.description

import androidx.lifecycle.*
import com.example.kotlin_interest.model.JwtTokens
import com.example.kotlin_interest.model.User
import com.example.kotlin_interest.retrofit.LoginRetrofitService
import com.example.kotlin_interest.util.SessionManager
import com.example.kotlin_interest.view.fragment.login.LoginInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class DescriptionViewModel @Inject constructor(
    private val loginRetrofitService: LoginRetrofitService,
    private val sessionManager: SessionManager
) :
    ViewModel() {

    val description = MutableLiveData<String>()
    private val responseJwt: MutableLiveData<JwtTokens> = MutableLiveData()
    private val errorMsg: MutableLiveData<String> = MutableLiveData()
    private var user: User? = null

    fun getErrorMsg() : LiveData<String> = errorMsg
    fun getTokenResponse() : LiveData<JwtTokens> = responseJwt

    private fun signIn(loginInfo: LoginInfo) {
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


    suspend fun register(user: User) {
        user.description = description.value ?: ""
        withContext(Dispatchers.IO) {
            val rUser = loginRetrofitService.postRegister(user)
            signIn(LoginInfo(user.username, user.password))
        }
    }


}