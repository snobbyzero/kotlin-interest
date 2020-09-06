package com.example.kotlin_interest.view.fragment.main_information

import androidx.lifecycle.*
import com.example.kotlin_interest.model.User
import com.example.kotlin_interest.retrofit.ValidationRetrofitService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainInformationViewModel @Inject constructor(private val validationRetrofitService: ValidationRetrofitService) :
    ViewModel() {

    val usernameError = MutableLiveData<String>()
    val passwordError = MutableLiveData<String>()
    val emailError = MutableLiveData<String>()
    val ageError = MutableLiveData<String>()

    private val usernameCheckCompleted = MutableLiveData<Boolean>(true)
    private val emailCheckCompleted = MutableLiveData<Boolean>(true)

    val username = MutableLiveData<String>("")
    val password = MutableLiveData<String>("")
    val email = MutableLiveData<String>("")
    val age = MutableLiveData<String>("")

    fun getUsernameCheckCompleted(): LiveData<Boolean> = usernameCheckCompleted
    fun getEmailCheckCompleted(): LiveData<Boolean> = emailCheckCompleted

    fun checkUsername() {
        val str = username.value.toString()
        viewModelScope.launch(Dispatchers.IO) {
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

    fun checkPassword() {
        val str = password.value.toString()
        return if (password.value == "" || str.length < 7 || str.length > 20) {
            passwordError.postValue("Length should be between 7 and 20")
        } else {
            passwordError.postValue("")
        }
    }

    fun checkEmail() {
        val str = email.value.toString()
        viewModelScope.launch(Dispatchers.IO) {
            emailCheckCompleted.postValue(false)
            if (email.value == "") {
                emailError.postValue("Check your input")
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(str).matches()) {
                emailError.postValue("Check your input")
            } else if (!validationRetrofitService.checkEmail(str)) {
                emailError.postValue("This email is already taken")
            } else {
                emailError.postValue("")
            }
            emailCheckCompleted.postValue(true)
        }


    }

    fun checkAge() {
        return if (age.value == "") {
            ageError.postValue("Input your age")
        } else {
            ageError.postValue("")
        }
    }

    fun checkAllFields(): Boolean {
        if (username.value == "") checkUsername()
        if (password.value == "") checkPassword()
        if (email.value == "") checkEmail()
        if (age.value == "") checkAge()
        return usernameError.value == "" &&
                passwordError.value == "" &&
                emailError.value == "" &&
                ageError.value == ""
    }

    fun getUser(): User {
        return User(username.value!!, password.value!!, age.value!!.toInt(), email.value!!)
    }
}