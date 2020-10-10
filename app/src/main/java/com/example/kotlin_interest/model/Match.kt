package com.example.kotlin_interest.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import javax.inject.Inject

class Match (
    @SerializedName("id") val id: Long?,
    @SerializedName("userId") val userId: Long,
    @SerializedName("likedId") val likedId: Long
) : Serializable {
}