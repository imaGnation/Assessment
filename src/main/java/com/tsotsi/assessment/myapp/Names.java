package com.tsotsi.assessment.myapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

/**
 * Created by TSOTSI on 2016/04/21.
 */
public class Names {
    private SQLiteDatabase database;
    private SQLiteDB dbHelper;

    private String[] column = {AppConstants.COLUMN_NAME};

    public Names(Context context) {
        dbHelper = SQLiteDB.getInstance(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public String getName(int _id) {
        String name = null;
        Cursor c = database.query(AppConstants.TABLE_NAMES, column, AppConstants.COLUMN_ID + " = " + _id, null, null, null, null);
        int count = c.getCount();
        if (count == 1) {
            c.moveToFirst();
            name = c.getString(c.getColumnIndex(AppConstants.COLUMN_NAME));
        }
        c.close();
//        database.close();
        return name;
    }

    public void addName(String name) {
        ContentValues values = new ContentValues();
        values.put(AppConstants.COLUMN_ID, AppConstants.DEFAULT_ID);
        values.put(AppConstants.COLUMN_NAME, name);

        database.execSQL("delete from " + AppConstants.TABLE_NAMES);
        database.insert(AppConstants.TABLE_NAMES, null, values);


    }

}