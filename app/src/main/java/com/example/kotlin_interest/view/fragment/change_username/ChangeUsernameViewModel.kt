package com.example.kotlin_interest.view.fragment.change_username

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlin_interest.model.User
import com.example.kotlin_interest.retrofit.UserRetrofitService
import com.example.kotlin_interest.retrofit.ValidationRetrofitService
import com.example.kotlin_interest.util.SessionManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChangeUsernameViewModel @Inject constructor(
    private val validationRetrofitService: ValidationRetrofitService,
    private val userRetrofitService: UserRetrofitService,
    private val sessionManager: SessionManager
) : ViewModel() {

    var user: User? = null

    init {
        user = sessionManager.getUser()
    }

    val usernameError = MutableLiveData<String>()

    private val usernameCheckCompleted = MutableLiveData<Boolean>(true)

    val username = MutableLiveData<String>(user!!.username)

    fun getUsernameCheckCompleted(): LiveData<Boolean> = usernameCheckCompleted

    fun checkUsername() {
        val str = username.value.toString()
        viewModelScope.launch(Dispatchers.IO) {
            if (username.value != user!!.username) {
                usernameCheckCompleted.postValue(false)
                if (username.value == "") {
                    usernameError.postValue("Check your input")
                } else if (str.length < 7 || str.length > 20) {
                    usernameError.postValue("Length should be between 7 and 20")
                } else if (!validationRetrofitService.checkUsername(str)) {
                    usernameError.postValue("This username is already taken")
                } else {
                    usernameError.postValue("")
                }
                usernameCheckCompleted.postValue(true)
            }
        }
    }

    fun changeUsername() {
        if (username.value != user!!.username && usernameError.value == "" && usernameCheckCompleted.value!!) {
            CoroutineScope(Dispatchers.IO).launch {
                user!!.username = username.value!!
                userRetrofitService.postUsername(user!!.id, username.value!!)
                sessionManager.saveUser(user!!)
            }
        }
    }
}