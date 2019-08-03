package com.dsc.lapak.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.dsc.lapak.database.UserHelper;
import com.dsc.lapak.entity.User;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ParkingProcess  extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private String KEY_CHECK = "key_check";
    UserHelper userHelper;

    String mitra_id, mitra_name, mitra_level, mitra_wallet, mitra_email, mitra_password;

    private ZXingScannerView scannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mitra_id = getIntent().getStringExtra("KEY_ID");
        mitra_name = getIntent().getStringExtra("KEY_NAME");
        mitra_level = getIntent().getStringExtra("KEY_LEVEL");
        mitra_wallet = getIntent().getStringExtra("KEY_BALANCE");
        mitra_email = getIntent().getStringExtra("KEY_EMAIL");
        mitra_password = getIntent().getStringExtra("KEY_PASSWORD");
        Log.d("testdata", "isi data: "+mitra_wallet);
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);

        userHelper = new UserHelper(this);
        userHelper.open();

    }

    @Override
    public void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        if (rawResult.getText().equals("https://lapak.com/check_in")) {
            Log.d("versi", "check_in");
            finish();
        } else if (rawResult.getText().equals("https://lapak.com/check_out")) {
            Log.d("versi", "check_out");
            String dataWallet = userHelper.walletUser(mitra_name).getString(4);
            int balance = Integer.parseInt(dataWallet);
            int newBalance = balance + 5000;
            String txtBalance = String.valueOf(newBalance);
            Log.d("test", mitra_id+ mitra_name+mitra_level+ txtBalance+ mitra_email+ mitra_password);
            userHelper.update(new User(mitra_id, mitra_name, mitra_level, txtBalance, mitra_email, mitra_password));

            finish();


        }

        scannerView.resumeCameraPreview(this);
    }

}
