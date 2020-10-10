package com.example.kotlin_interest.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "InterestCategory")
class InterestCategoryEntity (
    @PrimaryKey val id: Long,
    val name: String
) {
}