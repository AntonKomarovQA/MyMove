package com.example.mymove.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymove.R;
import com.example.mymove.data.Review;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private List<Review> reviews;

    @NonNull
    @NotNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.revies_item,parent,false);
        return new ReviewViewHolder(view);// возвращаем новый обьект
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ReviewAdapter.ReviewViewHolder holder, int position) {
        Review review = reviews.get(position);
        holder.textViewContent.setText(review.getContent());
        holder.textViewAutor.setText(review.getAuthor());
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged(); //что б сообщить данные адаптеру что данные изменились
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder{

        private TextView textViewAutor;
        private TextView textViewContent;

        public ReviewViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            textViewAutor = itemView.findViewById(R.id.textViewAutor);
            textViewContent = itemView.findViewById(R.id.textViewContent);

        }
    }
}
