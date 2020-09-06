package com.example.kotlin_interest.retrofit

import com.example.kotlin_interest.model.InterestCategory
import retrofit2.http.GET
import retrofit2.http.Headers

interface InterestsRetrofitService {

    @Headers("Content-Type: application/json")
    @GET("/categories")
    suspend fun getCategories() : List<InterestCategory>
}