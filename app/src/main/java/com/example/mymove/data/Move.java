package com.example.mymove.data;

public class Move {
    private int id ;  // для хранения в базе данных
    private int voteCount; // количество голосов
    private String title; // заголовок
    private String original; // оригинальное нахвание
    private String overview; // описание фильма
    private String poster; // путь к постеру c малым разрешением
    private String bigposter ; // с большим разрешением
    private String backdropP; // фоновое изображение
    private double voteAverage;// рейтинг
    private String realiseDate;// дата релиза


    public Move(int id, int voteCount, String title, String original, String overview, String poster, String bigposter, String backdropP, double voteAverage, String realiseDate) {
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

    public int getId() {
        return id;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public String getTitle() {
        return title;
    }

    public String getOriginal() {
        return original;
    }

    public String getOverview() {
        return overview;
    }

    public String getPoster() {
        return poster;
    }

    public String getBigposter() {
        return bigposter;
    }

    public String getBackdropP() {
        return backdropP;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public String getRealiseDate() {
        return realiseDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public void setBigposter(String bigposter) {
        this.bigposter = bigposter;
    }

    public void setBackdropP(String backdropP) {
        this.backdropP = backdropP;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public void setRealiseDate(String realiseDate) {
        this.realiseDate = realiseDate;
    }
}
