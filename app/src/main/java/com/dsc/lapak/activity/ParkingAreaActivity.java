package com.dsc.lapak.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.dsc.lapak.R;
import com.dsc.lapak.adapter.AreaAdapter;

public class ParkingAreaActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;
    AreaAdapter areaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_area);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        recyclerView = findViewById(R.id.recycler_area);

        gridLayoutManager = new GridLayoutManager(this, 2);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);

//        areaAdapter = new AreaAdapter(this);
//        recyclerView.setAdapter(areaAdapter);


    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return false;
    }
}
