package com.example.kotlin_interest.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Interest")
class InterestEntity (
    @PrimaryKey val id: Long,
    val name: String,
    val categoryId: Long
) {
}