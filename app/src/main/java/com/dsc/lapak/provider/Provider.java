package com.dsc.lapak.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.dsc.lapak.database.DatabaseContract;
import com.dsc.lapak.database.UserHelper;
import com.dsc.lapak.database.VehicleHelper;

import java.util.Objects;

import static com.dsc.lapak.database.DatabaseContract.AUTHORITY;
import static com.dsc.lapak.database.DatabaseContract.CONTENT_URI_USER;
import static com.dsc.lapak.database.DatabaseContract.CONTENT_URI_VEHICLE;

public class Provider extends ContentProvider {
    private static final int VEHICLE = 1;
    private static final int VEHICLE_ID = 2;
    private static final int USER = 3;
    private static final int USER_ID = 4;
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private UserHelper userHelper;

    static {
        uriMatcher.addURI(AUTHORITY, DatabaseContract.TABLE_USER, USER);
        uriMatcher.addURI(AUTHORITY, DatabaseContract.TABLE_USER + "/#", USER_ID);
    }

    private VehicleHelper vehicleHelper;

    static {
        uriMatcher.addURI(AUTHORITY, DatabaseContract.TABLE_VEHICLE, VEHICLE);
        uriMatcher.addURI(AUTHORITY, DatabaseContract.TABLE_VEHICLE + "/#", VEHICLE_ID);
    }

    @Override
    public boolean onCreate() {
        userHelper = new UserHelper(getContext());
        vehicleHelper = new VehicleHelper(getContext());
        userHelper.open();
        vehicleHelper.open();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        Cursor cursor;
        switch (uriMatcher.match(uri)) {
            case VEHICLE:
                cursor = vehicleHelper.queryProvider();
                break;
            case VEHICLE_ID:
                cursor = vehicleHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            case USER:
                cursor = userHelper.queryProvider();
                break;
            case USER_ID:
                cursor = userHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }
        if (cursor != null) {
            cursor.setNotificationUri(Objects.requireNonNull(getContext()).getContentResolver(), uri);
        }
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        long added;
        Uri content = null;
        switch (uriMatcher.match(uri)) {
            case VEHICLE:
                added = vehicleHelper.insertProvider(contentValues);
                content = CONTENT_URI_VEHICLE;
                break;
            case USER:
                added = userHelper.insertProvider(contentValues);
                content = CONTENT_URI_USER;
                break;
            default:
                added = 0;
                break;
        }

        if (added > 0) {
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
        }
        return Uri.parse(content + "/" + added);
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        int deleted;
        switch (uriMatcher.match(uri)) {
            case VEHICLE_ID:
                deleted = vehicleHelper.deleteProvider(uri.getLastPathSegment());
                break;
            case USER_ID:
                deleted = userHelper.deleteProvider(uri.getLastPathSegment());
            default:
                deleted = 0;
        }
        if (deleted > 0) {
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
        }
        return deleted;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
