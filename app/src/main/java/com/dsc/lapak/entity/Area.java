package com.dsc.lapak.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Area implements Parcelable {
    private String title, carAvailable, motorAvailable, latitude, longtitude;
    private int imgCover;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCarAvailable() {
        return carAvailable;
    }

    public void setCarAvailable(String carAvailable) {
        this.carAvailable = carAvailable;
    }

    public String getMotorAvailable() {
        return motorAvailable;
    }

    public void setMotorAvailable(String motorAvailable) {
        this.motorAvailable = motorAvailable;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
    }

    public int getImgCover() {
        return imgCover;
    }

    public void setImgCover(int imgCover) {
        this.imgCover = imgCover;
    }

    public Area() {}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.title);
        parcel.writeString(this.carAvailable);
        parcel.writeString(this.motorAvailable);
        parcel.writeString(this.latitude);
        parcel.writeString(this.longtitude);
        parcel.writeInt(this.imgCover);
    }

    protected Area(Parcel parcel) {
        this.title = parcel.readString();
        this.carAvailable = parcel.readString();
        this.motorAvailable = parcel.readString();
        this.latitude = parcel.readString();
        this.longtitude = parcel.readString();
        this.imgCover = parcel.readInt();
    }

    public static final Creator<Area> CREATOR = new Creator<Area>() {
        @Override
        public Area createFromParcel(Parcel parcel) {
            return new Area(parcel);
        }

        @Override
        public Area[] newArray(int size) {
            return new Area[size];
        }
    };
}
