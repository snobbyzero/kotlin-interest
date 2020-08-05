package com.example.kotlin_interest.di.module

import com.example.kotlin_interest.di.annotation.FragmentScope
import com.example.kotlin_interest.view.fragment.home.HomeFragment
import com.example.kotlin_interest.view.fragment.login.LoginFragment
import com.example.kotlin_interest.view.fragment.register.RegisterFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributesLoginFragment() : LoginFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributesRegisterFragment() : RegisterFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributesHomeFragment() : HomeFragment
}