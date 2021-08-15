package com.example.mymove.utils;

import android.net.Uri;
import android.os.AsyncTask;

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

    private static final String parms_Api_Key = "api_key"; // ключ
    private static final String parms_Language = "language"; // язык
    private static final String parms_Page = "page";       // листы
    private static final String parms_Sort_By = "sort_by"; // сортировка по

    private static final String Language_Value = "ru-RU";  // русский язык
    private static final String Api = "7a5a93da768cd04703de8b900e8b4b41"; // ключ
    private static final String Sort_By_Value_Pop = "popularity.desc"; // по популярности
    private static final String Sort_By_Value_Date = "release_date.desc"; // по дате
    private static final String Sort_By_Value_Top = "vote_average.desc"; // по оценке

    public static final int Popularity = 0;
    public static final int Top = 1;
    public static final int Relies_Date = 2;


    private static URL bildURL(int sortBy, int page) { // который возвщает запрос
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
                .appendQueryParameter(parms_Language, Language_Value)
                .appendQueryParameter(parms_Sort_By, metodofSort)
                .appendQueryParameter(parms_Page, Integer.toString(page)).build(); // передаем страницу
        try {
            result = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static JSONObject getJsonFromNet(int sortBy, int page) { //получается метод из сети
        JSONObject res = null;
        URL urL = bildURL(sortBy, page);
        try {
            res = new JsonLoadTask().execute(urL).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return res;
    }

    private static class JsonLoadTask extends AsyncTask<URL, Void, JSONObject> { //принимает юрл , возвращает Jcon
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
}