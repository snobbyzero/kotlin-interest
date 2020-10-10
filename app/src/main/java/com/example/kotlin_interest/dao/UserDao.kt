package com.example.kotlin_interest.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.kotlin_interest.entity.*
import com.example.kotlin_interest.model.User

@Dao
abstract class UserDao {

    @Transaction
    @Query("SELECT * FROM User")
    abstract fun getUsers() : LiveData<List<UserWithInterests>>

    @Transaction
    @Query("SELECT * FROM User u JOIN UserInterest ui ON u.id = ui.userId AND ui.interestId = :interestId")
    abstract suspend fun getUsersByInterest(interestId: Long) : List<UserWithInterests>

    suspend fun insert(users: List<UserWithInterests>) {
        for (u: UserWithInterests in users) {
            insertUser(u.user)
            u.interests.forEach {
                insertUserInterest(UserInterest(u.user.id, it.id))
                insertInterest(it)
            }
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertUser(user: UserEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertUserInterest(userInterest: UserInterest)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertInterest(interest: InterestEntity)

    @Delete
    abstract suspend fun delete(user: UserEntity)
}