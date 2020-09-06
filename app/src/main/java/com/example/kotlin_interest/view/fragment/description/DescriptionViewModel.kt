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

class DescriptionViewModel @Inject constructor() : ViewModel() {
    val description = MutableLiveData<String>("")
    val error = MutableLiveData<String>("")
    fun checkDescription() : Boolean {
        if (description.value!!.length < MIN_LENGTH) {
            error.postValue("Description must have at least 20 characters")
            return false
        } else if (description.value!!.length > MAX_LENGTH) {
            error.postValue("Description's length can't be more than 100 characters")
            return false
        } else {
            error.postValue("")
            return true
        }
    }

    companion object {
        const val MIN_LENGTH = 20
        const val MAX_LENGTH = 100
    }
}