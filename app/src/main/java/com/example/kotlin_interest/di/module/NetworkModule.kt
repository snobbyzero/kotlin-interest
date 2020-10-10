package com.example.kotlin_interest.di.module

import com.example.kotlin_interest.retrofit.*
import com.example.kotlin_interest.util.SessionManager
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        scalarsConverterFactory: ScalarsConverterFactory,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit = Retrofit.Builder()
        .baseUrl("https://interest-project.herokuapp.com")
        .client(okHttpClient)
        .addConverterFactory(scalarsConverterFactory)
        .addConverterFactory(gsonConverterFactory)
        .build()

    @Provides
    @Singleton
    fun provideOkHttp(
        tokenInterceptor: TokenInterceptor,
        tokenAuthenticator: TokenAuthenticator
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .authenticator(tokenAuthenticator)
            .addInterceptor(tokenInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideScalarsConverterFactory() = ScalarsConverterFactory.create()

    @Singleton
    @Provides
    fun provideLoginRetrofitService(retrofit: Retrofit): LoginRetrofitService =
        retrofit.create(LoginRetrofitService::class.java)

    @Singleton
    @Provides
    fun provideValidationRetrofitService(retrofit: Retrofit): ValidationRetrofitService =
        retrofit.create(ValidationRetrofitService::class.java)

    @Singleton
    @Provides
    fun provideImageRetrofitService(retrofit: Retrofit): ImageRetrofitService =
        retrofit.create(ImageRetrofitService::class.java)

    @Singleton
    @Provides
    fun provideInterestsRetrofitService(retrofit: Retrofit): InterestsRetrofitService =
        retrofit.create(InterestsRetrofitService::class.java)

    @Singleton
    @Provides
    fun provideUsernameRetrofitService(retrofit: Retrofit): UserRetrofitService =
        retrofit.create(UserRetrofitService::class.java)

    @Singleton
    @Provides
    fun provideMatchRetrofitService(retrofit: Retrofit): MatchRetrofitService =
        retrofit.create(MatchRetrofitService::class.java)
}