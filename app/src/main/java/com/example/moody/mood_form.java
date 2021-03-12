package com.example.moody;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class mood_form extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}