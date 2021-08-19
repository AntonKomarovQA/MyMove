package com.example.mymove.utils;

import com.example.mymove.data.Move;
import com.example.mymove.data.Review;
import com.example.mymove.data.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtil {
    //инфа о фильмах
    private static final String KEY_ID = "id";    // ключ - значение
    private static final String KEY_Count = "vote_count";
    private static final String KEY_Title = "title";
    private static final String KEY_Original = "original_title";
    private static final String KEY_OVERVIEW = "overview";
    private static final String KEY_Poster = "poster_path";
    private static final String KEY_BackDropP = "backdrop_path";
    private static final String KEY_VoteAverage = "vote_average";
    private static final String KEY_LISTDate = "release_date";


    private static final String KEY_Result = "results"; // получение Json по Резаульт
    // отзывы
    private static final String KEY_AUthor = "author"; // автор коментария
    private static final String KEY_COntent = "content"; // сам коментарий
    // видео
    private static final String KEY_NAME_Video = "name"; //имя трейлера
    private static final String BASE_YOUTUBE_Video = "https://www.youtube.com/watch?v="; //ютуб
    private static final String KEY_SITE_Video = "site"; // расположение
    private static final String KEY_oF_Video = "key"; // ключ


    private static final String Base_URL_Image = "https://image.tmdb.org/t/p/"; //ссылка на картинку
    private static final String Smol_Poster_Image = "w185"; // poster_size размер картинки маленькой
    private static final String Big_Poster_Image = "w780";  // размер большой картинки


    public static ArrayList<Move> movesFromJson(JSONObject jsonObject) { // получаем массив с фильмами
        ArrayList<Move> res = new ArrayList<>(); // создаем массив
        if (jsonObject == null) {
            return res; // возвращем наш эррей лист
        }
        try {
            JSONArray jsonArray = jsonObject.getJSONArray(KEY_Result); // получаем дсонАррай
            for (int i = 0; i < jsonArray.length(); i++) { // получаем фильмы
                JSONObject objectMove = jsonArray.getJSONObject(i); // закидываем их в эррай лист

                int id = objectMove.getInt(KEY_ID);
                int voteCount = objectMove.getInt(KEY_Count);
                String title = objectMove.getString(KEY_Title);
                String original = objectMove.getString(KEY_Original);
                String overview = objectMove.getString(KEY_OVERVIEW);
                String poster = Base_URL_Image + Smol_Poster_Image + objectMove.getString(KEY_Poster);
                String bigposter = Base_URL_Image + Big_Poster_Image + objectMove.getString(KEY_Poster);
                String backdropP = objectMove.getString(KEY_BackDropP);
                double voteAverage = objectMove.getDouble(KEY_VoteAverage);
                String realiseDate = objectMove.getString(KEY_LISTDate);

                Move move = new Move(id, voteCount, title, original, overview, poster, bigposter, backdropP, voteAverage, realiseDate);
                res.add(move); // добавляем в мови
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return res;
    }

    // для  озывов
    public static ArrayList<Review> reviewsFromJson(JSONObject jsonObject) { // получаем массив с отзывами
        ArrayList<Review> res = new ArrayList<>(); // создаем массив
        if (jsonObject == null) {
            return res; // возвращем наш эррей лист
        }
        try {
            JSONArray jsonArray = jsonObject.getJSONArray(KEY_Result); // получаем дсонАррай
            for (int i = 0; i < jsonArray.length(); i++) { // получаем фильмы
                JSONObject objectRevie = jsonArray.getJSONObject(i); // закидываем их в эррай лист
                String autor = objectRevie.getString(KEY_AUthor);
                String contetn = objectRevie.getString(KEY_COntent);
                Review review = new Review(autor,contetn);
                        res.add(review); // добавляем в мови
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return res;
    }
 // для трейлеров
    public static ArrayList<Trailer> trailersFromJson(JSONObject jsonObject) { // получаем массив с отзывами
        ArrayList<Trailer> res = new ArrayList<>(); // создаем массив
        if (jsonObject == null) {
            return res; // возвращем наш эррей лист
        }
        try {
            JSONArray jsonArray = jsonObject.getJSONArray(KEY_Result); // получаем дсонАррай
            for (int i = 0; i < jsonArray.length(); i++) { // получаем фильмы
                JSONObject objectTrailer = jsonArray.getJSONObject(i); // закидываем их в эррай лист
                String key = BASE_YOUTUBE_Video+ objectTrailer.getString(KEY_oF_Video); // добавили еще и ютуб
                String name = objectTrailer.getString(KEY_NAME_Video);

                Trailer trailer = new Trailer(key,name); //
                res.add(trailer); // добавляем в мови
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return res;
    }
}