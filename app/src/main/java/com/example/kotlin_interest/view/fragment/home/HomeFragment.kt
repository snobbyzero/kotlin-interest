package com.example.kotlin_interest.view.fragment.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.signature.ObjectKey
import com.example.kotlin_interest.GlideApp

import com.example.kotlin_interest.databinding.FragmentHomeBinding
import com.example.kotlin_interest.databinding.FragmentLoginBinding
import com.example.kotlin_interest.entity.UserEntity
import com.example.kotlin_interest.entity.UserWithInterests
import com.example.kotlin_interest.interests_adapter.UserInterestsAdapter
import com.example.kotlin_interest.model.Interest
import com.example.kotlin_interest.model.User
import com.example.kotlin_interest.util.Status
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.*
import javax.inject.Inject

class HomeFragment : DaggerFragment() {

    @Inject
    lateinit var modelFactory: ViewModelProvider.Factory
    private lateinit var homeViewModel: HomeViewModel

    private lateinit var binding: FragmentHomeBinding

    private var interestsAdapter: UserInterestsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false)

        homeViewModel = ViewModelProvider(this, modelFactory)[HomeViewModel::class.java]

        binding.homeViewModel = homeViewModel

        setupUI()

        getUsers(171)

        return binding.root
    }

    private fun setupUI() {
        with (binding) {
            likeButton.setOnClickListener {
                this@HomeFragment.homeViewModel.addMatch()
                loadUser()
            }

            dislikeButton.setOnClickListener {
                loadUser()
            }
        }
    }


    private fun getUsers(interestId: Long) {
        homeViewModel.getUsersFromDb(interestId).observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.apply {
                            progressBar.visibility = View.GONE
                        }
                        lifecycleScope.launch {
                            // Add users from db
                            homeViewModel.setUsers(interestId)
                            // Load first user
                            loadUser()
                            // Preload pic for each user
                            homeViewModel.users.forEach { u -> preloadPic(u.user) }
                        }
                    }
                    Status.ERROR -> {
                        binding.apply {
                            progressBar.visibility = View.GONE
                            Snackbar.make(requireView(), it.message.toString(), Snackbar.LENGTH_SHORT).show()
                        }
                    }
                    Status.LOADING -> {
                        binding.apply {
                            progressBar.visibility = View.VISIBLE
                        }
                    }
                }
            }
        })
    }

    private fun loadUser() {
        val u = homeViewModel.getNewUser()
        if (u != null) {
            with(binding) {
                usernameTextView.text = "${u.user.username}, ${u.user.age}"
                descriptionTextView.text = u.user.description
                setupInterestsList(u)
                loadPic(u.user)
            }
        } else {
            with(binding) {
                // TODO no users left
                usernameTextView.text = ""
                descriptionTextView.text = ""
                userImageView.setImageBitmap(null)
                interestsAdapter?.clearData()
            }
        }
    }

    private fun setupInterestsList(u: UserWithInterests) {
        val flexboxLayoutManager = FlexboxLayoutManager(context)
        with(flexboxLayoutManager) {
            flexWrap = com.google.android.flexbox.FlexWrap.WRAP
            flexDirection = com.google.android.flexbox.FlexDirection.ROW
            justifyContent = com.google.android.flexbox.JustifyContent.FLEX_START
            alignItems = com.google.android.flexbox.AlignItems.FLEX_START
        }
        interestsAdapter = UserInterestsAdapter(arrayListOf())
        with(binding.interestsRecyclerView) {
            layoutManager = flexboxLayoutManager
            adapter = interestsAdapter
        }
        interestsAdapter!!.addInterests(u.interests.map { Interest(it.id, it.name, it.categoryId) })
    }


    private fun preloadPic(userEntity: UserEntity) {
        GlideApp
            .with(requireContext())
            .load(homeViewModel.getImageUrl(userEntity.id))
            .signature(ObjectKey(userEntity.imageToken))
            .preload()
    }

    private fun loadPic(userEntity: UserEntity) {
        GlideApp
            .with(requireContext())
            .load(homeViewModel.getImageUrl(userEntity.id))
            .signature(ObjectKey(userEntity.imageToken))
            .thumbnail(0.1f)
            .into(binding.userImageView)
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    companion object {
        fun newInstance() = HomeFragment()
    }

}
