package com.example.kotlin_interest.view.fragment.login

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.kotlin_interest.R
import com.example.kotlin_interest.databinding.FragmentLoginBinding
import com.example.kotlin_interest.util.SessionManager
import com.example.kotlin_interest.view.activity.MainActivity
import com.example.kotlin_interest.view.fragment.register.RegisterFragment
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class LoginFragment : DaggerFragment() {

    @Inject
    lateinit var modelFactory: ViewModelProvider.Factory
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: FragmentLoginBinding
    @Inject
    lateinit var sessionManager: SessionManager

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(
            inflater,
            container,
            false
        )

        loginViewModel = ViewModelProvider(this, modelFactory)[LoginViewModel::class.java]

        binding.apply {
            signinButton.setOnClickListener {
                loginViewModel?.signIn()
            }

            signupTextView.setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                    if (event?.action == MotionEvent.ACTION_UP) {
                        val manager = requireActivity().supportFragmentManager
                        val tag = "reg"
                        if (manager.findFragmentByTag(tag) == null) {
                            manager.beginTransaction()
                                .addToBackStack(tag)
                                .replace(R.id.fragmentContent, RegisterFragment.newInstance())
                                .commit()
                        }
                    }
                    return true
                }

            })

            loginViewModel = this@LoginFragment.loginViewModel
            lifecycleOwner = this@LoginFragment
        }
        observe()

        return binding.root
    }


    private fun observe() {
        loginViewModel.getTokenResponse().observe(viewLifecycleOwner, Observer {
            it?.let {
                startActivity(Intent(activity, MainActivity::class.java))
            }
        })

        loginViewModel.getErrorMsg().observe(viewLifecycleOwner, Observer {
            it?.let {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
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
