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
import com.example.kotlin_interest.view.activity.MainActivity
import com.example.kotlin_interest.view.fragment.dialogs.DialogsFragment
import com.example.kotlin_interest.view.fragment.image_picker.ImagePickerFragment
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

        binding.nextButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                descriptionViewModel.register(user)
            }
        }

        observe()

        return binding.root
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    companion object {
        fun newInstance() = DescriptionFragment()
    }

    private fun observe() {
        descriptionViewModel.getTokenResponse().observe(viewLifecycleOwner, Observer {
            it?.let {
                val manager = requireActivity().supportFragmentManager
                val tag = "desc"
                if (manager.findFragmentByTag(tag) == null) {
                    val imagePickerFragment = ImagePickerFragment.newInstance()
                    manager.beginTransaction()
                        .addToBackStack(tag)
                        .replace(R.id.fragmentContent, imagePickerFragment)
                        .commit()
                }
            }
        })

        descriptionViewModel.getErrorMsg().observe(viewLifecycleOwner, Observer {
            it?.let {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
