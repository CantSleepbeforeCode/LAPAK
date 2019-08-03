package com.dsc.lapak.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dsc.lapak.R;
import com.dsc.lapak.entity.Vehicle;

import java.util.ArrayList;

public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.VehicleViewHolder> {
    private Cursor cursor;
    private Context context;
    private ArrayList<Vehicle> vehicles;

    public VehicleAdapter(Context context) {
        this.context = context;
    }

    private ArrayList<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(Cursor cursor) {
        this.cursor = cursor;
    }

    @NonNull
    @Override
    public VehicleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VehicleViewHolder(LayoutInflater.from(context).inflate(R.layout.item_vehicle, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VehicleViewHolder holder, int position) {
        holder.BindItem(position);
    }

    @Override
    public int getItemCount() {
        if (cursor == null) return 0;
        return cursor.getCount();
    }

    private Vehicle getItem(int position) {
        if (!cursor.moveToPosition(position)) {
            throw new IllegalStateException("Position Invalid");
        }
        return new Vehicle(cursor);
    }

    class VehicleViewHolder extends RecyclerView.ViewHolder {
        private TextView vehicleNoPolice;
        private ImageView vehicleType;

        VehicleViewHolder(@NonNull View view) {
            super(view);
            vehicleNoPolice = view.findViewById(R.id.vehicle_no_police);
            vehicleType = view.findViewById(R.id.vehicle_type);
        }

        void BindItem(final int position) {
            final Vehicle vehicle = getItem(position);
            vehicleNoPolice.setText(vehicle.noPolice);
            if (vehicle.type.equals("Car")) {
                vehicleType.setImageResource(R.drawable.icon_vehicle_car);
            } else if (vehicle.type.equals("Motorcycle")) {
                vehicleType.setImageResource(R.drawable.icon_vehicle_bike);
            }

        }
    }
}
