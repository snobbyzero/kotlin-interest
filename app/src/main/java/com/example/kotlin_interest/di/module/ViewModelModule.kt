package com.example.kotlin_interest.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kotlin_interest.di.ViewModelFactory
import com.example.kotlin_interest.di.annotation.ViewModelKey
import com.example.kotlin_interest.view.fragment.dialogs.DialogsViewModel
import com.example.kotlin_interest.view.fragment.home.HomeViewModel
import com.example.kotlin_interest.view.fragment.login.LoginViewModel
import com.example.kotlin_interest.view.fragment.profile.ProfileViewModel
import com.example.kotlin_interest.view.fragment.register.RegisterViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory) : ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(viewModel: LoginViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RegisterViewModel::class)
    abstract fun bindRegisterViewModel(viewModel: RegisterViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(viewModel: HomeViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DialogsViewModel::class)
    abstract fun bindDialogsViewModel(viewModel: DialogsViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    abstract fun bindProfileViewModel(viewModel: ProfileViewModel) : ViewModel

}