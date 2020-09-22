package com.example.kotlin_interest.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.kotlin_interest.model.Interest
import com.example.kotlin_interest.model.User

class UserWithInterests (
    @Embedded val user: UserEntity,
    @Relation(
        parentColumn = "id",
        entity = InterestEntity::class,
        entityColumn = "id",
        associateBy = Junction(
            value = UserInterest::class,
            parentColumn = "userId",
            entityColumn = "interestId"
        )
    )
    val interests: List<InterestEntity>
) {
}