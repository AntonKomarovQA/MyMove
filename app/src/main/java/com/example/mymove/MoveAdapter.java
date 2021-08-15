package com.example.mymove;

import android.renderscript.RenderScript;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.mymove.data.Move;
import com.example.mymove.utils.Network;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

public class MoveAdapter extends RecyclerView.Adapter<MoveAdapter.MoveHolder>  {

    private ArrayList<Move> moves;

    public MoveAdapter(){
        moves = new ArrayList<>();
    }

    @NonNull
    @Override
    public MoveHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.move2_ite,parent,false);
        return new MoveHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  MoveAdapter.MoveHolder holder, int position) {
        Move move = moves.get(position);
        ImageView imageView = holder.imageVievSmol;
        Picasso.get().load(move.getPoster()).into(holder.imageVievSmol);
       //Glide.with(imageView).load(Network.getJsonFromNet(Network.Popularity,2)).into(holder.imageVievSmol);


    }

    @Override
    public int getItemCount() {
        return moves.size();
    }

    public class MoveHolder extends  RecyclerView.ViewHolder{
            private ImageView imageVievSmol;
        public MoveHolder(@NonNull  View itemView) {
            super(itemView);
            imageVievSmol = itemView.findViewById(R.id.smolPoster2);
        }
    }

    public ArrayList<Move> getMoves() {
        return moves;
    }

    public void addMoves(ArrayList<Move> moves){
        this.moves = moves;
        notifyDataSetChanged();
    }
    public void setMoves(ArrayList<Move> moves) {
        this.moves = moves;
        notifyDataSetChanged();
    }
}