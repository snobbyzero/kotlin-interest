package com.example.kotlin_interest.di.module

import com.example.kotlin_interest.view.fragment.login.LoginInfo
import dagger.Module
import dagger.Provides

@Module
class LoginInfoModule {

    @Provides
    fun loginInfo() : LoginInfo = LoginInfo("", "")
}