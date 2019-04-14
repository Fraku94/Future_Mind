package com.example.fraku.future_mind;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class DatabaseHandlerData extends SQLiteOpenHelper {

    // All Static variables

    //Database Version
    private static final int DATABASE_VERSION = 1;
    //Database Name
    private static final String DATABASE_NAME = "DataManager";
    //Database table name
    private static final String TABLE_DATA = "data";

    // Contacts Table Columns name
    private static final String KEY_ID = "order_id";
    private static final String KEY_MODIFICATION_DATE = "modification_date";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_TITLE = "title";
    private static final String KEY_IMAGE_URL = "image_url";
    private static final String KEY_WEB_URL = "web_url";

    public DatabaseHandlerData(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_QUESTION_TABLE = "CREATE TABLE " + TABLE_DATA + "("
                + KEY_ID + " TEXT,"
                + KEY_MODIFICATION_DATE + " TEXT,"
                + KEY_DESCRIPTION + " TEXT,"
                + KEY_TITLE + " TEXT,"
                + KEY_IMAGE_URL + " TEXT,"
                + KEY_WEB_URL + " TEXT" + ")";
        db.execSQL(CREATE_QUESTION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop older table if existed

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATA);

        //Create table again
        onCreate(db);
    }

    //Adding new contact
    public void addData(DataObject DataObject) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, DataObject.getOrderId());
        values.put(KEY_MODIFICATION_DATE, DataObject.getModificationDate());
        values.put(KEY_DESCRIPTION, DataObject.getDescription());
        values.put(KEY_TITLE, DataObject.getTitle());
        values.put(KEY_IMAGE_URL, DataObject.getImageUrl());
        values.put(KEY_WEB_URL, DataObject.getWebUrl());

        //Inserting Row
        db.insert(TABLE_DATA, null, values);
        db.close();
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor AllData = db.rawQuery("SELECT * FROM " + TABLE_DATA, null);
        return AllData;
    }

    public Cursor getData(String Id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursorData = db.rawQuery(" SELECT " + KEY_WEB_URL + " FROM "
                + TABLE_DATA + " WHERE " + KEY_ID + " IS " + Id , null);

        return cursorData;
    }

    public void onClear() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATA);
        onCreate(db);
    }
}
