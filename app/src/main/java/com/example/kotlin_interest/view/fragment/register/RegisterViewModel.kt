package com.example.kotlin_interest.view.fragment.register

import androidx.lifecycle.ViewModel
import com.example.kotlin_interest.retrofit.LoginRetrofitService
import javax.inject.Inject

class RegisterViewModel @Inject constructor(val loginRetrofitService: LoginRetrofitService) : ViewModel() {
}