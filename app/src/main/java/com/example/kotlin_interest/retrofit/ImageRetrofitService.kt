package com.example.kotlin_interest.retrofit

import okhttp3.MultipartBody
import retrofit2.http.*
import java.io.File

interface ImageRetrofitService {

    @Multipart
    @POST("user/{userId}/image/{token}")
    suspend fun postSaveImage(@Path("userId") userId: Long, @Path("token") token: String, @Part file: MultipartBody.Part)
}