package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(Color.parseColor("#0A3D62"));


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

               Intent intent=new Intent(getApplicationContext(),MainActivity.class);
               startActivity(intent);
               Log.w("TAG", "run: first Page" );
            }
        },5000);
    }
}