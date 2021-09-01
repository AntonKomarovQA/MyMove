package com.example.mymove.utils;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

public class Network {
    private static final String Base_URL = "https://api.themoviedb.org/3/discover/movie"; // ссылка
    private static final String Base_URl_Video = "https://api.themoviedb.org/3/movie/%s/videos"; // ссылка на трейлер
    private static final String Base_URL_Reviews = "https://api.themoviedb.org/3/movie/%s/reviews"; // ссылка на отзывы





    private static final String parms_Api_Key = "api_key"; // ключ
    private static final String parms_Language = "language"; // язык
    private static final String parms_Page = "page";       // листы
    private static final String parms_Sort_By = "sort_by"; // сортировка по

   // private static final String Language_Value = "ru-RU";  // русский язык
    private static final String Api = "7a5a93da768cd04703de8b900e8b4b41"; // ключ
    private static final String Sort_By_Value_Pop = "popularity.desc"; // по популярности
    private static final String Sort_By_Value_Date = "release_date.desc"; // по дате
    private static final String Sort_By_Value_Top = "vote_average.desc"; // по оценке

    private static final String Params_Min_vote_COUT = "vote_count.gte"; // сортировка по количеству оценок
    private static final String Min_Vote_count = "500";

    public static final int Popularity = 0;
    public static final int Top = 1;
    public static final int Relies_Date = 2;


    // Создание трейлера
    public static URL bildURLVideo(int id,String lange) {
        Uri uri = Uri.parse(String.format(Base_URl_Video, id)).buildUpon()
                .appendQueryParameter(parms_Api_Key, Api)
                .appendQueryParameter(parms_Language, lange)
                .build();
        try {
            return new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
    //получается метод из сети
    public static JSONObject getJsonForVideo(int id,String lange) {
        JSONObject res = null;
        URL urL = bildURLVideo(id,lange);
        try {
            res = new JsonLoadTask().execute(urL).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return res;
    }
//

    // Создаем Юрл который возвращает Отзывы
    public static URL bildURLReviews(int id,String lange) {
        Uri uri = Uri.parse(String.format(Base_URL_Reviews, id)).buildUpon()
                .appendQueryParameter(parms_Api_Key, Api)
                .appendQueryParameter(parms_Language, lange)
                .build();
        try {
            return new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
    //получается метод из сети
    public static JSONObject getJsonForReview(int id,String lange) {
        JSONObject res = null;
        URL urL = bildURLReviews(id,lange);
        try {
            res = new JsonLoadTask().execute(urL).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return res;
    }

    // который возвщает запрос
    public static URL bildURL(int sortBy, int page,String lange) {
        URL result = null;
        String metodofSort;

        if (sortBy == Popularity) { // выбираем какая будет сортировка
            metodofSort = Sort_By_Value_Pop;
        } else if (sortBy == Top) {
            metodofSort = Sort_By_Value_Top;
        } else {
            metodofSort = Sort_By_Value_Date;
        }

        Uri uri = Uri.parse(Base_URL).buildUpon() //получили строку ввиде запроса
                .appendQueryParameter(parms_Api_Key, Api)
                .appendQueryParameter(parms_Language, lange )
                .appendQueryParameter(parms_Sort_By, metodofSort)
                .appendQueryParameter(Params_Min_vote_COUT,Min_Vote_count)// выводит фильмы с колвом оценок больше 500
                .appendQueryParameter(parms_Page, Integer.toString(page))
                .build(); // передаем страницу
        try {
            result = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return result;
    }

    //получается метод из сети
    public static JSONObject getJsonFromNet(int sortBy, int page,String lange) {
        JSONObject res = null;
        URL urL = bildURL(sortBy, page,lange);
        try {
            res = new JsonLoadTask().execute(urL).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return res;
    }
    //принимает юрл , возвращает Jcon
    private static class JsonLoadTask extends AsyncTask<URL, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(URL... urls) {
            JSONObject resul = null; // переменная которую возвращаем
            if (urls == null || urls.length == 0) { // если все норм то создаем соединение
                return resul;
            }
            HttpsURLConnection urlConnection = null;
            try {
                urlConnection = (HttpsURLConnection) urls[0].openConnection();
                InputStream inputStream = urlConnection.getInputStream(); // поток ввода

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader); // читать строками
                StringBuilder builder = new StringBuilder(); //читаем данные

                String line = bufferedReader.readLine();
                while (line != null) {
                    builder.append(line);
                    line = bufferedReader.readLine();
                }
                resul = new JSONObject(builder.toString()); // передаем строку
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return resul;
        }
    }
     // загрузчик
    public static class JsonPodgruz extends AsyncTaskLoader<JSONObject> {

        private Bundle bundle; // источник данных передаем через бандл
         private onStartLoad onStartLoadLis;

         public interface onStartLoad{
             void onStart();
         }

         public void setOnStartLoadLis(onStartLoad onStartLoadLis) {
             this.onStartLoadLis = onStartLoadLis;
         }

         public JsonPodgruz(@NonNull @NotNull Context context, Bundle bundle) {
            super(context);
            this.bundle = bundle;
        }


         @Override
         protected void onStartLoading() {// что б была загрузка
             super.onStartLoading();
             if (onStartLoadLis != null){
                 onStartLoadLis.onStart();
             }
             forceLoad();
         }

         @Nullable
        @org.jetbrains.annotations.Nullable
        @Override
        public JSONObject loadInBackground() {
            if (bundle == null) {
                return null;
            }
            String urlAsString = bundle.getString("url"); // получаем url   в виде строки
            URL url = null;
            try {
                url = new URL(urlAsString);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            JSONObject res = null;
            if (url == null){
                return null;
            }
            HttpsURLConnection connection = null;
            try {
                connection  = (HttpsURLConnection) url.openConnection();
                InputStream inputStream = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(inputStreamReader);
                StringBuilder builder = new StringBuilder();
                String line = reader.readLine();
                while (line != null){
                    builder.append(line);
                    line = reader.readLine();
                }
                res = new JSONObject(builder.toString());
            } catch (IOException | JSONException e){
                e.printStackTrace();
            } finally {
                if (connection != null){
                    connection.disconnect();
                }
            }
            return  res;
        }
    }
}