package com.example.kotlin_interest.util

class NetworkUtil {

    fun getUserImageUrl(id: Long): String = "http://interest-project.herokuapp.com/user/${id}/image"

}