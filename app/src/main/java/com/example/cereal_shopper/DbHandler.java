package com.example.cereal_shopper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHandler extends SQLiteOpenHelper {
    //information of database
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "studentDB.db";
    public static final String TABLE_NAME = "groups";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_NAME = "title";
    //initialize the database
    public DbHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("+ COLUMN_TYPE +
                " TEXT, " + COLUMN_NAME + " TEXT )";
        db.execSQL(CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {}
    public String loadHandler() {
        String result = "";
        String query = "Select * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            int result_0 = cursor.getInt(0);
            String result_1 = cursor.getString(1);
            result += String.valueOf(result_0) + " " + result_1 +
                    System.getProperty("line.separator");
        }
        cursor.close();
        db.close();
        return result;
    }
    public void addHandler(String _type, String _title) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_TYPE, _type);
        values.put(COLUMN_NAME, _title);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public String findType(int position) {
        String query = "Select * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        String type;
        if (cursor.moveToPosition(position)) {
            cursor.moveToPosition(position);
            type= cursor.getString(0);

            cursor.close();
        } else {
            type = null;
        }
        db.close();
        return type;
    }


    public String findTitle(int position) {
        String query = "Select * FROM " + TABLE_NAME ;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        String title;
        if (!cursor.moveToPosition(position)) {
            throw new IllegalStateException("couldn't move cursor to position " + position);
        }
        else {
            cursor.moveToPosition(position);
            title= cursor.getString(1);

            cursor.close();
        }
        db.close();
        return title;
    }


    public boolean deleteHandler(String title2delate) {
        boolean result = false;
        String query = "Select*FROM" + TABLE_NAME + "WHERE" + COLUMN_NAME + "= '" + title2delate + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {

            db.delete(TABLE_NAME, COLUMN_NAME + "=?",
                    new String[] {
                            String.valueOf(title2delate)
                    });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }
    public boolean updateHandler(String type, String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(COLUMN_TYPE, type);
        args.put(COLUMN_NAME, title);
        return db.update(TABLE_NAME, args, COLUMN_NAME + "=" + title, null) > 0;
    }
}