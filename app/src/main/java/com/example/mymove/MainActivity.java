package com.example.mymove;


import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymove.adapter.MoveAdapter;
import com.example.mymove.data.MainVievModel;
import com.example.mymove.data.Move;
import com.example.mymove.utils.JsonUtil;
import com.example.mymove.utils.Network;

import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<JSONObject> {

    private RecyclerView recyclerViewPoster;
    private MoveAdapter moveAdapter;
    private Switch aSwitchSort;
    private TextView textViewPop;
    private TextView textViewTop;
    private ProgressBar progressBar;

    private static final int Loader_Id = 133;
    private LoaderManager loaderManager; // менеджер загрузок

    private static int page = 1;
    private static int mathonSort;
    private static boolean isLoading = false;

    private static String lange;

    private MainVievModel vievModel; // обьек вье модел

    @Override // переопределяем меню
    public boolean onCreateOptionsMenu(Menu menu) {
        // нужно получить меню инфлейтер
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // реагируем на нажания в меню
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.itemMain:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.itemFavorit:
                Intent intent1 = new Intent(this, LoveFilms.class);
                startActivity(intent1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private int getColomcount (){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics); // получаем характеристики экрана
        int wich = (int)(displayMetrics.widthPixels/displayMetrics.density);
        return  wich/185 >2 ? wich/185 :2;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewPop = findViewById(R.id.textViewPop);
        textViewTop = findViewById(R.id.textViewTOP);
        lange = Locale.getDefault().getLanguage();
        progressBar = findViewById(R.id.progressBarLoad);
        vievModel = new ViewModelProvider(this).get(MainVievModel.class); // присвоили
        recyclerViewPoster = findViewById(R.id.RecyclerVierPoster);// cоздаем ссылку
        recyclerViewPoster.setLayoutManager(new GridLayoutManager(this, getColomcount())); // расположение сеткой
        moveAdapter = new MoveAdapter(); // присваиваем значение
        aSwitchSort = findViewById(R.id.switchSort);
        /*JSONObject jsonObject = Network.getJsonFromNet(Network.Popularity,2); // получаем список фильмов
        ArrayList<Move> moves = JsonUtil.movesFromJson(jsonObject);
        moveAdapter.setMoves(moves);*/
        recyclerViewPoster.setAdapter(moveAdapter);

        loaderManager = LoaderManager.getInstance(this); // используется паттерн синлг

        aSwitchSort.setChecked(true);
        aSwitchSort.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // добавили слушательно на свич
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                page = 1;
                setaSwitchSorta(isChecked);
            }
        });
        aSwitchSort.setChecked(false);
        moveAdapter.setOnPosterCliclLister(new MoveAdapter.onPosterCliclLister() {
            @Override
            public void onPosterClic(int position) {
                //  Toast.makeText(MainActivity.this,"Выбрано "+position,Toast.LENGTH_SHORT).show();
                Move move = moveAdapter.getMoves().get(position);
                Intent intent = new Intent(MainActivity.this, DitalActiviti.class);
                intent.putExtra("id", move.getId()); //отправляем фильм с описанием
                startActivity(intent);
            }
        });
        moveAdapter.setOnReacheEndLister(new MoveAdapter.onReacheEndLister() {
            @Override
            public void onReadchEnd() {
                if (!isLoading) {
                    // Toast.makeText(MainActivity.this, "Конец списка ", Toast.LENGTH_SHORT).show();
                    dowmloadData(mathonSort, page);
                }
            }
        });


        LiveData<List<Move>> moveFromData = vievModel.getMoves(); //
        moveFromData.observe(this, new Observer<List<Move>>() { // создали обсервер
            @Override // когда данные будут изменяться мы будем их устанавливать у адаптера
            public void onChanged(List<Move> moves) {
                if (page == 1){
                    moveAdapter.setMoves(moves);
                }
                //  moveAdapter.setMoves(moves);
            }
        });
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

    public void onClickPop(View view) {
        setaSwitchSorta(false);
        aSwitchSort.setChecked(false);

    }

    public void onClickTOP(View view) {
        setaSwitchSorta(true);
        aSwitchSort.setChecked(true);
    }

    private void setaSwitchSorta(boolean isTopRat) {
        // int sortSwitch;
        if (isTopRat) {
            textViewTop.setTextColor(getResources().getColor(android.R.color.holo_red_light));
            textViewPop.setTextColor(getResources().getColor(android.R.color.white));
            mathonSort = Network.Popularity;
        } else {
            textViewPop.setTextColor(getResources().getColor(android.R.color.holo_red_light));
            textViewTop.setTextColor(getResources().getColor(android.R.color.white));
            mathonSort = Network.Top;
        }
        /*JSONObject jsonObject = Network.getJsonFromNet(sortSwitch, 1); // а так же загрузка данных получаем список фильмов
        ArrayList<Move> moves = JsonUtil.movesFromJson(jsonObject);
        moveAdapter.setMoves(moves);*/
        dowmloadData(mathonSort, page); // сам метод
    }

    //загрузка данных
    private void dowmloadData(int sortSwitch, int page) { // будем сортироват
        /*JSONObject jsonObject = Network.getJsonFromNet(sortSwitch, 1); // получаем список фильмов
        ArrayList<Move> moves = JsonUtil.movesFromJson(jsonObject);

        if (moves != null && !moves.isEmpty()) { // проверка
            vievModel.deletAllMove(); // очищаем предыдуще данные
            for (Move move : moves) {
                vievModel.InsertMove(move); // вставляем новые данные
            }
        }*/
        URL url = Network.bildURL(sortSwitch, page,lange);
        Bundle bundle = new Bundle();
        bundle.putString("url", url.toString());
        loaderManager.restartLoader(Loader_Id, bundle, this);
    }

    @Override
    public Loader<JSONObject> onCreateLoader(int id, Bundle args) {
        Network.JsonPodgruz jsonPodgruz = new Network.JsonPodgruz(this, args);
        jsonPodgruz.setOnStartLoadLis(new Network.JsonPodgruz.onStartLoad() {
            @Override
            public void onStart() {
                progressBar.setVisibility(View.VISIBLE);
                isLoading = true;
            }
        });
        return jsonPodgruz;  // вернули наш загрузчик
    }

    @Override
    public void onLoadFinished(Loader<JSONObject> loader, JSONObject data) {
        ArrayList<Move> moves = JsonUtil.movesFromJson(data);
        if (moves != null && !moves.isEmpty()) { // проверка
            if (page == 1) {
                vievModel.deletAllMove(); // очищаем предыдуще данные
                moveAdapter.clear();
            }
            for (Move move : moves) {
                vievModel.InsertMove(move); // вставляем новые данные
            }
            moveAdapter.addMoves(moves);
        }
        page ++ ;
        isLoading = false;
        progressBar.setVisibility(View.INVISIBLE);
        loaderManager.destroyLoader(Loader_Id);
    }

    @Override
    public void onLoaderReset(Loader<JSONObject> loader) {

    }
}

