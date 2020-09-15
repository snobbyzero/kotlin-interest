package com.example.kotlin_interest.retrofit

import com.example.kotlin_interest.model.InterestCategory
import retrofit2.Response
import retrofit2.http.*

interface UserRetrofitService {

    @POST("/user/{userId}/username")
    suspend fun postUsername(@Path("userId") userId: Long, @Query("username") username: String)

    @POST("/user/{userId}/description")
    suspend fun postDescription(@Path("userId") userId: Long, @Query("description") description: String)
}