package com.dsc.lapak.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dsc.lapak.R;
import com.dsc.lapak.activity.vehicle.MyVehicleActivity;
import com.google.android.material.snackbar.Snackbar;

import java.text.NumberFormat;
import java.util.Locale;

public class MitraActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView backgroundImage;
    LinearLayout layoutSplashText, layoutWelcome, layoutHeader;
    LinearLayout layoutMyProfile, layoutCheckIn, layoutCheckOut, layoutHistory, layoutSettings;
    Animation welcomeText, menuAnim, horizontalAnim;

    TextView helloUser, txtBalance;

    private String KEY_NAME = "USER_NAME";
    private String KEY_LEVEL = "KEY_LEVEL";
    private String KEY_BALANCE = "KEY_BALANCE";

    String id_mitra, name_mitra, level_mitra, balance_mitra, email_mitra, password_mitra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mitra);
        helloUser = findViewById(R.id.txt_hello_user);
        txtBalance = findViewById(R.id.txt_balance);
        LinearLayout horizontalBase = findViewById(R.id.base_horizontal_scroll);
        String[] frontName = getIntent().getStringExtra("KEY_NAME").split(" ");
        String helloText = getString(R.string.hi_user) + " " + frontName[0] + "!";

        id_mitra = getIntent().getStringExtra("KEY_ID");
        name_mitra = getIntent().getStringExtra("KEY_NAME");
        level_mitra = getIntent().getStringExtra("KEY_LEVEL");
        balance_mitra = getIntent().getStringExtra("KEY_BALANCE");
        email_mitra = getIntent().getStringExtra("KEY_EMAIL");
        password_mitra = getIntent().getStringExtra("KEY_PASSWORD");

        String wallet = getIntent().getStringExtra("KEY_BALANCE");
        double amount = Double.parseDouble(wallet);
        Locale localeID = new Locale("in", "ID");
        txtBalance.setText(NumberFormat.getCurrencyInstance(localeID).format(amount));

        helloUser.setText(helloText);

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, 100);
        }

        backgroundImage = findViewById(R.id.bg_splash);
        backgroundImage.animate().translationY(-2100).setDuration(1000).setStartDelay(500);

        layoutSplashText = findViewById(R.id.text_splash);
        layoutSplashText.animate().alpha(0).translationY(150).setDuration(1000).setStartDelay(500);

        welcomeText = AnimationUtils.loadAnimation(this, R.anim.welcome_text);
        layoutWelcome = findViewById(R.id.text_welcome);
        layoutWelcome.startAnimation(welcomeText);
        horizontalAnim = AnimationUtils.loadAnimation(this, R.anim.horizontal_anim);
        horizontalBase.startAnimation(horizontalAnim);

        layoutHeader = findViewById(R.id.layout_header);
        menuAnim = AnimationUtils.loadAnimation(this, R.anim.menu);

        layoutHeader.startAnimation(menuAnim);

        layoutMyProfile = findViewById(R.id.layout_my_mitra_profile);
        layoutCheckIn = findViewById(R.id.layout_check_in);
        layoutCheckOut = findViewById(R.id.layout_check_out);
        layoutHistory = findViewById(R.id.layout_history_parking);
        layoutSettings = findViewById(R.id.layout_settings_mitra);

        layoutMyProfile.setOnClickListener(this);
        layoutCheckIn.setOnClickListener(this);
        layoutCheckOut.setOnClickListener(this);
        layoutHistory.setOnClickListener(this);
        layoutSettings.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        String message = getString(R.string.feature_not_available);
        int duration = Snackbar.LENGTH_LONG;
        View object;
        switch (view.getId()) {
            case R.id.top_up_wallet:
                object = findViewById(R.id.top_up_wallet);
                showSnackbar(object, message, duration);
                break;
            case R.id.layout_my_profile:
                object = findViewById(R.id.layout_my_profile);
                showSnackbar(object, message, duration);
                break;
            case R.id.layout_check_in:
                Intent checkIn = new Intent(this, ParkingProcess.class);
                checkIn.putExtra("KEY_ID", id_mitra);
                checkIn.putExtra("KEY_NAME", name_mitra);
                checkIn.putExtra("KEY_LEVEL", level_mitra);
                checkIn.putExtra("KEY_BALANCE", balance_mitra);
                checkIn.putExtra("KEY_EMAIL", email_mitra);
                checkIn.putExtra("KEY_PASSWORD", password_mitra);
                checkIn.putExtra("KEY_CHECK", "https://lapak.com/check_in");
                startActivity(checkIn);
                break;
            case R.id.layout_check_out:
                Intent checkOut = new Intent(this, ParkingProcess.class);
                checkOut.putExtra("KEY_ID", id_mitra);
                checkOut.putExtra("KEY_NAME", name_mitra);
                checkOut.putExtra("KEY_LEVEL", level_mitra);
                checkOut.putExtra("KEY_BALANCE", balance_mitra);
                checkOut.putExtra("KEY_EMAIL", email_mitra);
                checkOut.putExtra("KEY_PASSWORD", password_mitra);
                checkOut.putExtra("KEY_CHECK", "https://lapak.com/check_out");
                startActivity(checkOut);
                break;
            case R.id.layout_history:
                object = findViewById(R.id.layout_history);
                showSnackbar(object, message, duration);
                break;
            case R.id.layout_settings:
                object = findViewById(R.id.layout_settings);
                showSnackbar(object, message, duration);
                break;
        }
    }

    public void showSnackbar(View view, String message, int duration)
    {
        Snackbar.make(view, message, duration).show();
    }
}
