package com.dsc.lapak.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.dsc.lapak.R;

public class QRPaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrpayment);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return false;
    }
}
