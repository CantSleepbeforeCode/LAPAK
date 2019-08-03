package com.dsc.lapak.activity.vehicle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.dsc.lapak.R;
import com.dsc.lapak.database.VehicleHelper;
import com.dsc.lapak.entity.Vehicle;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

public class AddVehicleActivity extends AppCompatActivity {
    Button buttonVehicle;
    TextInputLayout textInputLayout;
    EditText inputNoPolice;
    RadioGroup radioGroupVehicle;
    RadioButton radioButtonVehicle;

    VehicleHelper vehicleHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);

        vehicleHelper = new VehicleHelper(this);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        buttonVehicle = findViewById(R.id.button_add_vehicle);
        textInputLayout = findViewById(R.id.textInputLayout_police_number);
        inputNoPolice = findViewById(R.id.input_no_police);
        radioGroupVehicle = findViewById(R.id.radio_group_vehicle);

        buttonVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtNoPolice = inputNoPolice.getText().toString().trim().toUpperCase();
                Log.d("isi", txtNoPolice);
                if (validate()) {
                    if (!vehicleHelper.isNoPoliceExist(txtNoPolice, getApplicationContext())) {
                        int idRadio = radioGroupVehicle.getCheckedRadioButtonId();
                        radioButtonVehicle = findViewById(idRadio);
                        vehicleHelper.insert(new Vehicle(null, String.valueOf(radioButtonVehicle.getText()), txtNoPolice));
                        Snackbar.make(buttonVehicle, getString(R.string.vehicle_added), Snackbar.LENGTH_LONG).show();
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                Intent success = new Intent(getApplicationContext(), MyVehicleActivity.class);
                                startActivity(success);
                                finish();
                            }
                        }, 3000);
                    } else {
                        Snackbar.make(buttonVehicle, getString(R.string.vehicle_already_exist), Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return false;
    }

    public boolean validate() {
        boolean valid = false;
        String txtNoPolice = inputNoPolice.getText().toString().trim();
//        cek namanya
        if (txtNoPolice.isEmpty()) {
            inputNoPolice.setError(getString(R.string.error_no_police));
        } else {
            valid = true;
        }
        return valid;
    }
}
