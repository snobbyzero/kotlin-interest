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
import com.example.kotlin_interest.databinding.FragmentFiltersBinding
import com.example.kotlin_interest.view.fragment.filters.FiltersViewModel
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class FiltersFragment : DaggerFragment() {

    @Inject
    lateinit var modelFactory: ViewModelProvider.Factory
    private lateinit var filtersViewModel: FiltersViewModel

    private lateinit var binding: FragmentFiltersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentFiltersBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        filtersViewModel = ViewModelProvider(this, modelFactory)[FiltersViewModel::class.java]

        return binding.root
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    companion object {
        fun newInstance() = FiltersFragment()
    }


}
