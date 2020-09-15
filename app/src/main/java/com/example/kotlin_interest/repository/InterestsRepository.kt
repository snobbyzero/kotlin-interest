package com.example.kotlin_interest.repository

import com.example.kotlin_interest.dao.InterestCategoryDao
import com.example.kotlin_interest.model.InterestCategory
import com.example.kotlin_interest.retrofit.InterestsRetrofitService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InterestsRepository @Inject constructor(
    private val interestsRetrofitService: InterestsRetrofitService,
    private val interestCategoryDao: InterestCategoryDao
) {

    suspend fun getCategories(): List<InterestCategory> {
        val categories: ArrayList<InterestCategory> = interestCategoryDao.getCategories() as ArrayList<InterestCategory>
        if (categories.isEmpty()) {
            return getCategoriesFromServer()
        }
        return categories
    }

    suspend fun getCategoriesFromServer() : List<InterestCategory> {
        val categoriesFromServer = interestsRetrofitService.getCategories()
        interestCategoryDao.insert(*categoriesFromServer.toTypedArray())
        return categoriesFromServer
    }
}