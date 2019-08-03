package com.dsc.lapak.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;

import com.dsc.lapak.R;
import com.dsc.lapak.database.UserHelper;

public class MainActivity extends AppCompatActivity {
    UserHelper userHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userHelper = new UserHelper(this);

        if (!userHelper.isEmailExists("kangparkir@gmail.com", getApplicationContext())) {
            userHelper.insertAdmin();
        }

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
