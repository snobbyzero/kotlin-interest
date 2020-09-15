package com.example.kotlin_interest.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.kotlin_interest.dao.InterestCategoryDao
import com.example.kotlin_interest.entity.InterestCategoryEntity
import com.example.kotlin_interest.entity.InterestEntity
import com.example.kotlin_interest.model.InterestCategory

@Database(entities = [
    InterestCategoryEntity::class,
    InterestEntity::class
],
version = 1,
exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun interestCategoryDao() : InterestCategoryDao
}