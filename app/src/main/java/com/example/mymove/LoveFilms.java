package com.example.mymove;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.mymove.adapter.MoveAdapter;
import com.example.mymove.data.FavoritMove;
import com.example.mymove.data.MainVievModel;
import com.example.mymove.data.Move;

import java.util.ArrayList;
import java.util.List;

public class LoveFilms extends AppCompatActivity {

    private RecyclerView recyclerViewLivefilms;
    private MoveAdapter adapter;
    private MainVievModel vievModel;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_love_films);
        recyclerViewLivefilms = findViewById(R.id.RecyclerVierLove_Films);
        recyclerViewLivefilms.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new MoveAdapter();
        recyclerViewLivefilms.setAdapter(adapter);
        vievModel = ViewModelProviders.of(this).get(MainVievModel.class);
        LiveData<List<FavoritMove>> favoritMoves = vievModel.getFavoritMove();
        favoritMoves.observe(this, new Observer<List<FavoritMove>>() {
            @Override
            public void onChanged(List<FavoritMove> favoritMoves) {
                List<Move> moves = new ArrayList<>();
                if (favoritMoves != null) {
                    moves.addAll(favoritMoves);
                    adapter.setMoves(moves);
                }
            }
        });

        adapter.setOnPosterCliclLister(new MoveAdapter.onPosterCliclLister() {
            @Override
            public void onPosterClic(int position) {
                //  Toast.makeText(MainActivity.this,"Выбрано "+position,Toast.LENGTH_SHORT).show();
                Move move = adapter.getMoves().get(position);
                Intent intent = new Intent(LoveFilms.this, DitalActiviti.class);
                intent.putExtra("id", move.getId()); //отправляем фильм с описанием
                startActivity(intent);
            }
        });
    }
}