package com.example.mymove.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainVievModel extends AndroidViewModel {

    private static MoveDATaBase daTaBase; // обьект баз0ы данных
    private LiveData<List<Move>> moves;  // создаем список фильмов
    private LiveData<List<FavoritMove>> favoritMove; //создаем список избранных фильмов

    public MainVievModel(@NonNull Application application) {
        super(application);
        daTaBase = MoveDATaBase.getInstance(getApplication()); // присваиваем
        moves = daTaBase.moveDao().getAllMove();
        favoritMove = daTaBase.moveDao().getAllFavoritMove();
    }

    public LiveData<List<FavoritMove>> getFavoritMove() {
        return favoritMove;
    }

    public Move getMoveID(int id) { // возвращает обьект муви
        try {
            return new GetMove().execute(id).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deletAllMove() {
        new DeletAllMove().execute();
    } // метод для удаления всех параметров

    public void InsertMove(Move move) {
        new InsertMove().execute(move);
    } //  метод для вставки

    public void deletMove(Move move) {
        new DeletMove().execute(move);
    } // метод для удаления 1 элемента в таблице

    public LiveData<List<Move>> getMoves() {
        return moves;
    }

    private static class DeletMove extends AsyncTask<Move, Void, Void> {
        @Override
        protected Void doInBackground(Move... moves) {
            if (moves != null && moves.length > 0) {
                daTaBase.moveDao().deletMov(moves[0]);
            }
            return null;
        }
    }

    private static class InsertMove extends AsyncTask<Move, Void, Void> {

        @Override
        protected Void doInBackground(Move... moves) {
            if (moves != null && moves.length > 0) {
                daTaBase.moveDao().insertMove(moves[0]);
            }
            return null;
        }
    }


    private static class DeletAllMove extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            daTaBase.moveDao().deletAllMove();
            return null;
        }
    }


    private static class GetMove extends AsyncTask<Integer, Void, Move> { //создаем другой поток принимает параметр инт воид возвращает мову
        @Override
        protected Move doInBackground(Integer... integers) {
            if (integers != null && integers.length > 0) {
                return daTaBase.moveDao().getMoveByID(integers[0]);
            }
            return null;
        }
    }
 // для Избранных фильмов
    public void InsertFavoritMove(FavoritMove favoritMove) {
        new InsertFavoritMove().execute(favoritMove);
    } //  метод для вставки

    public void deletFavoritMov(FavoritMove favoritMove) {
        new DeletFavoritMove().execute(favoritMove);
    } // метод для удаления 1 элемента в таблице

    private static class DeletFavoritMove extends AsyncTask<FavoritMove, Void, Void> {
        @Override
        protected Void doInBackground(FavoritMove... moves) {
            if (moves != null && moves.length > 0) {
                daTaBase.moveDao().deletFavoritMove(moves[0]);
            }
            return null;
        }
    }

    private static class InsertFavoritMove extends AsyncTask<FavoritMove, Void, Void> {

        @Override
        protected Void doInBackground(FavoritMove... moves) {
            if (moves != null && moves.length > 0) {
                daTaBase.moveDao().insertFavoritMove(moves[0]);
            }
            return null;
        }
    }

    public FavoritMove getFavoritMoveID(int id) { // возвращает обьект муви
        try {
            return new GetFavoritMove().execute(id).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
    private static class GetFavoritMove extends AsyncTask<Integer, Void, FavoritMove> { //создаем другой поток принимает параметр инт воид возвращает мову
        @Override
        protected FavoritMove doInBackground(Integer... integers) {
            if (integers != null && integers.length > 0) {
                return daTaBase.moveDao().getFavoritMoveByID(integers[0]);
            }
            return null;
        }
    }
}