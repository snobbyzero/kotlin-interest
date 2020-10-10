package com.example.kotlin_interest.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import javax.inject.Inject

class User() : Serializable {
    @SerializedName("id")
    var id: Long = -1

    @SerializedName("username")
    var username: String = ""

    @SerializedName("password")
    var password: String = ""

    @SerializedName("email")
    var email: String? = ""

    @SerializedName("sex")
    var sex: String = ""

    @SerializedName("description")
    var description: String = ""

    @SerializedName("age")
    var age: Int = -1

    @SerializedName("imageToken")
    var imageToken: String = ""

    @SerializedName("interests")
    var interests: List<Interest> = ArrayList()

    constructor(username: String, password: String, age: Int, email: String) : this() {
        this.username = username
        this.password = password
        this.age = age
        this.email = email
    }

    constructor(
        username: String,
        password: String,
        age: Int,
        email: String,
        description: String
    ) : this(username, password, age, email) {
        this.description = description
    }

    constructor(
        id: Long,
        username: String,
        email: String?,
        sex: String,
        description: String,
        age: Int,
        imageToken: String,
        interests: List<Interest>
    ) : this() {
        this.id = id
        this.username = username
        this.email = email
        this.sex = sex
        this.description = description
        this.age = age
        this.imageToken = imageToken
        this.interests = interests
    }
}