package com.example.mymove.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


//import com.bumptech.glide.Glide;
import com.example.mymove.R;
import com.example.mymove.data.Move;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.List;

public class MoveAdapter extends RecyclerView.Adapter<MoveAdapter.MoveHolder> {

    private List<Move> moves;
    private onPosterCliclLister onPosterCliclLister;
    private onReacheEndLister onReacheEndLister;

    public MoveAdapter() {

        moves = new ArrayList<>();
    }

   public interface onPosterCliclLister {
        void onPosterClic(int position); // метод принимает инт
    }

    public void setOnPosterCliclLister(MoveAdapter.onPosterCliclLister onPosterCliclLister) {
        this.onPosterCliclLister = onPosterCliclLister;
    }

    public interface onReacheEndLister{
        void onReadchEnd();
    }

    @NonNull
    @Override
    public MoveHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.move2_ite, parent, false);
        return new MoveHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoveAdapter.MoveHolder holder, int position) {
       if (position == moves.size()-4 && onReacheEndLister != null){
            onReacheEndLister.onReadchEnd();
       }
        Move move = moves.get(position);
       // ImageView imageView = holder.imageVievSmol;
        Picasso.get().load(move.getPoster()).into(holder.imageVievSmol);
        //Glide.with(imageView).load(Network.getJsonFromNet(Network.Popularity,2)).into(holder.imageVievSmol);


    }

    @Override
    public int getItemCount() {
        return moves.size();
    }

    public class MoveHolder extends RecyclerView.ViewHolder {
        private ImageView imageVievSmol;

        public MoveHolder(@NonNull View itemView) {
            super(itemView);
            imageVievSmol = itemView.findViewById(R.id.smolPoster2);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   if (onPosterCliclLister != null){
                       onPosterCliclLister.onPosterClic(getAdapterPosition());
                   }
                }
            });
        }
    }

    public void setOnReacheEndLister(MoveAdapter.onReacheEndLister onReacheEndLister) {
        this.onReacheEndLister = onReacheEndLister;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void addMoves(List<Move> moves) {
        this.moves = moves;
        notifyDataSetChanged();
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
        notifyDataSetChanged();
    }
}