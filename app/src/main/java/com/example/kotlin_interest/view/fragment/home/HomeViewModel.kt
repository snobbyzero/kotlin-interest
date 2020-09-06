package com.example.kotlin_interest.view.fragment.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_interest.model.User
import com.example.kotlin_interest.util.NetworkUtil
import javax.inject.Inject

class HomeViewModel @Inject constructor(networkUtil: NetworkUtil) : ViewModel() {

    private val usersList = MutableLiveData<List<User>>()

}