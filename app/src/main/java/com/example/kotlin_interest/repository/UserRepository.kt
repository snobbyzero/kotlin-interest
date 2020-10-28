package com.example.kotlin_interest.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.example.kotlin_interest.dao.UserDao
import com.example.kotlin_interest.entity.InterestEntity
import com.example.kotlin_interest.entity.UserEntity
import com.example.kotlin_interest.entity.UserInterest
import com.example.kotlin_interest.entity.UserWithInterests
import com.example.kotlin_interest.model.Interest
import com.example.kotlin_interest.model.User
import com.example.kotlin_interest.retrofit.UserRetrofitService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userRetrofitService: UserRetrofitService,
    private val userDao: UserDao
) {

    suspend fun getUsersByInterest(interestIds: List<Long>): List<UserWithInterests> {
        return userDao.getUsersByInterests(interestIds)
    }

    suspend fun insert(id: Long, interestIds: List<Long>) {
        val users = userRetrofitService.getUsersByInterests(id, interestIds)
        userDao.insert(users.map { dtoToEntity(it) })
    }

    suspend fun delete(user: UserEntity) {
        userDao.delete(user)
    }

    private fun dtoToEntity(user: User) = UserWithInterests(
        UserEntity(
            user.id,
            user.username,
            user.email,
            user.sex,
            user.description,
            user.age,
            user.imageToken
        ),
        user.interests.map { interest ->
            InterestEntity(
                interest.id,
                interest.name,
                interest.categoryId
            )
        }
    )
}