package com.example.kotlin_interest.view.fragment.interests

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.kotlin_interest.model.Interest
import com.example.kotlin_interest.model.InterestCategory
import com.example.kotlin_interest.model.JwtTokens
import com.example.kotlin_interest.model.User
import com.example.kotlin_interest.repository.InterestsRepository
import com.example.kotlin_interest.retrofit.LoginRetrofitService
import com.example.kotlin_interest.util.Resource
import com.example.kotlin_interest.util.SessionManager
import com.example.kotlin_interest.view.fragment.login.LoginInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class InterestsViewModel @Inject constructor(
    private val interestsRepository: InterestsRepository,
    private val loginRetrofitService: LoginRetrofitService,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val interests: ArrayList<Interest> = ArrayList()

    private val responseJwt: MutableLiveData<JwtTokens> = MutableLiveData()
    private val errorMsg: MutableLiveData<String> = MutableLiveData()

    fun getInterests() = interests

    fun getCategories() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = interestsRepository.getCategories()))
        } catch (ex: Exception) {
            emit(Resource.error(data = null, message = ex.message ?: "Error occurred!"))
        }
    }

    fun getCategoriesFromServer() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = interestsRepository.getCategoriesFromServer()))
        } catch (ex: Exception) {
            emit(Resource.error(data = null, message = ex.message ?: "Error occurred!"))
        }
    }

    fun getErrorMsg(): LiveData<String> = errorMsg
    fun getTokenResponse(): LiveData<JwtTokens> = responseJwt


    private fun signIn(loginInfo: LoginInfo) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = loginRetrofitService.postCreateAuthToken(
                    loginInfo,
                    sessionManager.getFingerprint()
                )
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
            } catch (e: java.lang.Exception) {
                errorMsg.postValue(e.message)
            }
        }
    }


    suspend fun register(user: User) {
        withContext(Dispatchers.IO) {
            user.interests = interests
            loginRetrofitService.postRegister(user)
            signIn(LoginInfo(user.username, user.password))
        }
    }
}