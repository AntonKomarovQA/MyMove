package com.example.mymove.data;


import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


// таблица в базе данных (таблица называется  = мовес)
@Entity(tableName = "moves")
public class Move {
    @PrimaryKey(autoGenerate = true) // id первичный ключ primarikey
    private int uniqueID; // первичный ключ сейчас
    private int id;  // для хранения в базе данных
    private int voteCount; // количество голосов
    private String title; // заголовок
    private String original; // оригинальное название
    private String overview; // описание фильма
    private String poster; // путь к постеру c малым разрешением
    private String bigposter; // с большим разрешением
    private String backdropP; // фоновое изображение
    private double voteAverage;// рейтинг
    private String realiseDate;// дата релиза

    public Move(int uniqueID, int id, int voteCount, String title, String original, String overview, String poster, String bigposter, String backdropP, double voteAverage, String realiseDate) {
        this.uniqueID = uniqueID;
        this.id = id;
        this.voteCount = voteCount;
        this.title = title;
        this.original = original;
        this.overview = overview;
        this.poster = poster;
        this.bigposter = bigposter;
        this.backdropP = backdropP;
        this.voteAverage = voteAverage;
        this.realiseDate = realiseDate;
    }
    @Ignore // конструтор в который мы не передаем за индификатор нужен для того что б мы сами передавали мову
    public Move( int id, int voteCount, String title, String original, String overview, String poster, String bigposter, String backdropP, double voteAverage, String realiseDate) {
        this.id = id;
        this.voteCount = voteCount;
        this.title = title;
        this.original = original;
        this.overview = overview;
        this.poster = poster;
        this.bigposter = bigposter;
        this.backdropP = backdropP;
        this.voteAverage = voteAverage;
        this.realiseDate = realiseDate;
    }

    public int getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(int uniqueID) {
        this.uniqueID = uniqueID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getBigposter() {
        return bigposter;
    }

    public void setBigposter(String bigposter) {
        this.bigposter = bigposter;
    }

    public String getBackdropP() {
        return backdropP;
    }

    public void setBackdropP(String backdropP) {
        this.backdropP = backdropP;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getRealiseDate() {
        return realiseDate;
    }

    public void setRealiseDate(String realiseDate) {
        this.realiseDate = realiseDate;
    }
}
