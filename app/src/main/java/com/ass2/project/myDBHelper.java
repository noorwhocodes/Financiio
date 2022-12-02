package com.ass2.project;

import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class myDBHelper extends SQLiteOpenHelper {

    String CREATE_DATA_TABLE="CREATE TABLE " +
            dataContract.Data.TABLE_NAME+ "(" +
            dataContract.Data._ITEM_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            dataContract.Data._AMOUNT+ " TEXT NOT NULL, " +
            dataContract.Data._CATEGORY+ " TEXT NOT NULL, " +
            dataContract.Data._DESCRIPTION+ " TEXT, " +
            dataContract.Data._TIME+ " TEXT, " +
            dataContract.Data._IMAGE+ " TEXT ); ";

    String DELETE_DATA_TABLE="DROP TABLE IF EXISTS " + dataContract.Data.TABLE_NAME;


    public myDBHelper(@Nullable Context context) {
        super(context, dataContract.DB_NAME, null, dataContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_DATA_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DELETE_DATA_TABLE);
        onCreate(sqLiteDatabase);
    }

    public long getCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, dataContract.Data.TABLE_NAME);
        return count;
    }
}
