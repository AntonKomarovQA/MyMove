package com.example.mymove;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymove.data.FavoritMove;
import com.example.mymove.data.MainVievModel;
import com.example.mymove.data.Move;
import com.squareup.picasso.Picasso;

public class DitalActiviti extends AppCompatActivity {

    private ImageView imageViewBigPoster;
    private TextView textViewNazvanie;
    private TextView TextViewNazvanieOrig;
    private TextView TextViewReiting;
    private TextView TextViewDataReliz;
    private TextView TextViewOpis;

    private ImageView imageViewAddFavorit;

    private int id;
    private MainVievModel vievModel;
    private Move move;
 private FavoritMove favoritMove;
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
    }

    public void onClicChangeFavor(View view) {
        if (favoritMove== null){
            vievModel.InsertFavoritMove(new FavoritMove(move));
            Toast.makeText(this, R.string.Favorite_Plus,Toast.LENGTH_SHORT).show();
        } else {
            vievModel.deletFavoritMov(favoritMove);
            Toast.makeText(DitalActiviti.this, R.string.Delete_FrovMove,Toast.LENGTH_SHORT).show();
        }
        setFavorit();
    }

    private void setFavorit(){
        favoritMove = vievModel.getFavoritMoveID(id); // существует ли фильм
        if (favoritMove == null){
            imageViewAddFavorit.setImageResource(android.R.drawable.btn_star_big_off);
        } else {
            imageViewAddFavorit.setImageResource(android.R.drawable.btn_star_big_on);
        }
    }
}