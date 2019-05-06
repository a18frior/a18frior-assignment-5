package org.brohede.marcus.sqliteapp;

import android.database.sqlite.SQLiteOpenHelper;


import java.util.LinkedList;
import java.util.List;
import java.util.jar.Attributes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

/**
 * Created by marcus on 2018-04-25.
 */

public class MountainReaderDbHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "mountains_db";


    public MountainReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(Mountain.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Mountain.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public long insertMountain(String Name) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Mountain.COLUMN_NAME, Name);

        // insert row
        long id = db.insert(Mountain.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }


    public Mountain getNote(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Mountain.TABLE_NAME,
                new String[]{Mountain.COLUMN_NAME, Mountain.COLUMN_LOCATION, Mountain.COLUMN_HEIGHT,Mountain.COLUMN_IMG_URL,Mountain.COLUMN_IMG_INFO },
                Mountain.COLUMN_NAME + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();


        Mountain mountain = new Mountain(

                cursor.getString(cursor.getColumnIndex(Mountain.COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndex(Mountain.COLUMN_LOCATION)),
                cursor.getInt(cursor.getColumnIndex(Mountain.COLUMN_HEIGHT)),
                cursor.getString(cursor.getColumnIndex(Mountain.COLUMN_IMG_URL)),
                cursor.getString(cursor.getColumnIndex(Mountain.COLUMN_IMG_INFO)));


        cursor.close();

        return mountain;
    }




    public int updateMountain(Mountain mountain) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Mountain.COLUMN_NAME, mountain.getName());

        // updating row
        return db.update(Mountain.TABLE_NAME, values, Mountain.COLUMN_HEIGHT + " = ?",
                new String[]{String.valueOf(mountain.getHeight())});
    }

    public void deleteMountain(Mountain mountain) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Mountain.TABLE_NAME, Mountain.COLUMN_HEIGHT + " = ?",
                new String[]{String.valueOf(mountain.getHeight())});
        db.close();
    }
}






