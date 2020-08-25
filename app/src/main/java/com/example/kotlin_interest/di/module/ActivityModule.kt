package com.example.kotlin_interest.di.module

import com.example.kotlin_interest.di.annotation.ActivityScope
import com.example.kotlin_interest.view.activity.LoginActivity
import com.example.kotlin_interest.view.activity.MainActivity
import com.example.kotlin_interest.view.activity.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ActivityScope
    @ContributesAndroidInjector
    abstract fun contributeMainActivityAndroidInjector() : MainActivity

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun contributeLoginActivityAndroidInjector() : LoginActivity

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun contributeSplashActivityAndroidInjector() : SplashActivity

}