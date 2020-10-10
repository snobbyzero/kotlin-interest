package com.example.kotlin_interest.model

import com.google.gson.annotations.SerializedName

data class JwtTokens(
    @SerializedName("accessToken") val accessToken: String,
    @SerializedName("refreshToken") val refreshToken: String
)