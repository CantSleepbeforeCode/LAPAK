package com.dsc.lapak.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.dsc.lapak.entity.Vehicle;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.dsc.lapak.database.DatabaseContract.TABLE_VEHICLE;
import static com.dsc.lapak.database.DatabaseContract.vehicleColoumn.ID;
import static com.dsc.lapak.database.DatabaseContract.vehicleColoumn.NO_POLICE;
import static com.dsc.lapak.database.DatabaseContract.vehicleColoumn.TYPE;

public class VehicleHelper {
    private static String DATABASE_TABLE_VEHICLE = TABLE_VEHICLE;
    private Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase sqLiteDatabase;

    public VehicleHelper(Context context) {
    this.context = context;
    }

    public VehicleHelper open() throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        return this;
    }

    public ArrayList<Vehicle> query() {
        ArrayList<Vehicle> arrayList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(DATABASE_TABLE_VEHICLE,
                null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        Vehicle vehicle;
        if (cursor.getCount() > 0) {
            do {
                vehicle = new Vehicle();
                vehicle.setId(cursor.getString(cursor.getColumnIndexOrThrow(ID)));
                vehicle.setType(cursor.getString(cursor.getColumnIndexOrThrow(TYPE)));
                vehicle.setNoPolice(cursor.getString(cursor.getColumnIndexOrThrow(NO_POLICE)));

                arrayList.add(vehicle);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(Vehicle vehicle) {
        open();
        ContentValues values = new ContentValues();
        values.put(ID, vehicle.getId());
        values.put(TYPE, vehicle.getType());
        values.put(NO_POLICE, vehicle.getNoPolice());
        return sqLiteDatabase.insert(DATABASE_TABLE_VEHICLE,null, values);
    }

    public int update(Vehicle vehicle) {
        ContentValues values = new ContentValues();
        values.put(ID, vehicle.getId());
        values.put(TYPE, vehicle.getType());
        values.put(NO_POLICE, vehicle.getNoPolice());
        return sqLiteDatabase.update(DATABASE_TABLE_VEHICLE, values, ID + " = '" + vehicle.getId() + "'", null);
    }

    public int delete(String vehicleId) {
        return sqLiteDatabase.delete(DATABASE_TABLE_VEHICLE, ID + " = '" + vehicleId + "'", null);
    }

    public Cursor queryByIdProvider(String vehicleId) {
        return sqLiteDatabase.query(DATABASE_TABLE_VEHICLE,
                null,
                ID + " = ?",
                new String[]{vehicleId},
                null,
                null,
                null,
                null);
    }

    public Cursor queryProvider() {
        return sqLiteDatabase.query(DATABASE_TABLE_VEHICLE,
                null,
                null,
                null,
                null,
                null,
                TYPE + " ASC");
    }

    public long insertProvider(ContentValues values) {
        return sqLiteDatabase.insert(DATABASE_TABLE_VEHICLE, null, values);
    }

    public int deleteProvider(String id) {
        return sqLiteDatabase.delete(DATABASE_TABLE_VEHICLE, ID + " = ?", new String[]{id});
    }

    public boolean isNoPoliceExist(String noPolice, Context context) {
        databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_VEHICLE,
                new String[]{ID, TYPE, NO_POLICE}, NO_POLICE + "=?",
                new String[]{noPolice},
                null, null, null);

        if (cursor != null && cursor.moveToFirst() && cursor.getCount() > 0) {
            return true;
        }
        return false;
    }
}