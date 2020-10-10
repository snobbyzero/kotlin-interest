package com.example.kotlin_interest.retrofit

import com.example.kotlin_interest.util.SessionManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenInterceptor @Inject constructor(private val sessionManager: SessionManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        if ((original.url().encodedPath().contains("/register") && original.method() == "post") ||
            (original.url().encodedPath().contains("/auth/token") && original.method() == "post") ||
            (original.url().encodedPath().contains("/auth/refresh-token") && original.method() == "post") ||
            (original.url().encodedPath().contains("/validation/username") && original.method() == "get") ||
            (original.url().encodedPath().contains("/validation/email") && original.method() == "get")
        ) {
            return chain.proceed(original)
        }

        val originalHttpUrl = original.url()
        val requestBuilder = original.newBuilder()
            .url(originalHttpUrl)
        sessionManager.getAccessToken().let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}