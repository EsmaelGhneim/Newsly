package com.example.jumann;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "login.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "users";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_EMAIL = "email";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_USERNAME + " TEXT PRIMARY KEY," +
                COLUMN_PASSWORD + " TEXT," +
                COLUMN_EMAIL + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public String getUserEmail(String username) {
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {COLUMN_EMAIL};
        String selection = COLUMN_USERNAME + " = ?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query(
                TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        String email = "";

        if (cursor.moveToFirst()) {
            email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL));
        }

        cursor.close();
        db.close();

        return email;
    }
    public void addUser(String username, String password, String email) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_EMAIL, email);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {COLUMN_PASSWORD};
        String selection = COLUMN_USERNAME + " = ?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query(
                TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            String savedPassword = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD));
            if (savedPassword.equals(password)) {
                cursor.close();
                db.close();
                return true;
            }
        }

        cursor.close();
        db.close();
        return false;
    }

    public List<String> getAllUsernames() {
        List<String> usernames = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_USERNAME + " FROM " + TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                String username = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME));
                usernames.add(username);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return usernames;
    }

    public void updatePassword(String username, String newPassword) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_PASSWORD, newPassword);

        String selection = COLUMN_USERNAME + " = ?";
        String[] selectionArgs = {username};

        db.update(TABLE_NAME, values, selection, selectionArgs);
        db.close();
    }

    public void updateUserEmail(String username, String newEmail) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, newEmail);

        String selection = COLUMN_USERNAME + " = ?";
        String[] selectionArgs = {username};

        db.update(TABLE_NAME, values, selection, selectionArgs);
        db.close();
    }

    public void deleteUser(String username) {
        SQLiteDatabase db = getWritableDatabase();

        String selection = COLUMN_USERNAME + " = ?";
        String[] selectionArgs = {username};

        db.delete(TABLE_NAME, selection, selectionArgs);
        db.close();
    }
}






