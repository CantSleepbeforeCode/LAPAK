package com.dsc.lapak.entity;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static com.dsc.lapak.database.DatabaseContract.getColumnString;
import static com.dsc.lapak.database.DatabaseContract.vehicleColoumn.ID;
import static com.dsc.lapak.database.DatabaseContract.vehicleColoumn.NO_POLICE;
import static com.dsc.lapak.database.DatabaseContract.vehicleColoumn.TYPE;

public class Vehicle implements Parcelable {
    private String id;
    public String type;
    public String noPolice;

    public Vehicle(String id, String type, String noPolice){
        this.id = id;
        this.type = type;
        this.noPolice = noPolice;
    }

    public Vehicle() {}

    public Vehicle(Cursor cursor) {
    this.id =getColumnString(cursor, ID);
    this.type = getColumnString(cursor, TYPE);
    this.noPolice = getColumnString(cursor, NO_POLICE);

    }

    protected Vehicle(Parcel in) {
        id = in.readString();
        type = in.readString();
        noPolice = in.readString();
    }

    public static final Creator<Vehicle> CREATOR = new Creator<Vehicle>() {
        @Override
        public Vehicle createFromParcel(Parcel in) {
            return new Vehicle(in);
        }

        @Override
        public Vehicle[] newArray(int size) {
            return new Vehicle[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNoPolice() {
        return noPolice;
    }

    public void setNoPolice(String noPolice) {
        this.noPolice = noPolice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(type);
        parcel.writeString(noPolice);
    }
}
