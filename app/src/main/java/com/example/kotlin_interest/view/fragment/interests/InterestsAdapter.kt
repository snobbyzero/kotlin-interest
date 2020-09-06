package com.example.kotlin_interest.view.fragment.interests

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_interest.R
import com.example.kotlin_interest.model.Interest
import java.lang.Exception

class InterestsAdapter(
    private val dataset: ArrayList<Interest>,
    private val checkedInterests: ArrayList<Interest>,
    private val categoriesHolder: CategoriesAdapter.CategoriesViewHolder,
    private val categoriesAdapter: CategoriesAdapter,
    private val checkBoxesMap: HashMap<Interest, CheckBox>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class SelectedInterestsViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ) : RecyclerView.ViewHolder(inflater.inflate(R.layout.selected_interest_item, parent, false)) {
        private var interestNameTextView: TextView? = null
        private var deleteButton: ImageButton? = null

        init {
            interestNameTextView = itemView.findViewById(R.id.interestNameTextView)
            deleteButton = itemView.findViewById(R.id.deleteButton)
        }

        fun bind(interest: Interest) {
            interestNameTextView?.text = interest.name
            deleteButton?.setOnClickListener {
                checkBoxesMap[interest]!!.isChecked = false
                checkedInterests.remove(interest)
                dataset.remove(interest)
                notifyItemRemoved(adapterPosition)
                notifyItemRangeChanged(adapterPosition, checkedInterests.size)
            }
        }
    }

    inner class InterestsViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.interest_item, parent, false)) {
        private var interestNameTextView: TextView? = null
        private var chosenCheckBox: CheckBox? = null

        init {
            interestNameTextView = itemView.findViewById(R.id.interestNameTextView)
            chosenCheckBox = itemView.findViewById(R.id.chosenCheckBox)
        }


        fun bind(interest: Interest) {
            interestNameTextView?.text = interest.name
            chosenCheckBox?.isChecked = false
            checkBoxesMap[interest] = chosenCheckBox!!

            chosenCheckBox?.setOnCheckedChangeListener { p0, isChecked ->
                if (isChecked) {
                    itemView.background = ContextCompat.getDrawable(
                        itemView.context, R.drawable.adapter_interest_checked_item
                    )
                    checkedInterests.add(interest)
                    categoriesAdapter.notifyItemChanged(0)

                    interestNameTextView?.setTextColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.colorWhite
                        )
                    )
                } else {
                    itemView.background = null
                    checkedInterests.remove(interest)
                }
            }
        }

    }

    override fun getItemCount() = dataset.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val interest = dataset[position]
        when (holder) {
            is SelectedInterestsViewHolder -> holder.bind(interest)
            is InterestsViewHolder -> holder.bind(interest)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            0 -> SelectedInterestsViewHolder(inflater, parent)
            1 -> InterestsViewHolder(inflater, parent)
            else -> throw Exception()
        }
    }

    fun addInterests(interests: List<Interest>) {
        dataset.apply {
            clear()
            addAll(interests)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (categoriesHolder.adapterPosition == 0) 0 else 1
    }

}