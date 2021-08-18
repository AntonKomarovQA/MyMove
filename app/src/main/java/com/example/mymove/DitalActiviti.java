package com.example.mymove;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

    private int id;
    private MainVievModel vievModel;

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
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("id")) { // получаем id фильма
            id = intent.getIntExtra("id", -1); // присваиваем значения
        } else {
            finish(); // закрыли активность
        }
        vievModel = ViewModelProviders.of(this).get(MainVievModel.class); // получаем фильм из базы данных
        Move move = vievModel.getMoveID(id);
        Picasso.get().load(move.getPoster()).into(imageViewBigPoster);
        textViewNazvanie.setText(move.getTitle());
        TextViewNazvanieOrig.setText(move.getOriginal());
        TextViewReiting.setText(Double.toString(move.getVoteAverage()));
        TextViewDataReliz.setText(move.getRealiseDate());
        TextViewOpis.setText(move.getOverview());
    }

    public void onClicChangeFavor(View view) {
    }
}