package com.example.kotlin_interest.di.component

import com.example.kotlin_interest.di.MyApplication
import com.example.kotlin_interest.di.module.*
import com.example.kotlin_interest.view.fragment.login.LoginInfo
import dagger.BindsInstance
import dagger.Component
import dagger.Provides
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    NetworkModule::class,
    ViewModelModule::class,
    ActivityModule::class,
    FragmentModule::class,
    LoginInfoModule::class,
    AppModule::class
])
interface ApplicationComponent : AndroidInjector<MyApplication> {

    @Component.Builder
    interface Builder {
        fun build(): ApplicationComponent

        @BindsInstance
        fun applicationBind(application: MyApplication): Builder
    }



}