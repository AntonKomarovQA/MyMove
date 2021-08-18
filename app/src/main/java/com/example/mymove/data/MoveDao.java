package com.example.mymove.data;



import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao// анатация
public interface MoveDao {
    @Query("SELECT*FROM moves")
        // запрос к базе
     LiveData<List<Move>> getAllMove();
    //возвращет лив дата

    @Query("SELECT * FROM moves WHERE id == :moveID")
        // возвращает конкрентный фильм
    Move getMoveByID(int moveID);

    @Query("DELETE FROM moves")
    void deletAllMove(); // удаляет все данные

    @Insert
    void insertMove(Move move); // вставляем данные

    @Delete
    void deletMov(Move move); // удалаем 1 элемент

    //для 2 таблицы
    @Query("SELECT*FROM moves")// запрос к базе
    LiveData<List<FavoritMove>> getAllFavoritMove();//возвращет лив дата

    @Query("SELECT * FROM Favorit_Move WHERE id == :moveID")
        // возвращает конкрентный фильм
    FavoritMove getFavoritMoveByID(int moveID);

/*
    @Query("DELETE FROM moves")
    void deletAllMove(); // удаляет все данные
*/

    @Insert
    void insertFavoritMove(FavoritMove FavoritMove); // вставляем данные

    @Delete
    void deletFavoritMove(FavoritMove favoritMove); // удалаем 1 элемент



}
