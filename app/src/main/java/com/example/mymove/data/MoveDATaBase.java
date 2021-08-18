package com.example.mymove.data;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Move.class},version = 1,exportSchema = false)
// анатация датабейс ентетис  версия .
    public abstract class MoveDATaBase extends RoomDatabase {
    private static MoveDATaBase daTaBase;
    private static final String DB_Name = "moves.db"; // имя таблицы
    private static final Object LOCK = new Object(); // блок синхроназий

    public static MoveDATaBase getInstance(Context context) {
        synchronized (LOCK) { // блок синхронизаций
            if (daTaBase == null) { //
                daTaBase = Room.databaseBuilder(context, MoveDATaBase.class, DB_Name).build();
            }
            return daTaBase;
        }
    }

    public abstract MoveDao moveDao(); // возврщаем метод
}
