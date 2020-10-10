package com.example.kotlin_interest.di

import android.app.Application
import com.example.kotlin_interest.GlideModule
import com.example.kotlin_interest.di.component.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject


class MyApplication : Application(), HasAndroidInjector {
    @Inject lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        DaggerApplicationComponent.builder()
            .applicationBind(this)
            .build()
            .inject(this)

    }

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector
}