package com.example.kotlin_interest.view.fragment.history

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.kotlin_interest.databinding.FragmentHistoryBinding

import com.example.kotlin_interest.databinding.FragmentHomeBinding
import com.example.kotlin_interest.databinding.FragmentLoginBinding
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class HistoryFragment : DaggerFragment() {

    @Inject
    lateinit var modelFactory: ViewModelProvider.Factory
    private lateinit var historyViewModel: HistoryViewModel

    private lateinit var binding: FragmentHistoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHistoryBinding.inflate(inflater, container, false)

        historyViewModel = ViewModelProvider(this, modelFactory)[HistoryViewModel::class.java]

        return binding.root
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    companion object {
        fun newInstance() = HistoryFragment()
    }

}
