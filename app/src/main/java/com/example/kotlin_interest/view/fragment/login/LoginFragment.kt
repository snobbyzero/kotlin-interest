package com.example.kotlin_interest.view.fragment.login

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.kotlin_interest.R
import com.example.kotlin_interest.databinding.FragmentLoginBinding
import com.example.kotlin_interest.util.SessionManager
import com.example.kotlin_interest.util.Status
import com.example.kotlin_interest.view.activity.MainActivity
import com.example.kotlin_interest.view.fragment.main_information.MainInformationFragment
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.schedule

class LoginFragment : DaggerFragment() {

    @Inject
    lateinit var modelFactory: ViewModelProvider.Factory
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: FragmentLoginBinding

    @Inject
    lateinit var sessionManager: SessionManager

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

        setupUI()

        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupUI() {
        binding.apply {
            signinButton.setOnClickListener {
                loginViewModel!!.getTokenResponse().observe(viewLifecycleOwner, Observer {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                if (loginViewModel!!.checkResponse(resource.data)) {
                                    startActivity(
                                        Intent(
                                            activity,
                                            MainActivity::class.java
                                        )
                                    )
                                }
                                binding.apply {
                                    progressBar.visibility = View.GONE
                                }
                            }
                            Status.ERROR -> {
                                binding.apply {
                                    progressBar.visibility = View.GONE
                                    Snackbar.make(
                                        requireView(),
                                        it.message.toString(),
                                        Snackbar.LENGTH_SHORT
                                    ).show()
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


            signupTextView.setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                    if (event?.action == MotionEvent.ACTION_UP) {
                        val manager = requireActivity().supportFragmentManager
                        val tag = "reg"
                        if (manager.findFragmentByTag(tag) == null) {
                            manager.beginTransaction()
                                .addToBackStack(tag)
                                .replace(
                                    R.id.fragmentContent,
                                    MainInformationFragment.newInstance()
                                )
                                .commit()
                        }
                    }
                    return true
                }
            })

            usernameEditText.editText?.addTextChangedListener(object : TextWatcher {
                var timer = Timer()
                val delay = 1000L
                override fun afterTextChanged(p0: Editable?) {
                    timer.cancel()
                    timer = Timer()
                    timer.schedule(delay) {
                        loginViewModel!!.checkUsername()
                    }
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            })

            passwordEditText.editText?.addTextChangedListener(object : TextWatcher {
                var timer = Timer()
                val delay = 1000L
                override fun afterTextChanged(p0: Editable?) {
                    timer.cancel()
                    timer = Timer()
                    timer.schedule(delay) {
                        loginViewModel!!.checkPassword()
                    }
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            })

            loginViewModel = this@LoginFragment.loginViewModel
            lifecycleOwner = this@LoginFragment
        }
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
