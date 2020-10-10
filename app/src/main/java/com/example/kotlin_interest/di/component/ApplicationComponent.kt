package com.example.kotlin_interest.di.component

import android.app.Application
import com.bumptech.glide.Glide
import com.example.kotlin_interest.GlideModule
import com.example.kotlin_interest.di.MyApplication
import com.example.kotlin_interest.di.module.*
import dagger.BindsInstance
import dagger.Component
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
    AppModule::class,
    GlideBuilderModule::class,
    DatabaseModule::class
])
interface ApplicationComponent : AndroidInjector<MyApplication> {

    fun inject(application: Application)

    @Component.Builder
    interface Builder {
        fun build(): ApplicationComponent

        @BindsInstance
        fun applicationBind(application: Application): Builder
    }


}