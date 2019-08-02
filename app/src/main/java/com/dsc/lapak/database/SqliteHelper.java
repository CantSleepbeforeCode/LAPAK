package com.dsc.lapak.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dsc.lapak.entity.User;
import com.dsc.lapak.entity.Vehicle;

public class SqliteHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "PointDeal";
    public static final int DATABASE_VERSION = 1;

//    table user
    public static final String TABLE_USERS = "users";
    public static final String KEY_ID = "id";
    public static final String KEY_USER_NAME = "username";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String SQL_TABLE_USERS = " CREATE TABLE " + TABLE_USERS
            + " ( "
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_USER_NAME + " TEXT, "
            + KEY_EMAIL + " TEXT, "
            + KEY_PASSWORD + " TEXT" + " ) ";

//    table vehicle
    public static final String TABLE_VEHICLE = "vehicles";
    public static final String KEY_ID_VEHICLE = "id";
    public static final String KEY_TYPE = "type";
    public static final String KEY_NO_POLICE = "NoPolice";
    public static final String SQL_TABLE_VEHICLE = " CREATE TABLE " + TABLE_VEHICLE
            + " ( "
            + KEY_ID_VEHICLE + " INTEGER PRIMARY KEY, "
            + KEY_TYPE + " TEXT, "
            + KEY_NO_POLICE + " TEXT " + " ) ";

    public SqliteHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        // membuat table ketika onCreate dipanggil
        sqLiteDatabase.execSQL(SQL_TABLE_USERS);
        sqLiteDatabase.execSQL(SQL_TABLE_VEHICLE);
    }

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int j){
        // drop table untuk membuaat yang baru jika database diupdate
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_USERS);
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_VEHICLE);
        onCreate(sqLiteDatabase);
    }

    // gunakan method ini supaya kita bisa menambah user ke table user
    public void addUser(User user){
        // ambil writable database
        SQLiteDatabase db = this.getWritableDatabase();

        // buat content value untuk dimasukkan
        ContentValues values = new ContentValues();

        // masukkan username ke @values
        values.put(KEY_USER_NAME, user.userName);

        // masukkan email ke @values
        values.put(KEY_EMAIL, user.email);

        // masukkan password ke @values
        values.put(KEY_PASSWORD, user.password);

        // masukkan baris
        long todo_id = db.insert(TABLE_USERS, null, values);
    }

    public void addVehicle(Vehicle vehicle) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(KEY_TYPE, vehicle.type);
        contentValues.put(KEY_NO_POLICE, vehicle.noPolice);
        long todo_id = db.insert(TABLE_VEHICLE, null, contentValues);
    }

    public String idUser = "";
    public String nameUser = "";
    public String emailUser = "";

    public User Authenticate(User user){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, // Milih Table
                new String[]{KEY_ID, KEY_USER_NAME, KEY_EMAIL, KEY_PASSWORD}, // milih kolom untuk di query
                KEY_EMAIL + "=?",
                new String[]{user.email}, // ayat ke
                null, null, null );
        if(cursor != null && cursor.moveToFirst()&& cursor.getCount()>0){
            //jika kursor memiliki nilai maka dalam basis data pengguna ada pengguna yang terkait dengan email yang diberikan ini
            User user1 = new User(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
            // Cocokkan kedua kata sandi periksa apakah sama atau tidak
            if(user.password.equalsIgnoreCase(user1.password)){
                idUser = cursor.getString(0);
                nameUser = cursor.getString(1);
                emailUser = cursor.getString(2);
                return user1;
            }
        }
        //jika kata sandi pengguna tidak cocok atau tidak ada catatan dengan email itu maka kembalikan @false
        return null;
    }

    public boolean isEmailExists(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, // Milih Table
                new String[]{KEY_ID, KEY_USER_NAME, KEY_EMAIL, KEY_PASSWORD}, // milih kolom untuk di query
                KEY_EMAIL + "=?",
                new String[]{email}, // ayat ke
                null, null, null );
        if(cursor != null && cursor.moveToFirst() && cursor.getCount() > 0){
            //jika kursor memiliki nilai maka dalam basis data pengguna ada pengguna yang terkait dengan email yang diberikan ini jadi kembalikan benar
            return true;
        }
        return false;
    }

    public boolean isNoPoliceExist(String noPolice) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_VEHICLE,
                new String[]{KEY_ID_VEHICLE, KEY_TYPE, KEY_NO_POLICE}, KEY_NO_POLICE + "=?",
                new String[]{noPolice},
                null, null, null);

        if (cursor != null && cursor.moveToFirst() && cursor.getCount() > 0) {
            return true;
        }
        return false;
    }
}
