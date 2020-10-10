package com.example.kotlin_interest.view.fragment.description

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.kotlin_interest.R
import com.example.kotlin_interest.databinding.FragmentDescriptionBinding
import com.example.kotlin_interest.model.User
import com.example.kotlin_interest.view.activity.LoginActivity
import com.example.kotlin_interest.view.activity.MainActivity
import com.example.kotlin_interest.view.fragment.dialogs.DialogsFragment
import com.example.kotlin_interest.view.fragment.image_picker.ImagePickerFragment
import com.example.kotlin_interest.view.fragment.interests.InterestsFragment
import com.example.kotlin_interest.view.fragment.profile.ProfileFragment
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.coroutineContext


class DescriptionFragment : DaggerFragment(){

    @Inject
    lateinit var modelFactory: ViewModelProvider.Factory
    private lateinit var descriptionViewModel: DescriptionViewModel

    private lateinit var binding: FragmentDescriptionBinding
    private lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDescriptionBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        descriptionViewModel = ViewModelProvider(this, modelFactory)[DescriptionViewModel::class.java]
        binding.descriptionViewModel = descriptionViewModel

        val bundle = arguments
        bundle?.let {
            user = bundle.getSerializable("user") as User
        }

        setupUI()

        return binding.root
    }

    private fun setupUI() {
        binding.nextButton.setOnClickListener {
            when (activity) {
                is LoginActivity -> goToImagePicker()
                is MainActivity -> {
                    binding.nextButton.text = resources.getText(R.string.save)
                    descriptionViewModel.changeDescription()
                    goToProfileFragment()
                }
            }
        }
    }

    private fun goToImagePicker() {
        if (descriptionViewModel.checkDescription()) {
            val manager = requireActivity().supportFragmentManager
            val tag = "interests"
            if (manager.findFragmentByTag(tag) == null) {
                user.description = descriptionViewModel.description.value!!
                val interestsFragment = InterestsFragment.newInstance()
                val bundle = Bundle()
                bundle.putSerializable(
                    "user",
                    user
                )
                interestsFragment.arguments = bundle
                manager.beginTransaction()
                    .addToBackStack(tag)
                    .replace(R.id.fragmentContent, interestsFragment)
                    .commit()
            }
        }
    }

    private fun goToProfileFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container, ProfileFragment.newInstance(), ProfileFragment::class.java.simpleName)
            .commit()
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    companion object {
        fun newInstance() = DescriptionFragment()
    }

}
