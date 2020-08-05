package com.example.kotlin_interest.view.fragment.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.example.kotlin_interest.databinding.FragmentLoginBinding
import com.example.kotlin_interest.util.SessionManager
import com.example.kotlin_interest.view.activity.MainActivity
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class LoginFragment : DaggerFragment() {

    @Inject lateinit var modelFactory: ViewModelProvider.Factory
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: FragmentLoginBinding
    @Inject lateinit var logInfo: LoginInfo
    @Inject lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(
            inflater,
            container,
            false
        )
        binding.apply {
            loginInfo = logInfo
        }
        binding.loginButton.setOnClickListener {
            Snackbar.make(binding.root, logInfo.username, Snackbar.LENGTH_SHORT)
            loginViewModel.signIn(logInfo)
        }


        loginViewModel = ViewModelProvider(this, modelFactory)[LoginViewModel::class.java]
        observe(binding.root)

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
                Snackbar.make(view, it, Snackbar.LENGTH_SHORT).show()
            }
        })
    }

    private fun checkIfLoggedIn(sessionManager: SessionManager) {
        if (!sessionManager.getLoggedIn()) startActivity(Intent(activity, MainActivity::class.java))
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    companion object {
        @JvmStatic
        fun newInstance() = LoginFragment()
    }
}
