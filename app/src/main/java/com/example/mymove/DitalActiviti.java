package com.example.mymove;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymove.adapter.ReviewAdapter;
import com.example.mymove.adapter.TrailerAdapter;
import com.example.mymove.data.FavoritMove;
import com.example.mymove.data.MainVievModel;
import com.example.mymove.data.Move;
import com.example.mymove.data.Review;
import com.example.mymove.data.Trailer;
import com.example.mymove.utils.JsonUtil;
import com.example.mymove.utils.Network;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DitalActiviti extends AppCompatActivity {

    private ImageView imageViewBigPoster;
    private TextView textViewNazvanie;
    private TextView TextViewNazvanieOrig;
    private TextView TextViewReiting;
    private TextView TextViewDataReliz;
    private TextView TextViewOpis;

    private ReviewAdapter reviewAdapter;
    private TrailerAdapter trailerAdapter;
    private RecyclerView recyclerViewReview;
    private RecyclerView recyclerViewTrailer;


    private ImageView imageViewAddFavorit;

    private int id;
    private MainVievModel vievModel;
    private Move move;
    private FavoritMove favoritMove;

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
        setContentView(R.layout.activity_dital_activiti);
        imageViewBigPoster = findViewById(R.id.BigPoster);
        textViewNazvanie = findViewById(R.id.textVIewPosterTitle2);
        TextViewNazvanieOrig = findViewById(R.id.textViewOrigTitle2);
        TextViewReiting = findViewById(R.id.textViewTopReiting2);
        TextViewDataReliz = findViewById(R.id.textViewDateRelis2);
        TextViewOpis = findViewById(R.id.textViewOpisanie2);
        imageViewAddFavorit = findViewById(R.id.imageViewAddFavorit);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("id")) { // получаем id фильма
            id = intent.getIntExtra("id", -1); // присваиваем значения
        } else {
            finish(); // закрыли активность
        }
        vievModel = ViewModelProviders.of(this).get(MainVievModel.class); // получаем фильм из базы данных
        move = vievModel.getMoveID(id);
        Picasso.get().load(move.getPoster()).into(imageViewBigPoster);
        textViewNazvanie.setText(move.getTitle());
        TextViewNazvanieOrig.setText(move.getOriginal());
        TextViewReiting.setText(Double.toString(move.getVoteAverage()));
        TextViewDataReliz.setText(move.getRealiseDate());
        TextViewOpis.setText(move.getOverview());
        setFavorit();

        recyclerViewReview = findViewById(R.id.RecyclerVierRevies); // присвоил отзывы
        recyclerViewTrailer = findViewById(R.id.RecyclerVierTrailers); // присвоил трейлер
        reviewAdapter = new ReviewAdapter();
        trailerAdapter = new TrailerAdapter();
        trailerAdapter.setOnTrailerClicListner(new TrailerAdapter.OnTrailerClicListner() {
            @Override
            public void onTrailer(String url) {
              //  Toast.makeText(DitalActiviti.this,url,Toast.LENGTH_SHORT).show();//выводили адрес обзора на трейлер
                Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent1);
            }
        });
        recyclerViewReview.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewTrailer.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewReview.setAdapter(reviewAdapter);
        recyclerViewTrailer.setAdapter(trailerAdapter);
        JSONObject jsonObjectTrailer = Network.getJsonForVideo(move.getId());
        JSONObject jsonObjectReview = Network.getJsonForReview(move.getId());
        ArrayList<Trailer> trailers = JsonUtil.trailersFromJson(jsonObjectTrailer);
        ArrayList<Review> reviews = JsonUtil.reviewsFromJson(jsonObjectReview);
        reviewAdapter.setReviews(reviews);
        trailerAdapter.setTrailers(trailers);
    }

    public void onClicChangeFavor(View view) {
        if (favoritMove == null) {
            vievModel.InsertFavoritMove(new FavoritMove(move));
            Toast.makeText(this, R.string.Favorite_Plus, Toast.LENGTH_SHORT).show();
        } else {
            vievModel.deletFavoritMov(favoritMove);
            Toast.makeText(DitalActiviti.this, R.string.Delete_FrovMove, Toast.LENGTH_SHORT).show();
        }
        setFavorit();
    }

    private void setFavorit() {
        favoritMove = vievModel.getFavoritMoveID(id); // существует ли фильм
        if (favoritMove == null) {
            imageViewAddFavorit.setImageResource(android.R.drawable.btn_star_big_off);
        } else {
            imageViewAddFavorit.setImageResource(android.R.drawable.btn_star_big_on);
        }
    }
}