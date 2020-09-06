package com.example.kotlin_interest.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kotlin_interest.di.ViewModelFactory
import com.example.kotlin_interest.di.annotation.ViewModelKey
import com.example.kotlin_interest.view.fragment.description.DescriptionViewModel
import com.example.kotlin_interest.view.fragment.dialogs.DialogsViewModel
import com.example.kotlin_interest.view.fragment.home.HomeViewModel
import com.example.kotlin_interest.view.fragment.image_picker.ImagePickerViewModel
import com.example.kotlin_interest.view.fragment.interests.InterestsViewModel
import com.example.kotlin_interest.view.fragment.login.LoginViewModel
import com.example.kotlin_interest.view.fragment.profile.ProfileViewModel
import com.example.kotlin_interest.view.fragment.main_information.MainInformationViewModel
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
    @ViewModelKey(MainInformationViewModel::class)
    abstract fun bindRegisterViewModel(viewModel: MainInformationViewModel) : ViewModel

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

    @Binds
    @IntoMap
    @ViewModelKey(DescriptionViewModel::class)
    abstract fun bindDescriptionViewModel(viewModel: DescriptionViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ImagePickerViewModel::class)
    abstract fun bindImagePickerViewModel(viewModel: ImagePickerViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(InterestsViewModel::class)
    abstract fun bindInterestsViewModel(viewModel: InterestsViewModel) : ViewModel

}