package com.dsc.lapak.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dsc.lapak.R;
import com.dsc.lapak.activity.vehicle.MyVehicleActivity;
import com.google.android.material.snackbar.Snackbar;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView backgroundImage, topUpWallet;
    LinearLayout layoutSplashText, layoutWelcome, layoutHeader, layoutSub1, layoutSub2, layoutSub3, layoutSub4, layoutSub5, layoutSub6;
    LinearLayout layoutMyProfile, layoutMyVehicle, layoutHistory, layoutSettings;
    Animation welcomeText, menuAnim, subMenuAnim;

    TextView helloUser;

    private String KEY_NAME = "USER_NAME";
    private String KEY_LEVEL = "KEY_LEVEL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        helloUser = findViewById(R.id.txt_hello_user);
        String[] frontName = getIntent().getStringExtra("KEY_NAME").split(" ");
        String helloText = getString(R.string.hi_user) + " " + frontName[0] + "!";

        helloUser.setText(helloText);


        backgroundImage = findViewById(R.id.bg_splash);
        backgroundImage.animate().translationY(-2100).setDuration(1000).setStartDelay(500);

        layoutSplashText = findViewById(R.id.text_splash);
        layoutSplashText.animate().alpha(0).translationY(150).setDuration(1000).setStartDelay(500);

        welcomeText = AnimationUtils.loadAnimation(this, R.anim.welcome_text);
        layoutWelcome = findViewById(R.id.text_welcome);
        layoutWelcome.startAnimation(welcomeText);

        layoutHeader = findViewById(R.id.layout_header);
        layoutSub1 = findViewById(R.id.layout_parking_payment);
        layoutSub2 = findViewById(R.id.layout_search_parking);
        layoutSub3 = findViewById(R.id.layout_bike_rental);
        layoutSub4 = findViewById(R.id.layout_purchase_credit);
        layoutSub5 = findViewById(R.id.layout_electric_refill);
        layoutSub6 = findViewById(R.id.layout_water_payment);
        menuAnim = AnimationUtils.loadAnimation(this, R.anim.menu);
        subMenuAnim = AnimationUtils.loadAnimation(this, R.anim.sub_menu);

        layoutHeader.startAnimation(menuAnim);
        layoutSub1.startAnimation(subMenuAnim);
        layoutSub2.startAnimation(subMenuAnim);
        layoutSub3.startAnimation(subMenuAnim);
        layoutSub4.startAnimation(subMenuAnim);
        layoutSub5.startAnimation(subMenuAnim);
        layoutSub6.startAnimation(subMenuAnim);

        topUpWallet = findViewById(R.id.top_up_wallet);
        layoutMyProfile = findViewById(R.id.layout_my_profile);
        layoutMyVehicle = findViewById(R.id.layout_my_vehicle);
        layoutHistory = findViewById(R.id.layout_history);
        layoutSettings = findViewById(R.id.layout_settings);

        topUpWallet.setOnClickListener(this);
        layoutMyProfile.setOnClickListener(this);
        layoutMyVehicle.setOnClickListener(this);
        layoutHistory.setOnClickListener(this);
        layoutSettings.setOnClickListener(this);
        layoutSub1.setOnClickListener(this);
        layoutSub2.setOnClickListener(this);
        layoutSub3.setOnClickListener(this);
        layoutSub4.setOnClickListener(this);
        layoutSub5.setOnClickListener(this);
        layoutSub6.setOnClickListener(this);

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
            case R.id.layout_my_vehicle:
                Intent myVehicle = new Intent(this, MyVehicleActivity.class);
                startActivity(myVehicle);
                break;
            case R.id.layout_history:
                object = findViewById(R.id.layout_history);
                showSnackbar(object, message, duration);
                break;
            case R.id.layout_settings:
                object = findViewById(R.id.layout_settings);
                showSnackbar(object, message, duration);
                break;
            case R.id.layout_parking_payment:
                Intent qrPayment = new Intent(this, QRPaymentActivity.class);
                startActivity(qrPayment);
                break;
            case R.id.layout_search_parking:
                Intent searchParking = new Intent(this, ParkingAreaActivity.class);
                startActivity(searchParking);
                break;
            case R.id.layout_bike_rental:
                Intent bikeIntent = new Intent(this, BikeActivity.class);
                startActivity(bikeIntent);
                break;
            case R.id.layout_purchase_credit:
                object = findViewById(R.id.layout_purchase_credit);
                showSnackbar(object, message, duration);
                break;
            case R.id.layout_electric_refill:
                object = findViewById(R.id.layout_electric_refill);
                showSnackbar(object, message, duration);
                break;
            case R.id.layout_water_payment:
                object = findViewById(R.id.layout_water_payment);
                showSnackbar(object, message, duration);
                break;

        }
    }

    public void showSnackbar(View view, String message, int duration)
    {
        Snackbar.make(view, message, duration).show();
    }
}
