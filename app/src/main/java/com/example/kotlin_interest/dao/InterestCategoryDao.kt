package com.example.kotlin_interest.dao

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
    abstract fun getCategoriesWithInterests(): List<CategoryWithInterests>

    fun getCategories() = getCategoriesWithInterests().map { entityToDto(it) }

    fun insert(vararg interestCategories: InterestCategory) {
        val entities = interestCategories.asList().map { dtoToEntity(it) }
        for (category: CategoryWithInterests in entities) {
            insert(category.interestCategoryEntity)
            insert(category.interests)
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(categoryEntity: InterestCategoryEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insert(interests: List<InterestEntity>)

    private fun dtoToEntity(category: InterestCategory) =
        CategoryWithInterests(
            InterestCategoryEntity(category.id, category.name),
            category.interestList.map { interest ->
                InterestEntity(
                    interest.id,
                    interest.name,
                    category.id
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
                    interestEntity.name
                )
            }
        )
}