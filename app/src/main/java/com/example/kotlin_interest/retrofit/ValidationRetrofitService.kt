package com.example.kotlin_interest.retrofit

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ValidationRetrofitService {

    @Headers("Content-Type: application/json")
    @GET("validation/username")
    suspend fun checkUsername(@Query("username") username: String) : Boolean

    @Headers("Content-Type: application/json")
    @GET("validation/email")
    suspend fun checkEmail(@Query("email") email: String) : Boolean

}