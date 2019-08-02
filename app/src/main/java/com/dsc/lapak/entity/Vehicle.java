package com.dsc.lapak.entity;

import android.database.Cursor;

import static com.dsc.lapak.database.DatabaseContract.getColumnString;
import static com.dsc.lapak.database.DatabaseContract.vehicleColoumn.ID;
import static com.dsc.lapak.database.DatabaseContract.vehicleColoumn.NO_POLICE;
import static com.dsc.lapak.database.DatabaseContract.vehicleColoumn.TYPE;

public class Vehicle {
    private String id;
    public String type;
    public String noPolice;

    public Vehicle(String id, String type, String noPolice){
        this.id = id;
        this.type = type;
        this.noPolice = noPolice;
    }

    public Vehicle(Cursor cursor) {
    this.id =getColumnString(cursor, ID);
    this.type = getColumnString(cursor, TYPE);
    this.noPolice = getColumnString(cursor, NO_POLICE);

    }

}
