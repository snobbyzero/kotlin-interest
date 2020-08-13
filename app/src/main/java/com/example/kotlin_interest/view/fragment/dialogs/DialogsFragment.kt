package com.example.kotlin_interest.view.fragment.dialogs

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider

import com.example.kotlin_interest.R
import com.example.kotlin_interest.databinding.FragmentDialogsBinding
import com.example.kotlin_interest.databinding.FragmentLoginBinding
import com.example.kotlin_interest.view.fragment.home.HomeFragment
import com.example.kotlin_interest.view.fragment.home.HomeViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class DialogsFragment : Fragment() {

    @Inject
    lateinit var modelFactory: ViewModelProvider.Factory
    private lateinit var dialogsViewModel: DialogsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentDialogsBinding>(inflater, R.layout.fragment_dialogs, container, false)

        dialogsViewModel = ViewModelProvider(this, modelFactory)[DialogsViewModel::class.java]

        return binding.root
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    companion object {
        fun newInstance() = DialogsFragment()
    }

}
