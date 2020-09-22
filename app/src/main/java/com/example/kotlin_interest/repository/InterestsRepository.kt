package com.example.kotlin_interest.repository

import com.example.kotlin_interest.dao.InterestCategoryDao
import com.example.kotlin_interest.entity.CategoryWithInterests
import com.example.kotlin_interest.entity.InterestCategoryEntity
import com.example.kotlin_interest.entity.InterestEntity
import com.example.kotlin_interest.model.Interest
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
        val categories: List<CategoryWithInterests> = interestCategoryDao.getCategoriesWithInterests()
        if (categories.isEmpty()) {
            return getCategoriesFromServer()
        }
        return categories.map { entityToDto(it) }
    }

    suspend fun getCategoriesFromServer() : List<InterestCategory> {
        val categoriesFromServer = interestsRetrofitService.getCategories()
        interestCategoryDao.insert(*categoriesFromServer.map { dtoToEntity(it) }.toTypedArray())
        return getCategories()
    }

    private fun dtoToEntity(category: InterestCategory) =
        CategoryWithInterests(
            InterestCategoryEntity(category.id, category.name),
            category.interestList.map { interest ->
                InterestEntity(
                    interest.id,
                    interest.name,
                    interest.categoryId
                )
            }
        )

    private fun entityToDto(entity: CategoryWithInterests) =
        InterestCategory(
            entity.interestCategoryEntity.id,
            entity.interestCategoryEntity.name,
            entity.interests.map { interestEntity ->
                Interest(
                    interestEntity.id,
                    interestEntity.name,
                    interestEntity.categoryId
                )
            }
        )
}