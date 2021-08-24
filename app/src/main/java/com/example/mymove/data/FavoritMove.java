package com.example.mymove.data;

import androidx.room.Entity;
import androidx.room.Ignore;

@Entity (tableName = "Favorit_Move")
public class FavoritMove extends Move {
    public FavoritMove(int uniqueID, int id, int voteCount, String title, String original, String overview, String poster, String bigposter, String backdropP, double voteAverage, String realiseDate) {
        super(uniqueID, id, voteCount, title, original, overview, poster, bigposter, backdropP, voteAverage, realiseDate);
    }
    
   @Ignore
    public FavoritMove(Move move) {
        super(move.getUniqueID(), move.getId(), move.getVoteCount(), move.getTitle(), move.getOriginal(), move.getOverview(), move.getPoster(), move.getBigposter(), move.getBackdropP(), move.getVoteAverage(), move.getRealiseDate());
    }
}

