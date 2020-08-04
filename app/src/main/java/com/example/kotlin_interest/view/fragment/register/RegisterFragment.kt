package com.example.kotlin_interest.view.fragment.register

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider

import com.example.kotlin_interest.R
import com.example.kotlin_interest.databinding.FragmentLoginBinding
import com.example.kotlin_interest.view.fragment.login.LoginFragment
import com.example.kotlin_interest.view.fragment.login.LoginViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class RegisterFragment : Fragment() {

    @Inject
    lateinit var modelFactory: ViewModelProvider.Factory
    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentLoginBinding>(inflater, R.layout.fragment_register, container, false)

        registerViewModel = ViewModelProvider(this, modelFactory)[RegisterViewModel::class.java]

        return binding.root
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

}
