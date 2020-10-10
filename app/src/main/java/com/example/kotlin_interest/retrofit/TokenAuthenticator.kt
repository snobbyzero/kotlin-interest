package com.example.kotlin_interest.retrofit

import com.example.kotlin_interest.model.JwtTokens
import com.example.kotlin_interest.util.SessionManager
import dagger.Lazy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenAuthenticator @Inject constructor(val sessionManager: SessionManager, private val loginRetrofitService: Lazy<LoginRetrofitService>): Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        val token = runBlocking { getNewToken() }
        return response.request().newBuilder().header("Authorization", "Bearer " + token?.accessToken).build()
    }


    private suspend fun getNewToken() : JwtTokens? {
        var token: JwtTokens? = null
        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            val jwtResponse = loginRetrofitService.get().updateTokens(
                sessionManager.getRefreshToken(),
                sessionManager.getFingerprint()
            )
            if (jwtResponse.isSuccessful) {
                token = jwtResponse.body()
                sessionManager.saveTokens(token!!)
            }
        }
        return token
    }
}