package com.example.interest.ui.messenger

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.interest.R
import java.util.*

class DataAdapter(context: Context?, var messages: ArrayList<String>) : Adapter<ViewHolder> {
    var inflater: LayoutInflater

    @NonNull
    fun onCreateViewHolder(@NonNull parent: ViewGroup?, viewType: Int): ViewHolder {
        val view: View = inflater.inflate(R.layout.item_message, parent, false)
        return ViewHolder(view)
    }

    fun onBindViewHolder(@NonNull holder: ViewHolder, position: Int) {
        val msg = messages[position]
        holder.message.setText(msg)
    }

    val itemCount: Int
        get() = messages.size

    init {
        inflater = LayoutInflater.from(context)
    }
}