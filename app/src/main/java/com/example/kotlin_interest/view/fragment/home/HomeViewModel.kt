package com.example.kotlin_interest.view.fragment.home

import android.content.Context
import android.widget.ImageView
import androidx.lifecycle.*
import com.example.kotlin_interest.GlideApp
import com.example.kotlin_interest.entity.InterestEntity
import com.example.kotlin_interest.entity.UserEntity
import com.example.kotlin_interest.entity.UserWithInterests
import com.example.kotlin_interest.model.Match
import com.example.kotlin_interest.model.User
import com.example.kotlin_interest.repository.UserRepository
import com.example.kotlin_interest.retrofit.MatchRetrofitService
import com.example.kotlin_interest.util.NetworkUtil
import com.example.kotlin_interest.util.Resource
import com.example.kotlin_interest.util.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val networkUtil: NetworkUtil,
    private val matchRetrofitService: MatchRetrofitService,
    private val sessionManager: SessionManager
) : ViewModel() {
    val user = sessionManager.getUser()!!
    var currentUser: UserWithInterests? = null
    var users: ArrayList<UserWithInterests> = ArrayList()
    val usersLoaded = MutableLiveData(false)
    val matchesResult = MutableLiveData<HashMap<UserWithInterests, String>>()
    private var count = 0

    init {
        matchesResult.value = HashMap()
    }

    suspend fun setUsers(interestId: Long) {
        // get list from db
        usersLoaded.postValue(false)
        users.addAll(userRepository.getUsersByInterest(interestId))
        usersLoaded.postValue(true)
    }

    fun getUsersFromDb(interestId: Long) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = userRepository.insert(
                        user.id,
                        interestId
                    )
                )
            )
        } catch (ex: Exception) {
            emit(Resource.error(data = null, message = ex.message ?: "Error occurred!"))
        }
    }

    fun getImageUrl(userId: Long) = networkUtil.getUserImageUrl(userId)

    fun getNewUser(): UserWithInterests? {
        currentUser = if (count >= users.size) {
            null
        } else {
            users[count++]
        }
        return currentUser
    }

    fun addMatch(): String? {
        currentUser?.let {
            val likedId = it.user.id
            viewModelScope.launch {
                matchesResult.value!![it] = matchRetrofitService.addMatch(
                    Match(null, user.id, likedId)
                )
                matchesResult.value = matchesResult.value
                // TODO переместить удаление и удалять картинку из кэша
                userRepository.delete(it.user)
            }
        }
        return null
    }

    fun dislike() {

    }

}