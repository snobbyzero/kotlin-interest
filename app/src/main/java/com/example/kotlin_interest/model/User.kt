package com.example.kotlin_interest.model

import java.io.Serializable
import javax.inject.Inject

class User : Serializable {
    val id: Long = -1
    var username: String = ""
    var password: String = ""
    val email: String = ""
    var sex: String = ""
    var description: String = ""
    var age: Int = -1
    var imageToken: String = ""
    var phoneTokens: List<PhoneToken> = ArrayList()
    var interests: List<Interest> = ArrayList()
    var chatId: Long = -1L
}