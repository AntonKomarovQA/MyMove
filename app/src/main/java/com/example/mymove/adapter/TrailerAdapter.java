package com.example.mymove.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymove.R;
import com.example.mymove.data.Trailer;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {
    private List<Trailer> trailers;
    private OnTrailerClicListner onTrailerClicListner;

    public interface OnTrailerClicListner {
        void onTrailer(String url);
    }

    public void setTrailers(List<Trailer> trailers) {
        this.trailers = trailers;
        notifyDataSetChanged();
    }

    public void setOnTrailerClicListner(OnTrailerClicListner onTrailerClicListner) {
        this.onTrailerClicListner = onTrailerClicListner;
    }

    @NonNull
    @NotNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_item, parent, false);
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull TrailerAdapter.TrailerViewHolder holder, int position) {
        Trailer trailer = trailers.get(position);
        holder.textViewVideo.setText(trailer.getName());

    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }


    class TrailerViewHolder extends RecyclerView.ViewHolder { // вложенный класс

        private TextView textViewVideo;

        public TrailerViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            textViewVideo = itemView.findViewById(R.id.textViewoFVideo);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onTrailerClicListner != null){
                        onTrailerClicListner.onTrailer(trailers.get(getAdapterPosition()).getKey());
                    }
                }
            });
        }
    }
}
