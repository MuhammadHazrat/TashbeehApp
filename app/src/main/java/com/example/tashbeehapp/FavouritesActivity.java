package com.example.tashbeehapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;

public class FavouritesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        ArrayList<Tasbeeh> data = (ArrayList<Tasbeeh>) getIntent().getSerializableExtra("favTasbeeh");

        ArrayAdapter adapter =  new ArrayAdapter<>(this, R.layout.activity_row, data);

        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }


}