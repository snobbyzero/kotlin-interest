package com.example.kotlin_interest.view.fragment.description

import androidx.lifecycle.*
import com.example.kotlin_interest.model.JwtTokens
import com.example.kotlin_interest.model.User
import com.example.kotlin_interest.retrofit.LoginRetrofitService
import com.example.kotlin_interest.retrofit.UserRetrofitService
import com.example.kotlin_interest.util.SessionManager
import com.example.kotlin_interest.view.fragment.login.LoginInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class DescriptionViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val userRetrofitService: UserRetrofitService
) : ViewModel() {
    val user = sessionManager.getUser()
    val description = MutableLiveData<String>(user?.description ?: "")
    val error = MutableLiveData<String>("")
    fun checkDescription(): Boolean {
        description.postValue(description.value!!.trim())
        if (description.value!!.trim().length < MIN_LENGTH) {
            error.postValue("Description must have at least 20 characters")
            return false
        } else if (description.value!!.trim().length > MAX_LENGTH) {
            error.postValue("Description's length can't be more than 100 characters")
            return false
        } else {
            error.postValue("")
            return true
        }
    }

    // Method for changing description in profile
    fun changeDescription() {
        if (user != null && description.value != user.description && error.value == "") {
            CoroutineScope(Dispatchers.IO).launch {
                user.description = description.value!!
                userRetrofitService.postDescription(user.id, description.value!!)
                sessionManager.saveUser(user)
            }
        }
    }

    companion object {
        const val MIN_LENGTH = 20
        const val MAX_LENGTH = 100
    }
}