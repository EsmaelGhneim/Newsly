package com.example.jumann;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelperNews extends SQLiteOpenHelper {

    private static final String DB_NAME = "news.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "news";


    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_IMAGE_PATH = "image_path";



    public DBHelperNews(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_TITLE + " TEXT," +
                COLUMN_DESCRIPTION + " TEXT," +
                COLUMN_IMAGE_PATH + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    @SuppressLint("Range")
    public Item getItemById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        Item item = null;
        if (cursor != null && cursor.moveToFirst()) {
            item = new Item();
            item.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
            item.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
            item.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));
            item.setImagePath(cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE_PATH)));
            cursor.close();
        }

        db.close();

        return item;
    }


    public void addItem(String title, String description, String imagePath) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_IMAGE_PATH, imagePath);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void deleteItem(int itemId) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(itemId)});
    }


public List<Item> getAllItems() {
    List<Item> itemList = new ArrayList<>();

    SQLiteDatabase db = getReadableDatabase();
    Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);

    if (cursor.moveToFirst()) {
        do {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION));
            String imagePath = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_PATH));

            Item item = new Item(id, title, description, imagePath); // Include imagePath parameter
            itemList.add(item);
        } while (cursor.moveToNext());
    }

    cursor.close();
    db.close();

    return itemList;
}}

