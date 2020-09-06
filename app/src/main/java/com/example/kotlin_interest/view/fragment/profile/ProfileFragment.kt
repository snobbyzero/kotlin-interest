package com.example.kotlin_interest.view.fragment.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.signature.ObjectKey
import com.example.kotlin_interest.GlideApp
import com.example.kotlin_interest.R
import com.example.kotlin_interest.databinding.FragmentProfileBinding
import com.example.kotlin_interest.util.NetworkUtil
import com.example.kotlin_interest.view.activity.LoginActivity
import com.example.kotlin_interest.view.fragment.image_picker.ImagePickerFragment
import com.google.android.flexbox.FlexboxLayoutManager
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ProfileFragment @Inject constructor(): DaggerFragment() {

    @Inject
    lateinit var modelFactory: ViewModelProvider.Factory
    private lateinit var profileViewModel: ProfileViewModel

    private lateinit var binding: FragmentProfileBinding

    private lateinit var onInterestClickListener: View.OnClickListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProfileBinding.inflate(inflater, container, false)
        profileViewModel = ViewModelProvider(this, modelFactory)[ProfileViewModel::class.java]

        binding.profileViewModel = profileViewModel
        with (binding) {
            logoutButton.setOnClickListener { logout() }
            imageButton.setOnClickListener { changeImage() }
            usernameTextView.setOnClickListener { changeUsername() }
            descriptionTextView.setOnClickListener { changeDescription() }
            descriptionDescTextView.setOnClickListener { changeDescription() }
            interestsDescTextView.setOnClickListener { changeInterests() }
        }
        onInterestClickListener = View.OnClickListener {
            changeInterests()
        }
        setProfileImage()
        setupRecyclerView()

        return binding.root
    }

    private fun changeImage() {
        //profileViewModel.changeImage()
        val tag = "photo"
        requireActivity().supportFragmentManager.beginTransaction()
            .addToBackStack(tag)
            .replace(R.id.container, ImagePickerFragment.newInstance(), ImagePickerFragment::class.java.simpleName)
            .commit()
    }

    private fun changeUsername() {
        //profileViewModel.changeUsername()
    }

    private fun changeDescription() {
        //profileViewModel.changeDescription()
    }

    private fun changeInterests() {
        //profileViewModel.changeInterests()

    }

    private fun logout() {
        profileViewModel.logout()
        startActivity(Intent(requireActivity(), LoginActivity::class.java))
    }

    private fun setupRecyclerView() {
        val flexboxLayoutManager = FlexboxLayoutManager(context)
        with(flexboxLayoutManager) {
            flexWrap = com.google.android.flexbox.FlexWrap.WRAP
            flexDirection = com.google.android.flexbox.FlexDirection.ROW
            justifyContent = com.google.android.flexbox.JustifyContent.FLEX_START
            alignItems = com.google.android.flexbox.AlignItems.FLEX_START
        }
        val interestsAdapter = UserInterestsAdapter(arrayListOf(), onInterestClickListener)
        with (binding.interestsRecyclerView) {
            layoutManager = flexboxLayoutManager
            adapter = interestsAdapter
        }
        interestsAdapter.addInterests(profileViewModel.user!!.interests)
    }

    private fun setProfileImage() {
        GlideApp.with(binding.root)
            .load(profileViewModel.imagePath)
            .circleCrop()
            .signature(ObjectKey(profileViewModel.imageToken!!))
            .into(binding.imageButton)
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    companion object {
        fun newInstance() = ProfileFragment()
    }

}
