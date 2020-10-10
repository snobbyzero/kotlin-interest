package com.example.kotlin_interest.model

import com.google.gson.annotations.SerializedName

class JwtResponse (
    @SerializedName("jwtToken")
    val jwtTokens: JwtTokens,
    @SerializedName("user")
    val user: User
)