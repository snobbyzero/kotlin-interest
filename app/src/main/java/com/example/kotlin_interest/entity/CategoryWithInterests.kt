package com.example.kotlin_interest.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import com.example.kotlin_interest.model.Interest
import com.example.kotlin_interest.model.InterestCategory

class CategoryWithInterests (
    @Embedded val interestCategoryEntity: InterestCategoryEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "categoryId",
        entity = InterestEntity::class
    )
    val interests: List<InterestEntity>
) {
}