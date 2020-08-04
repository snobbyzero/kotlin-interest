package com.example.kotlin_interest.view.fragment.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.example.kotlin_interest.R
import com.example.kotlin_interest.databinding.FragmentLoginBinding
import com.example.kotlin_interest.di.component.DaggerApplicationComponent
import com.example.kotlin_interest.view.activity.MainActivity
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject
import kotlin.math.log

class LoginFragment : Fragment() {

    @Inject lateinit var modelFactory: ViewModelProvider.Factory
    private lateinit var loginViewModel: LoginViewModel
    @Inject lateinit var logInfo: LoginInfo

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentLoginBinding>(inflater, R.layout.fragment_login, container, false)

        loginViewModel = ViewModelProvider(this, modelFactory)[LoginViewModel::class.java]
        observe(binding.root)

        binding.apply {
            loginInfo = logInfo
            loginButton.setOnClickListener { loginViewModel.signIn(logInfo, "fingerprint") }
        }


        return binding.root
    }


    private fun observe(view: View) {
        loginViewModel.getTokenResponse().observe(viewLifecycleOwner, Observer {
            it?.let {
                startActivity(Intent(activity, MainActivity::class.java))
            }
        })

        loginViewModel.getErrorMsg().observe(viewLifecycleOwner, Observer {
            it?.let {
                Snackbar.make(view, it, Snackbar.LENGTH_SHORT)
            }
        })
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }
}
