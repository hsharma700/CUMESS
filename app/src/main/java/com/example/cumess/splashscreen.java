package com.example.cumess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class splashscreen extends AppCompatActivity {

    Handler h= new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent( splashscreen.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        },2000);
    }
}