package com.dsc.lapak.activity.vehicle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.dsc.lapak.R;

public class MyVehicleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_vehicle);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.vehicle_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.navigation_add:
                Intent addVehicle = new Intent(this, AddVehicleActivity.class);
                startActivity(addVehicle);
                return true;
            case android.R.id.home:
                finish();
                return true;
        }
        return false;
    }
}
