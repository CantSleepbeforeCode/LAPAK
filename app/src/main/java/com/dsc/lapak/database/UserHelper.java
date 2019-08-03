package com.dsc.lapak.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.dsc.lapak.entity.User;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.dsc.lapak.database.DatabaseContract.TABLE_USER;
import static com.dsc.lapak.database.DatabaseContract.userColoumn.EMAIL;
import static com.dsc.lapak.database.DatabaseContract.userColoumn.ID;
import static com.dsc.lapak.database.DatabaseContract.userColoumn.PASSWORD;
import static com.dsc.lapak.database.DatabaseContract.userColoumn.USER_LEVEL;
import static com.dsc.lapak.database.DatabaseContract.userColoumn.USER_NAME;
import static com.dsc.lapak.database.DatabaseContract.userColoumn.USER_WALLET;

public class UserHelper {
    private static String DATABASE_TABLE_USER = TABLE_USER;
    private Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase sqLiteDatabase;

    public UserHelper(Context context) {
        this.context = context;
    }

    public UserHelper open() throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        return this;
    }

    public ArrayList<User> query() {
        ArrayList<User> arrayList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(DATABASE_TABLE_USER,
                null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        User user;
        if (cursor.getCount() > 0) {
            do {
                user = new User();
                user.setId(cursor.getString(cursor.getColumnIndexOrThrow(ID)));
                user.setUserName(cursor.getString(cursor.getColumnIndexOrThrow(USER_NAME)));
                user.setUserLevel(cursor.getString(cursor.getColumnIndexOrThrow(USER_LEVEL)));
                user.setUserWallet(cursor.getString(cursor.getColumnIndexOrThrow(USER_WALLET)));
                user.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndexOrThrow(PASSWORD)));

                arrayList.add(user);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(User user) {
        open();
        ContentValues values = new ContentValues();
        values.put(ID, user.getId());
        values.put(USER_NAME, user.getUserName());
        values.put(USER_LEVEL, user.getUserLevel());
        values.put(USER_WALLET, user.getUserWallet());
        values.put(EMAIL, user.getEmail());
        values.put(PASSWORD, user.getPassword());
        return sqLiteDatabase.insert(DATABASE_TABLE_USER,null, values);
    }

    public long insertAdmin() {
        ContentValues values = new ContentValues();
        open();
        values.put(USER_NAME, "Jupri Gonzales");
        values.put(USER_LEVEL, "mitra");
        values.put(USER_WALLET, "0");
        values.put(EMAIL, "kangparkir@gmail.com");
        values.put(PASSWORD, "123456");
        return sqLiteDatabase.insert(DATABASE_TABLE_USER,null, values);
    }

    public int update(User user) {
        ContentValues values = new ContentValues();
        values.put(ID, user.getId());
        values.put(USER_NAME, user.getUserName());
        values.put(USER_LEVEL, user.getUserLevel());
        values.put(USER_WALLET, user.getUserWallet());
        values.put(EMAIL, user.getEmail());
        values.put(PASSWORD, user.getPassword());
        Log.d("update", DATABASE_TABLE_USER + " \n" + values + " \n" + USER_NAME + " = '" + user.getUserName() + " \n" + null);
        return sqLiteDatabase.update(DATABASE_TABLE_USER, values, USER_NAME + " = '" + user.getUserName() + "';", null);
    }

    public Cursor walletUser(String name) {
        return sqLiteDatabase.rawQuery("SELECT * FROM " + DATABASE_TABLE_USER + " WHERE " + USER_NAME + " = '" + name + "';", null);
    }

    public int delete(String userId) {
        return sqLiteDatabase.delete(DATABASE_TABLE_USER, ID + " = '" + userId + "'", null);
    }

    public Cursor queryByIdProvider(String userId) {
        return sqLiteDatabase.query(DATABASE_TABLE_USER,
                null,
                ID + " = ?",
                new String[]{userId},
                null,
                null,
                null,
                null);
    }

    public Cursor queryProvider() {
        return sqLiteDatabase.query(DATABASE_TABLE_USER,
                null,
                null,
                null,
                null,
                null,
                USER_NAME + " ASC");
    }

    public long insertProvider(ContentValues values) {
        return sqLiteDatabase.insert(DATABASE_TABLE_USER, null, values);
    }

    public int deleteProvider(String id) {
        return sqLiteDatabase.delete(DATABASE_TABLE_USER, ID + " = ?", new String[]{id});
    }

    public String idUser = "";
    public String nameUser = "";
    public String userLevel = "";
    public String userBalance = "";
    public String emailUser = "";
    public String passwordUser = "";

    public User Authenticate(User user, Context context){
        databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER, // Milih Table
                new String[]{ID, USER_NAME, USER_LEVEL, USER_WALLET, EMAIL, PASSWORD}, // milih kolom untuk di query
                EMAIL + "=?",
                new String[]{user.email}, // ayat ke
                null, null, null );
        if(cursor != null && cursor.moveToFirst()&& cursor.getCount()>0){
            //jika kursor memiliki nilai maka dalam basis data pengguna ada pengguna yang terkait dengan email yang diberikan ini
            User user1 = new User(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));
            // Cocokkan kedua kata sandi periksa apakah sama atau tidak
            if(user.password.equalsIgnoreCase(user1.password)){
                idUser = cursor.getString(0);
                nameUser = cursor.getString(1);
                userLevel = cursor.getString(2);
                userBalance = cursor.getString(3);
                emailUser = cursor.getString(4);
                passwordUser = cursor.getString(5);

                return user1;
            }
            return null;
        }
        //jika kata sandi pengguna tidak cocok atau tidak ada catatan dengan email itu maka kembalikan @false
        return null;
    }

    public boolean isEmailExists(String email, Context context){
        databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER, // Milih Table
                new String[]{ID, USER_NAME, USER_LEVEL, USER_WALLET, EMAIL, PASSWORD}, // milih kolom untuk di query
                EMAIL + "=?",
                new String[]{email}, // ayat ke
                null, null, null );
        if(cursor != null && cursor.moveToFirst() && cursor.getCount() > 0){
            //jika kursor memiliki nilai maka dalam basis data pengguna ada pengguna yang terkait dengan email yang diberikan ini jadi kembalikan benar
            return true;
        }
        return false;
    }
}