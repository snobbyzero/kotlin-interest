package com.example.interest.ui.messenger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.interest.R;

import java.io.File;
import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<ViewHolder> {
   ArrayList<String> messages;
   LayoutInflater inflater;
   ArrayList<File> files;

    public DataAdapter(Context context, ArrayList<String> messages, ArrayList<File> files) {
        this.messages = messages;
        this.inflater = LayoutInflater.from(context);
        this.files = files;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_message, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String msg = messages.get(position);
        holder.message.setText(msg);
        File fil = files.get(position);
        holder.file.set(fil);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
}
