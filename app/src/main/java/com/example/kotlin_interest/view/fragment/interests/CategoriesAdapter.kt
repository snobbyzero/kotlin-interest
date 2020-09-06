package com.example.kotlin_interest.view.fragment.interests

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_interest.R
import com.example.kotlin_interest.model.Interest
import com.example.kotlin_interest.model.InterestCategory
import com.google.android.flexbox.*
import kotlinx.android.synthetic.main.category_item.view.*
import kotlinx.coroutines.selects.select

class CategoriesAdapter(
    private val dataset: ArrayList<InterestCategory>,
    private val checkedInterests: ArrayList<Interest>,
    private val container: FrameLayout,
    private val checkBoxesMap: HashMap<Interest, CheckBox>
) :
    RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {

    private var selectedItem: View? = null
        get() = field
        set(value) {
            field = value
        }

    inner class CategoriesViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        checkedInterests: ArrayList<Interest>
    ) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.category_item, parent, false)) {
        private var categoryNameTextView: TextView? = null
        private var interestsAdapter: InterestsAdapter? = null
        private var interestsRecyclerView: RecyclerView? = null


        init {
            categoryNameTextView = itemView.findViewById(R.id.categoryNameTextView)
            interestsRecyclerView =
                inflater.inflate(R.layout.interests_list, null, false) as RecyclerView
            interestsAdapter =
                InterestsAdapter(arrayListOf(), checkedInterests, this, this@CategoriesAdapter, checkBoxesMap)
        }

        fun bind(interestCategory: InterestCategory, container: FrameLayout) {
            val flexboxLayoutManager = FlexboxLayoutManager(itemView.context)
            with(flexboxLayoutManager) {
                flexWrap = FlexWrap.WRAP
                flexDirection = FlexDirection.ROW
                justifyContent = JustifyContent.FLEX_START
                alignItems = AlignItems.FLEX_START
            }
            categoryNameTextView?.text = interestCategory.name
            interestsRecyclerView?.apply {
                layoutManager = flexboxLayoutManager
                adapter = interestsAdapter
                interestsAdapter?.addInterests(interestCategory.interestList)
            }

            itemView.setOnClickListener {
                if (selectedItem != itemView) {
                    selectedItem?.background = null
                    selectedItem?.categoryNameTextView?.setTextColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.colorWhite
                        )
                    )
                    selectedItem = itemView
                }
                itemView.background = ContextCompat.getDrawable(
                    itemView.context, R.drawable.adapter_category_selected_item_background
                )
                categoryNameTextView?.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.colorEndOrange
                    )
                )
                container.removeAllViewsInLayout()
                container.addView(interestsRecyclerView)
            }

        }

    }

    override fun getItemCount() = dataset.size

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        val interestCategory = dataset[position]
        holder.bind(interestCategory, container)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CategoriesViewHolder(inflater, parent, checkedInterests)
    }


    fun addCategories(categories: List<InterestCategory>) {
        dataset.apply {
            clear()
            add(InterestCategory(-1, "Selected", checkedInterests))
            addAll(categories)
            notifyDataSetChanged()
        }
    }


}