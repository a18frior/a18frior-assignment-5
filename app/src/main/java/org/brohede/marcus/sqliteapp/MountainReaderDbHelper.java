package org.brohede.marcus.sqliteapp;

import android.database.sqlite.SQLiteOpenHelper;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.jar.Attributes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.provider.ContactsContract;

import android.util.Log;


public class MountainReaderDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 55;
    public static final String DATABASE_NAME = "MountainReader.db";
    // Contacts table name
    private static final String TABLE_MOUNTAIN ="mountain";

    public static final String COLUMN_NAME_Name = "Name";
    public static final String COLUMN_NAME_Location = "Location";
    public static final String COLUMN_NAME_Height= "Height";
    public static final String COLUMN_NAME_InfoUrl= "InfoUrl";
    public static final String COLUMN_NAME_Img_url= "Img_url";



    public MountainReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE "+ TABLE_MOUNTAIN + "("
        + COLUMN_NAME_Name+ " TEXT PRIMARY KEY," + COLUMN_NAME_Location + " TEXT,"
        + COLUMN_NAME_Height + " TEXT,"+ COLUMN_NAME_InfoUrl + " TEXT," + COLUMN_NAME_Img_url+  " TEXT" +")";

        Log.e("CREATE", CREATE_CONTACTS_TABLE);
        db.execSQL(CREATE_CONTACTS_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_MOUNTAIN);
// Creating tables again
        onCreate(db);
    }
    // Adding new shop
    public void addmountain(Mountain mountain) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_Name, mountain.getName());
        values.put(COLUMN_NAME_Location,mountain.getLocation());
        values.put(COLUMN_NAME_Height,mountain.getHeight());
        values.put(COLUMN_NAME_Img_url,mountain.getImg_url());
        values.put(COLUMN_NAME_InfoUrl,mountain.getInfoUrl());
// Inserting Row
        db.insert(TABLE_MOUNTAIN, null, values);

        String countQuery = "SELECT * FROM " + TABLE_MOUNTAIN;
        Cursor cursor = db.rawQuery(countQuery, null);

// return count
        Log.d("FridaRockar",""+cursor.getCount() + " added: " +mountain.getName());

        /*db.close(); // Closing database connection*/
    }

    public  Mountain getmountain(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_MOUNTAIN, new String[]{COLUMN_NAME_Name,
                COLUMN_NAME_Location, COLUMN_NAME_Height, COLUMN_NAME_Img_url, COLUMN_NAME_InfoUrl}, COLUMN_NAME_Name + "=?",
        new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Mountain mountain = new Mountain(cursor.getString(0),
                cursor.getString(1), cursor.getString(2),cursor.getString(3), cursor.getString(4));

        return mountain;
    }

    public int getDBSSize(){

        String selectQuery = "SELECT * FROM " + TABLE_MOUNTAIN;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor.getCount();
    }

    public ArrayList<Mountain> getAllMountains() {
        Log.d("DBSconnection", "getAllMountains()");
        ArrayList <Mountain> bergTemp = new ArrayList<Mountain>();
// Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_MOUNTAIN;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.d("Frida rocker", ""+cursor.getCount());

// looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            int counter = 0;
            do {
            //for(int i = 0;  i < cursor.getCount(); i++) {
             //   Mountain mountain;
                /*
                Mountain mountain = new Mountain(COLUMN_NAME_Name, COLUMN_NAME_Location, COLUMN_NAME_Height, COLUMN_NAME_Img_url, COLUMN_NAME_InfoUrl);


                mountain.setName(cursor.getString(0));
                mountain.setLocation(cursor.getString(1));
                mountain.setHeight(cursor.getString(2));
                mountain.setImg_url(cursor.getString(3));
                mountain.setInfoUrl(cursor.getString(4));*/

                String name = cursor.getString(0);
                String location = cursor.getString(1);
                String height = cursor.getString(2);
                String imgUrl = cursor.getString(3);
                String infoURL = cursor.getString(4);
                //mountain = new Mountain(
                bergTemp.add(new Mountain(
                        name,
                        location,
                        height,
                        imgUrl,
                        infoURL
                ));
                Log.d("Frida Rockar4", "_" + counter + "_" + cursor.getString(0) + " " + cursor.getString(1));

// Adding contact to list
                //bergTemp.add(mountain);

                Log.d("Frida RockarAddMountain", "_" + counter + "_ size:" + bergTemp.size());
            for (int j = 0; j < bergTemp.size(); j++) {
                Log.d("Frida RockarAddMountain", "\t_" + counter +""+j+ "" + bergTemp.get(j).getName());
            }

            counter++;

            } while (cursor.moveToNext());
        }
        for (int i =0; i < bergTemp.size();i++) {
            String log = "_"+i+"_"+ "Name:" + bergTemp.get(i).getName() + ",Location:" + bergTemp.get(i).getLocation() + "Height" + bergTemp.get(i).getHeight();
// Writing shops to log
            Log.d( "FridaDBSConnection: : ", log);
        }
Log.d("Frida Rockar4", ""+bergTemp.size());
        return bergTemp;
    }
    // Getting shops Count
    public int getShopsCount() {
        String countQuery = "SELECT * FROM " + TABLE_MOUNTAIN;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

// return count
        return cursor.getCount();
    }
    // Updating a shop
    public int updatemountain(Mountain mountain) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_Name, mountain.getName());
        values.put(COLUMN_NAME_Location, mountain.getLocation());
        values.put(COLUMN_NAME_Height, mountain.getHeight());
        values.put(COLUMN_NAME_Img_url, mountain.getImg_url());
        values.put(COLUMN_NAME_InfoUrl, mountain.getInfoUrl());
// updating row
        return db.update(TABLE_MOUNTAIN, values, COLUMN_NAME_Name+ " = ?",
        new String[]{String.valueOf(mountain.getName())});
    }

    // Deleting a shop
    public void deleteShop(Mountain mountain) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MOUNTAIN, COLUMN_NAME_Name + " = ?",
        new String[] { String.valueOf(mountain.getName()) });
        /*db.close();*/
    }
}

