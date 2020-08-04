package com.example.kotlin_interest.retrofit

import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenAuthenticator: Authenticator {
    @Inject lateinit var loginRetrofitService: LoginRetrofitService

    override fun authenticate(route: Route?, response: Response): Request? {
        newJwt =
        //this is your new token
        return response.request().newBuilder().header("Authorization", "Bearer " + newToken).build()
    }
}