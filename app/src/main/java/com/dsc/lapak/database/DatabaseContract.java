package com.dsc.lapak.database;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static final String TABLE_VEHICLE = "vehicles";
    public static final String AUTHORITY = "com.dsc.lapak.database";
    private static final String SCHEME = "content";

    public static final Uri CONTENT_URI_TVSHOW = new Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_VEHICLE)
            .build();

    public static final class vehicleColoumn implements BaseColumns {
        public static String ID = "id";
        public static String TYPE = "type";
        public static String NO_POLICE = "no_police";
    }

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

}

