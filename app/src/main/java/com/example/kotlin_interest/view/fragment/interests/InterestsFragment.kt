package com.example.kotlin_interest.view.fragment.interests

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.CheckBox
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.kotlin_interest.R
import com.example.kotlin_interest.databinding.FragmentHomeBinding
import com.example.kotlin_interest.databinding.FragmentInterestsBinding
import com.example.kotlin_interest.model.Interest
import com.example.kotlin_interest.model.InterestCategory
import com.example.kotlin_interest.model.User
import com.example.kotlin_interest.util.Status
import com.example.kotlin_interest.view.fragment.home.HomeFragment
import com.example.kotlin_interest.view.fragment.home.HomeViewModel
import com.example.kotlin_interest.view.fragment.image_picker.ImagePickerFragment
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class InterestsFragment : DaggerFragment() {

    @Inject
    lateinit var modelFactory: ViewModelProvider.Factory
    private lateinit var interestsViewModel: InterestsViewModel

    private lateinit var binding: FragmentInterestsBinding
    private lateinit var categoriesAdapter: CategoriesAdapter

    private lateinit var user: User


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentInterestsBinding.inflate(inflater, container, false)

        interestsViewModel = ViewModelProvider(this, modelFactory)[InterestsViewModel::class.java]

        setupUI()

        val bundle = arguments
        bundle?.let {
            user = bundle.getSerializable("user") as User
        }

        observe()

        return binding.root
    }

    private fun setupUI() {
        categoriesAdapter = CategoriesAdapter(arrayListOf(), interestsViewModel.getInterests(), binding.container, HashMap())

        binding.nextButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                interestsViewModel.register(user)
            }
        }

        binding.updateButton.setOnClickListener {  updateCategories() }

        with (binding.categoriesRecyclerView) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = categoriesAdapter
        }
    }

    private fun updateCategories() {
        interestsViewModel.getCategoriesFromServer().observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.apply {
                            categoriesRecyclerView.visibility = View.VISIBLE
                            progressBar.visibility = View.GONE
                        }
                        resource.data?.let { categories -> retrieveCategoriesList(categories) }
                    }
                    Status.ERROR -> {
                        binding.apply {
                            categoriesRecyclerView.visibility = View.VISIBLE
                            progressBar.visibility = View.GONE
                            Snackbar.make(requireView(), it.message.toString(), Snackbar.LENGTH_SHORT).show()
                        }
                    }
                    Status.LOADING -> {
                        binding.apply {
                            categoriesRecyclerView.visibility = View.GONE
                            progressBar.visibility = View.VISIBLE
                        }
                    }
                }
            }
        })
    }

    private fun observe() {
        interestsViewModel.getCategories().observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.apply {
                            categoriesRecyclerView.visibility = View.VISIBLE
                            progressBar.visibility = View.GONE
                        }
                        resource.data?.let { categories ->  retrieveCategoriesList(categories)}
                    }
                    Status.ERROR -> {
                        binding.apply {
                            categoriesRecyclerView.visibility = View.VISIBLE
                            progressBar.visibility = View.GONE
                            Snackbar.make(requireView(), it.message.toString(), Snackbar.LENGTH_SHORT).show()
                        }
                    }
                    Status.LOADING -> {
                        binding.apply {
                            categoriesRecyclerView.visibility = View.GONE
                            progressBar.visibility = View.VISIBLE
                        }
                    }
                }
            }
        })

        interestsViewModel.getTokenResponse().observe(viewLifecycleOwner, Observer {
            it?.let {
                val manager = requireActivity().supportFragmentManager
                val tag = "photo"
                if (manager.findFragmentByTag(tag) == null) {
                    val imagePickerFragment = ImagePickerFragment.newInstance()
                    manager.beginTransaction()
                        .addToBackStack(tag)
                        .replace(R.id.fragmentContent, imagePickerFragment)
                        .commit()
                }
            }
        })

        interestsViewModel.getErrorMsg().observe(viewLifecycleOwner, Observer {
            it?.let {
                Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).show()
            }
        })
    }

    private fun retrieveCategoriesList(categories: List<InterestCategory>) {
        with (categoriesAdapter) {
            addCategories(categories)
        }

    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    companion object {
        fun newInstance() = InterestsFragment()
    }
}
