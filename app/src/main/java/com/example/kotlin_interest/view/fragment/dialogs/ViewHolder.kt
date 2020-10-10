package com.example.interest.ui.messenger

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.interest.R


class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var message: TextView

    init {
        message = itemView.findViewById(R.id.message_item)
    }
}
