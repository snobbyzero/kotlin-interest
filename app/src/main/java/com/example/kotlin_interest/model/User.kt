package com.example.kotlin_interest.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import javax.inject.Inject

class User() : Serializable {
    @SerializedName("id")
    val id: Long = -1
    @SerializedName("username")
    var username: String = ""
    @SerializedName("password")
    var password: String = ""
    @SerializedName("email")
    var email: String = ""
    @SerializedName("sex")
    var sex: String = ""
    @SerializedName("description")
    var description: String = ""
    @SerializedName("age")
    var age: Int = -1
    @SerializedName("imageToken")
    var imageToken: String = ""
    @SerializedName("phoneTokens")
    var phoneTokens: List<PhoneToken> = ArrayList()
    @SerializedName("interests")
    var interests: List<Interest> = ArrayList()
    @SerializedName("chatId")
    var chatId: Long = -1L

    constructor(username: String, password: String, age: Int, email: String) : this() {
        this.username = username
        this.password = password
        this.age = age
        this.email = email
    }
}