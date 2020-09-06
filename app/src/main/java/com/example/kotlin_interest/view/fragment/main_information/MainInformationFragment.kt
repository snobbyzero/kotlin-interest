package com.example.kotlin_interest.view.fragment.main_information

import android.content.Context
import android.graphics.drawable.Animatable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.kotlin_interest.R
import com.example.kotlin_interest.databinding.FragmentRegisterBinding
import com.example.kotlin_interest.util.getProgressBarDrawable
import com.example.kotlin_interest.view.fragment.description.DescriptionFragment
import com.google.android.material.textfield.TextInputLayout
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.schedule

class MainInformationFragment : DaggerFragment() {

    @Inject
    lateinit var modelFactory: ViewModelProvider.Factory
    private lateinit var mainInformationViewModel: MainInformationViewModel

    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)

        mainInformationViewModel = ViewModelProvider(this, modelFactory)[MainInformationViewModel::class.java]

        setupUI()

        observe()

        return binding.root
    }

    private fun setupUI() {
        binding.apply {
            setProgressBar(usernameEditText)
            setProgressBar(emailEditText)

            usernameEditText.editText?.addTextChangedListener(object : TextWatcher {
                var timer = Timer()
                val delay = 1000L
                override fun afterTextChanged(p0: Editable?) {
                    timer.cancel()
                    timer = Timer()
                    timer.schedule(delay) {
                        registerViewModel!!.checkUsername()
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
                        registerViewModel!!.checkPassword()
                    }
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            })

            emailEditText.editText?.addTextChangedListener(object : TextWatcher {
                var timer = Timer()
                val delay = 1000L
                override fun afterTextChanged(p0: Editable?) {
                    timer.cancel()
                    timer = Timer()
                    timer.schedule(delay) {
                        registerViewModel!!.checkEmail()
                    }
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            })

            ageEditText.editText?.addTextChangedListener(object : TextWatcher {
                var timer = Timer()
                val delay = 1000L
                override fun afterTextChanged(p0: Editable?) {
                    timer.cancel()
                    timer = Timer()
                    timer.schedule(delay) {
                        registerViewModel!!.checkAge()
                    }
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            })

            registerViewModel = this@MainInformationFragment.mainInformationViewModel
            lifecycleOwner = this@MainInformationFragment

            nextButton.setOnClickListener { onNextButtonClicked() }
        }
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainInformationFragment()

        @BindingAdapter("app:error")
        @JvmStatic
        fun setErrorMessage(view: TextInputLayout, error: String?) {
            view.error = error ?: ""
        }

    }

    private fun startProgressBar(textInputLayout: TextInputLayout) {
        (textInputLayout.endIconDrawable as? Animatable)?.start()
    }

    private fun stopProgressBar(textInputLayout: TextInputLayout) {
        (textInputLayout.endIconDrawable as? Animatable)?.stop()
    }

    private fun setProgressBar(textInputLayout: TextInputLayout) {
        textInputLayout.endIconMode = TextInputLayout.END_ICON_CUSTOM
        textInputLayout.endIconDrawable =
            this@MainInformationFragment.requireContext().getProgressBarDrawable()
    }

    private fun observe() {
        mainInformationViewModel.getUsernameCheckCompleted().observe(viewLifecycleOwner, Observer {
            when (it) {
                true -> stopProgressBar(binding.usernameEditText)
                false -> startProgressBar(binding.usernameEditText)
            }
        })

        mainInformationViewModel.getEmailCheckCompleted().observe(viewLifecycleOwner, Observer {
            when (it) {
                true -> stopProgressBar(binding.emailEditText)
                false -> startProgressBar(binding.emailEditText)
            }
        })
    }

    private fun onNextButtonClicked() {
        if (this@MainInformationFragment.mainInformationViewModel.checkAllFields()) {
            val manager = requireActivity().supportFragmentManager
            val tag = "desc"
            if (manager.findFragmentByTag(tag) == null) {
                val descriptionFragment = DescriptionFragment.newInstance()
                val bundle = Bundle()
                bundle.putSerializable(
                    "user",
                    this@MainInformationFragment.mainInformationViewModel.getUser()
                )
                descriptionFragment.arguments = bundle
                manager.beginTransaction()
                    .addToBackStack(tag)
                    .replace(R.id.fragmentContent, descriptionFragment)
                    .commit()
            }
        }
    }
}
