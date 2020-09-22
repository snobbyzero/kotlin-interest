package com.example.kotlin_interest.entity

import androidx.room.Entity

@Entity(
    tableName = "UserInterest",
    primaryKeys = ["userId", "interestId"]
)
class UserInterest (
    val userId: Long,
    val interestId: Long
) {}