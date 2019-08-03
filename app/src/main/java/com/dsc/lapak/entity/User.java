package com.dsc.lapak.entity;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static com.dsc.lapak.database.DatabaseContract.getColumnString;
import static com.dsc.lapak.database.DatabaseContract.userColoumn.EMAIL;
import static com.dsc.lapak.database.DatabaseContract.userColoumn.ID;
import static com.dsc.lapak.database.DatabaseContract.userColoumn.PASSWORD;
import static com.dsc.lapak.database.DatabaseContract.userColoumn.USER_LEVEL;
import static com.dsc.lapak.database.DatabaseContract.userColoumn.USER_NAME;
import static com.dsc.lapak.database.DatabaseContract.userColoumn.USER_WALLET;

public class User implements Parcelable {
    private String id;
    public String userName;
    public String userLevel;
    public String userWallet;
    public String email;
    public String password;

    public User(String id, String userName, String userLevel, String userWallet, String email, String password){
        this.id = id;
        this.userName = userName;
        this.userLevel = userLevel;
        this.userWallet = userWallet;
        this.email = email;
        this.password = password;
    }

    public User() {}

    public User(Cursor cursor) {
        this.id = getColumnString(cursor, ID);
        this.userName = getColumnString(cursor, USER_NAME);
        this.userLevel = getColumnString(cursor, USER_LEVEL);
        this.userWallet = getColumnString(cursor, USER_WALLET);
        this.email = getColumnString(cursor, EMAIL);
        this.password = getColumnString(cursor, PASSWORD);
    }

    protected User(Parcel in) {
        id = in.readString();
        userName = in.readString();
        userLevel = in.readString();
        userWallet = in.readString();
        email = in.readString();
        password = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
    }

    public String getUserWallet() {
        return userWallet;
    }

    public void setUserWallet(String userWallet) {
        this.userWallet = userWallet;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(userName);
        parcel.writeString(userLevel);
        parcel.writeString(userWallet);
        parcel.writeString(email);
        parcel.writeString(password);
    }
}
