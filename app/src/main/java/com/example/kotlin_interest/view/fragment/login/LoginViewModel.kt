package com.example.kotlin_interest.view.fragment.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.kotlin_interest.model.JwtResponse
import com.example.kotlin_interest.model.JwtTokens
import com.example.kotlin_interest.retrofit.LoginRetrofitService
import com.example.kotlin_interest.util.NetworkUtil
import com.example.kotlin_interest.util.Resource
import com.example.kotlin_interest.util.SessionManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val loginRetrofitService: LoginRetrofitService,
    private val sessionManager: SessionManager,
    var loginInfo: LoginInfo
) : ViewModel() {

    private val responseJwt: MutableLiveData<JwtTokens> = MutableLiveData()
    private val errorMsg: MutableLiveData<String> = MutableLiveData()
    val usernameError = MutableLiveData<String>()
    val passwordError = MutableLiveData<String>()

    fun getErrorMsg(): LiveData<String> = errorMsg
    fun getTokenResponse() = liveData(Dispatchers.IO) {
        if (checkLoginInfo()) {
            emit(Resource.loading(data = null))
            try {
                emit(
                    Resource.success(
                        data = loginRetrofitService.postCreateAuthToken(
                            loginInfo,
                            sessionManager.getFingerprint()
                        )
                    )
                )
            } catch (ex: Exception) {
                emit(Resource.error(data = null, message = ex.message ?: "Error occurred!"))
            }
        }
    }

    fun checkResponse(response: Response<JwtResponse>?): Boolean {
        response?.let {
            if (it.isSuccessful) {
                val body = it.body()!!
                responseJwt.postValue(body.jwtTokens)
                // Save tokens
                sessionManager.saveTokens(body.jwtTokens)
                sessionManager.setLoggedIn(true)
                // Save user
                sessionManager.saveUser(body.user)

                return true
            } else {
                errorMsg.postValue("Invalid username or password")
            }
        }
        return false
    }

    fun checkUsername() {
        if (loginInfo.username == "") {
            usernameError.postValue("Enter your username")
        } else {
            usernameError.postValue("")
        }
    }

    fun checkPassword() {
        if (loginInfo.password == "") passwordError.postValue("Enter your password") else passwordError.postValue(
            ""
        )
    }

    private fun checkLoginInfo(): Boolean {
        checkUsername()
        checkPassword()
        return usernameError.value == "" && passwordError.value == ""
    }

}