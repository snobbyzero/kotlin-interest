package com.example.kotlin_interest.di.module

import com.example.kotlin_interest.di.annotation.FragmentScope
import com.example.kotlin_interest.view.fragment.description.DescriptionFragment
import com.example.kotlin_interest.view.fragment.dialogs.DialogsFragment
import com.example.kotlin_interest.view.fragment.home.HomeFragment
import com.example.kotlin_interest.view.fragment.image_picker.ImagePickerFragment
import com.example.kotlin_interest.view.fragment.interests.InterestsFragment
import com.example.kotlin_interest.view.fragment.login.LoginFragment
import com.example.kotlin_interest.view.fragment.main_information.MainInformationFragment
import com.example.kotlin_interest.view.fragment.profile.ProfileFragment
import com.example.kotlin_interest.view.fragment.change_username.ChangeUsernameFragment
import com.example.kotlin_interest.view.fragment.dialogs.FiltersFragment
import com.example.kotlin_interest.view.fragment.history.HistoryFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributesLoginFragment() : LoginFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributesRegisterFragment() : MainInformationFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributesHomeFragment() : HomeFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeDialogsFragment() : DialogsFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeProfileFragment() : ProfileFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeDescriptionFragment() : DescriptionFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeImagePickerFragment() : ImagePickerFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeInterestsFragment() : InterestsFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeChangeUsernameFragment() : ChangeUsernameFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeHistoryFragment() : HistoryFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeFiltersFragment() : FiltersFragment
}