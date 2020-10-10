package com.example.kotlin_interest.di.module

import com.bumptech.glide.integration.okhttp3.OkHttpLibraryGlideModule
import com.example.kotlin_interest.GlideModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class GlideBuilderModule {

    @ContributesAndroidInjector
    abstract fun bind(): GlideModule
}