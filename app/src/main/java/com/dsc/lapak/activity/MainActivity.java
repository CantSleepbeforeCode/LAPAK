package com.dsc.lapak.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.dsc.lapak.R;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int splash_time = 4000;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent home = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(home);
                finish();
            }
        }, splash_time);

    }
}
