package com.example.kotlin_interest.retrofit

import com.example.kotlin_interest.model.InterestCategory
import com.example.kotlin_interest.model.User
import retrofit2.Response
import retrofit2.http.*

interface UserRetrofitService {

    @POST("/user/{userId}/username")
    suspend fun postUsername(@Path("userId") userId: Long, @Query("username") username: String)

    @POST("/user/{userId}/description")
    suspend fun postDescription(@Path("userId") userId: Long, @Query("description") description: String)

    @GET("/user/{userId}/list")
    suspend fun getUsersByInterests(@Path("userId") userId: Long, @Query("interestIds") interestId: List<Long>) : List<User>
}