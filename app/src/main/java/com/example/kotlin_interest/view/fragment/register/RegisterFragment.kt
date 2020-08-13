package com.example.kotlin_interest.view.fragment.register

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider

import com.example.kotlin_interest.R
import com.example.kotlin_interest.databinding.FragmentLoginBinding
import com.example.kotlin_interest.databinding.FragmentRegisterBinding
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class RegisterFragment : DaggerFragment() {

    @Inject
    lateinit var modelFactory: ViewModelProvider.Factory
    private lateinit var registerViewModel: RegisterViewModel

    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)

        registerViewModel = ViewModelProvider(this, modelFactory)[RegisterViewModel::class.java]

        return binding.root
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    companion object {
        @JvmStatic
        fun newInstance() = RegisterFragment()
    }
}
