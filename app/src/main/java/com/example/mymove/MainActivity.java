package com.example.mymove;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.mymove.data.Move;
import com.example.mymove.utils.JsonUtil;
import com.example.mymove.utils.Network;

import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

        private RecyclerView recyclerViewPoster;
        private MoveAdapter moveAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewPoster = findViewById(R.id.RecyclerVierPoster);// cоздаем ссылку
        recyclerViewPoster.setLayoutManager(new GridLayoutManager(this,4)); // расположение сеткой
        moveAdapter = new MoveAdapter(); // присваиваем значение
        JSONObject jsonObject = Network.getJsonFromNet(Network.Popularity,2); // получаем список фильмов
        ArrayList<Move> moves = JsonUtil.movesFromJson(jsonObject);
        moveAdapter.setMoves(moves);
        recyclerViewPoster.setAdapter(moveAdapter);

       //1 проверка
        /* String url = Network.bildURL(Network.Popularity,3).toString();
            Log.i ("XX",url); */  //17min работает норм, строке пристаиваем нетворк поп и выводим в логи  //
     // 2 проверка
       /* JSONObject jsonObject = Network.getJsonFromNet(Network.Top, 2);

        if (jsonObject == null) {
            Toast.makeText(this, "Ошибка", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this, "Успешно", Toast.LENGTH_SHORT).show();*/
        // 3 проверка*/
       /* JSONObject jsonObject = Network.getJsonFromNet(Network.Relies_Date,3);
        ArrayList<Move> moves = JsonUtil.movesFromJson(jsonObject);
        StringBuilder builder = new StringBuilder();
        for (Move move : moves){
            builder.append(move.getTitle()).append("\n");
        }
        Log.i("XX",builder.toString());*/
    }
}

