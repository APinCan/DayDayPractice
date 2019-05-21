package com.example.navigatinodrawaertest;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "DATABASE_MEMO";

    // Table Names
    private static final String DB_TABLE = "MEMODATA";

    // column names
    private static final String KEY_TITLE = "TITLE";
    private static final String KEY_MAIN = "MAIN";
    private static final String KEY_ADDRESS = "ADDRESS";
    private static final String KEY_DAY = "DAY";
    private static final String KEY_IMAGE = "IMAGE";

    // Table create statement
    private static final String CREATE_TABLE_IMAGE = "CREATE TABLE " + DB_TABLE + "("+
            KEY_TITLE + " TEXT," +
            KEY_MAIN + " TEXT," +
            KEY_ADDRESS + " TEXT,"+
            KEY_DAY + " TEXT," +
            KEY_IMAGE + " BLOB);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_IMAGE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //업그레이드할때 옛날 테이블을 지우고
        db.execSQL("DROP TABLE IF EXISTS "+ DB_TABLE);

        //새로운 테이블 생성성
       onCreate(db);
    }

    public void addEntry( String title, String main, String address, String day, byte[] image) throws SQLiteException {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv = new  ContentValues();
        cv.put(KEY_TITLE,     title);
        cv.put(KEY_MAIN,        main);
        cv.put(KEY_ADDRESS,     address);
        cv.put(KEY_DAY,         day);
        cv.put(KEY_IMAGE,     image);
        database.insert( DB_TABLE, null, cv );
    }

    //비트맵을 bytes로 변환
//    // convert from bitmap to byte array
//    public static byte[] getBytes(Bitmap bitmap) {
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        bitmap.compress(CompressFormat.PNG, 0, stream);
//        return stream.toByteArray();
//    }
//
//    // convert from byte array to bitmap
//    public static Bitmap getImage(byte[] image) {
//        return BitmapFactory.decodeByteArray(image, 0, image.length);
//    }
}
