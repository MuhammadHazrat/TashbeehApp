package com.example.tashbeehapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button plusBtn;
    Button minusBtn;
    TextView tvCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        plusBtn = findViewById(R.id.plusBtn);
        minusBtn = findViewById(R.id.minusBtn);
        tvCount = findViewById(R.id.textView);



    }

    public void minusButtonClicked(View view) {
        long counter = Integer.parseInt(tvCount.getText().toString());
        counter--;

        tvCount.setText(String.valueOf(counter));

    }

    public void plusBtnClicked(View view) {
        long counter = Integer.parseInt(tvCount.getText().toString());
        counter++;

        tvCount.setText(String.valueOf(counter));
    }
}