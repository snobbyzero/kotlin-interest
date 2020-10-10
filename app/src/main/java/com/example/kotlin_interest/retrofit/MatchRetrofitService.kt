package com.example.kotlin_interest.retrofit

import com.example.kotlin_interest.model.Match
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


interface MatchRetrofitService {

    @Headers("Content-Type: application/json")
    @POST("match")
    suspend fun addMatch(@Body match: Match) : String
}