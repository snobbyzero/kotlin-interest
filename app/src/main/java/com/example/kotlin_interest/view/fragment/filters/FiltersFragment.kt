package com.example.kotlin_interest.view.fragment.dialogs

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.kotlin_interest.R
import com.example.kotlin_interest.databinding.FragmentDialogsBinding
import com.example.kotlin_interest.databinding.FragmentFiltersBinding
import com.example.kotlin_interest.view.fragment.filters.FiltersViewModel
import com.example.kotlin_interest.view.fragment.filters.InterestAdapter
import com.example.kotlin_interest.view.fragment.home.HomeFragment
import com.example.kotlin_interest.view.interests_adapter.UserInterestsAdapter
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

        binding = FragmentFiltersBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        filtersViewModel = ViewModelProvider(this, modelFactory)[FiltersViewModel::class.java]

        setupUI()

        return binding.root
    }

    private fun setupUI() {
        setupRecyclerView()
        binding.applyButton.setOnClickListener {
            // TODO apply filters
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(
                    R.id.container,
                    HomeFragment.newInstance(),
                    HomeFragment::class.java.simpleName
                )
                .commit()
        }
    }

    private fun setupRecyclerView() {
        val interestsAdapter = InterestAdapter(filtersViewModel.getInterests(), filtersViewModel.getCheckedInterests())
        with(binding.interestsRecyclerView) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = interestsAdapter
        }
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    companion object {
        fun newInstance() = FiltersFragment()
    }


}
