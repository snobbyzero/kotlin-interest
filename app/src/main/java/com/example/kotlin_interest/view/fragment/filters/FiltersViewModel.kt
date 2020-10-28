package com.example.kotlin_interest.view.fragment.filters

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_interest.model.Interest
import com.example.kotlin_interest.model.User
import com.example.kotlin_interest.util.SessionManager
import javax.inject.Inject

class FiltersViewModel @Inject constructor(sessionManager: SessionManager) : ViewModel() {

    private val interests = ArrayList<Interest>()
    private val checkedInterests = ArrayList<Interest>()

    init {
        interests.addAll(sessionManager.getUser()!!.interests)
    }

    fun getInterests() = interests

    fun getCheckedInterests() = checkedInterests
}