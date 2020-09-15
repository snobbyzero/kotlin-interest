package com.example.kotlin_interest.view.fragment.change_username

import android.content.Context
import android.graphics.drawable.Animatable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.kotlin_interest.databinding.FragmentChangeUsernameBinding
import com.example.kotlin_interest.util.getProgressBarDrawable
import com.google.android.material.textfield.TextInputLayout
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.schedule
import androidx.lifecycle.Observer
import com.example.kotlin_interest.R
import com.example.kotlin_interest.view.fragment.profile.ProfileFragment

class ChangeUsernameFragment : DaggerFragment() {

    @Inject
    lateinit var modelFactory: ViewModelProvider.Factory
    private lateinit var changeUsernameViewModel: ChangeUsernameViewModel
    private lateinit var binding: FragmentChangeUsernameBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentChangeUsernameBinding.inflate(inflater, container, false)

        changeUsernameViewModel = ViewModelProvider(this, modelFactory)[ChangeUsernameViewModel::class.java]

        setupUI()
        observe()

        return binding.root
    }

    private fun setupUI() {
        binding.apply {
            changeUsernameViewModel = this@ChangeUsernameFragment.changeUsernameViewModel
            lifecycleOwner = this@ChangeUsernameFragment

            setProgressBar(usernameEditText)

            usernameEditText.editText?.addTextChangedListener(object : TextWatcher {
                var timer = Timer()
                val delay = 1000L
                override fun afterTextChanged(p0: Editable?) {
                    timer.cancel()
                    timer = Timer()
                    timer.schedule(delay) {
                        changeUsernameViewModel!!.checkUsername()
                    }
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            })

            nextButton.setOnClickListener {
                changeUsernameViewModel!!.changeUsername()
                goToProfileFragment()
            }
        }
    }

    private fun goToProfileFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container, ProfileFragment.newInstance(), ProfileFragment::class.java.simpleName)
            .commit()
    }

    private fun observe() {
        changeUsernameViewModel.getUsernameCheckCompleted().observe(viewLifecycleOwner, Observer {
            when (it) {
                true -> {
                    if (changeUsernameViewModel.usernameError.value == "") {
                        binding.nextButton.isEnabled = true
                    }
                    stopProgressBar(binding.usernameEditText)
                }
                false -> {
                    binding.nextButton.isEnabled = false
                    startProgressBar(binding.usernameEditText)
                }
            }
        })
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
            this.requireContext().getProgressBarDrawable()
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    companion object {
        fun newInstance() = ChangeUsernameFragment()
    }

}
