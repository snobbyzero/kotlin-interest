package com.example.kotlin_interest.view.fragment.filters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_interest.R
import com.example.kotlin_interest.databinding.FilterInterestBinding
import com.example.kotlin_interest.databinding.UserInterestItemBinding
import com.example.kotlin_interest.model.Interest
import com.example.kotlin_interest.view.interests_adapter.UserInterestsAdapter

class InterestAdapter(
    private val dataset: ArrayList<Interest>,
    private val checkedInterests: ArrayList<Interest>
) : RecyclerView.Adapter<InterestAdapter.InterestViewHolder>() {

    inner class InterestViewHolder(
        val binding: FilterInterestBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(interest: Interest) {
            binding.interest = interest
            binding.interestCheckBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    checkedInterests.add(interest)
                    Log.d("FILTERS", "checked interests: " + checkedInterests.map { i -> i.name + " " })
                } else {
                    checkedInterests.remove(interest)
                }
            }
        }
    }

    override fun getItemCount() = dataset.size

    override fun onBindViewHolder(holder: InterestViewHolder, position: Int) {
        val interest = dataset[position]
        holder.bind(interest)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InterestViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FilterInterestBinding.inflate(inflater, parent, false)
        return InterestViewHolder(binding)
    }
}