package com.example.kotlin_interest.repository

import com.example.kotlin_interest.retrofit.InterestsRetrofitService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InterestsRepository @Inject constructor(private val interestsRetrofitService: InterestsRetrofitService) {

    suspend fun getCategories() = interestsRetrofitService.getCategories()
}