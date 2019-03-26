package com.example.cereal_shopper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


public class DbAdapter {
    @SuppressWarnings("unused")
    private static final String LOG_TAG = DbAdapter.class.getSimpleName();
    private Context context;
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    // Database fields
    private static final String DATABASE_TABLE = "groups";
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";

    public DbAdapter(Context context) {
        this.context = context;
    }
    public DbAdapter open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }
    public void close() {
        dbHelper.close();
    }

    public long createGroup(String name ) {
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        return database.insert(DATABASE_TABLE, null, values);
    }
    public Cursor fetchAll() {
        return database.query(DATABASE_TABLE, new String[] { KEY_NAME }, null, null, null, null, null);
    }

}
