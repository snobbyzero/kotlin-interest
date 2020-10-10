package com.example.kotlin_interest.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
class UserEntity (
    @PrimaryKey val id: Long,
    val username: String,
    val email: String?,
    val sex: String,
    val description: String,
    val age: Int,
    val imageToken: String
) {

}