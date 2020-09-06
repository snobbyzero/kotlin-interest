package com.example.kotlin_interest.view.fragment.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_interest.R
import com.example.kotlin_interest.databinding.UserInterestItemBinding
import com.example.kotlin_interest.model.Interest
import com.example.kotlin_interest.model.User

class UserInterestsAdapter(
    private val dataset: ArrayList<Interest>,
    val onInterestClickListener: View.OnClickListener
) :
    RecyclerView.Adapter<UserInterestsAdapter.UserInterestsViewHolder>() {

    class UserInterestsViewHolder(
        val binding: UserInterestItemBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(interest: Interest) {
            binding.interest = interest
        }

    }

    override fun getItemCount() = dataset.size

    override fun onBindViewHolder(holder: UserInterestsViewHolder, position: Int) {
        val interest = dataset[position]
        holder.bind(interest)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserInterestsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = UserInterestItemBinding.inflate(inflater, parent, false)
        return UserInterestsViewHolder(binding)
    }

    fun addInterests(interests: List<Interest>) {
        with (dataset) {
            clear()
            addAll(interests)
            notifyDataSetChanged()
        }
    }
}