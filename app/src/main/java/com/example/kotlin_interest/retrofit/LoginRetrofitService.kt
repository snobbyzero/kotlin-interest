package com.example.kotlin_interest.retrofit

import com.example.kotlin_interest.model.JwtResponse
import com.example.kotlin_interest.model.User
import com.example.kotlin_interest.view.fragment.login.LoginInfo
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface LoginRetrofitService {

    @Headers("Content-Type: application/json")
    @POST("auth/token")
    suspend fun postCreateAuthToken(
        @Body loginInfo: LoginInfo,
        @Query("fingerprint") fingerprint: String
    ): Response<JwtResponse>

    @Headers("Content-Type: application/json")
    @POST("register")
    suspend fun postRegister(@Body loginInfo: LoginInfo): User

    @Headers("Content-type: application/json")
    @POST("auth/refresh-token")
    suspend fun updateTokens(
        @Query("refreshToken") refreshToken: String,
        @Query("fingerprint") fingerprint: String
    )
}