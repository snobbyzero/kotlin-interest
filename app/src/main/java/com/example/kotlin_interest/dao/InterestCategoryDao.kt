package com.example.kotlin_interest.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.kotlin_interest.entity.CategoryWithInterests
import com.example.kotlin_interest.entity.InterestCategoryEntity
import com.example.kotlin_interest.entity.InterestEntity
import com.example.kotlin_interest.model.Interest
import com.example.kotlin_interest.model.InterestCategory

@Dao
abstract class InterestCategoryDao {

    @Transaction
    @Query("SELECT * FROM InterestCategory")
    abstract suspend fun getCategoriesWithInterests(): List<CategoryWithInterests>


    suspend fun insert(vararg interestCategories: CategoryWithInterests) {
        for (category: CategoryWithInterests in interestCategories) {
            insertCategory(category.interestCategoryEntity)
            insertInterests(category.interests)
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertCategory(categoryEntity: InterestCategoryEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertInterests(interests: List<InterestEntity>)


}