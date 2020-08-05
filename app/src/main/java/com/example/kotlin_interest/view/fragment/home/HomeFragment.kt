package com.example.kotlin_interest.view.fragment.home

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.example.kotlin_interest.R
import com.example.kotlin_interest.databinding.FragmentLoginBinding
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : DaggerFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentLoginBinding>(
            inflater,
            R.layout.fragment_login,
            container,
            false
        )

        return binding.root
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

}
