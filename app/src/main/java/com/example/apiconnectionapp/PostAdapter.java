package com.example.apiconnectionapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    // Lista u kojoj cuvamo Postove
    private List<Post> postList;

    // Constructor - Ovako cemo assignat postove koje cemo dobiti iz responsa u MainActivitiju u postList u ovom Adapteru
    public PostAdapter(List<Post> postList) {
        this.postList = postList;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);
        return new PostViewHolder(itemView);
    }

    // Ovdje setamo tekst unutar TextViewa u item_layout-u
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post currentPost = postList.get(position);

        holder.titleTextView.setText(currentPost.getTitle());
        holder.bodyTextView.setText(currentPost.getBody());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();

                Intent intent = new Intent(context, DetailActivity.class);

                intent.putExtra("EXTRA_TITLE", currentPost.getTitle());
                intent.putExtra("EXTRA_BODY", currentPost.getBody());

                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {

        // Declare the TextViews from our item_layout.xml
        public TextView titleTextView;
        public TextView bodyTextView;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            // Link the variables to the IDs in the XML
            titleTextView = itemView.findViewById(R.id.textViewTitle);
            bodyTextView = itemView.findViewById(R.id.textViewBody);


        }
    }
}
