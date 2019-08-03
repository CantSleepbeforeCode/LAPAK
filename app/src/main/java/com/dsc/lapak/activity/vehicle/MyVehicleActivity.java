package com.dsc.lapak.activity.vehicle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.dsc.lapak.R;
import com.dsc.lapak.adapter.VehicleAdapter;
import com.dsc.lapak.database.VehicleHelper;

import java.util.Objects;

import static com.dsc.lapak.database.DatabaseContract.CONTENT_URI_VEHICLE;

public class MyVehicleActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView noData;
    private VehicleAdapter vehicleAdapter;
    VehicleHelper vehicleHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_vehicle);

        vehicleAdapter = new VehicleAdapter(this);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        recyclerView = findViewById(R.id.recycler_vehicle);
        noData = findViewById(R.id.no_data_vehicle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        vehicleAdapter = new VehicleAdapter(this);
        new LoadVehicle().execute();
    }

    @Override
    public void onResume() {
        super.onResume();
        new LoadVehicle().execute();
    }

    private class LoadVehicle extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            noData.setVisibility(View.GONE);
        }

        protected Cursor doInBackground(Void... voids) {
            return Objects.requireNonNull(getApplicationContext()).getContentResolver().query(CONTENT_URI_VEHICLE, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            noData.setVisibility(View.GONE);
            vehicleAdapter.setVehicles(cursor);
            vehicleAdapter.notifyDataSetChanged();
            recyclerView.setAdapter(vehicleAdapter);

            int count = 0;

            try {
                count = ((cursor.getCount() > 0) ? cursor.getCount():0);
            } catch (Exception e) {
                Log.w("DataError", Objects.requireNonNull(e.getMessage()));
            }
            if (count == 0) {
                noData.setVisibility(View.VISIBLE);
            }

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
