package com.tsotsi.assessment.myapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by TSOTSI on 2016/04/21.
 */
public class SQLiteDB extends SQLiteOpenHelper {

    private static final String DATABASE_CREATE = "create table " + AppConstants.TABLE_NAMES + "("
            + AppConstants.COLUMN_ID + " integer primary key, " + AppConstants.COLUMN_NAME + " text not null"
            + ");";
    private static SQLiteDB sqLiteDB;

    private SQLiteDB(Context context) {

        super(context, AppConstants.DATABASE_NAME, null, AppConstants.DATABASE_VERSION);
    }

    public static synchronized SQLiteDB getInstance(Context context) {
        if (sqLiteDB == null) {
            sqLiteDB = new SQLiteDB(context);
        }

        return sqLiteDB;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(this.getClass().getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + AppConstants.TABLE_NAMES);
        onCreate(db);
    }
}
