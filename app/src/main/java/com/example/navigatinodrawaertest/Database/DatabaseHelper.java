package com.example.navigatinodrawaertest.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.navigatinodrawaertest.MemoAdapter;
import com.example.navigatinodrawaertest.Datas.MemoData;

//database를 사용하기 위한 클래스
public class DatabaseHelper extends SQLiteOpenHelper {
    SQLiteDatabase db;
    MemoAdapter memoAdapter;

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "DATABASE_MEMO";

    // Table Names
    private static final String DB_TABLE = "MEMODATA";

    // column names
    private static final String KEY_ID="ID";
    private static final String KEY_TITLE = "TITLE";
    private static final String KEY_MAIN = "MAIN";
    private static final String KEY_ADDRESS = "ADDRESS";
    private static final String KEY_DAY = "DAY";
    private static final String KEY_IMAGE = "IMAGE";

    // Table create statement
    private static final String CREATE_TABLE_IMAGE = "CREATE TABLE " + DB_TABLE + "(" +
            KEY_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_TITLE + " TEXT," +
            KEY_MAIN + " TEXT," +
            KEY_ADDRESS + " TEXT," +
            KEY_DAY + " TEXT," +
            KEY_IMAGE + " BLOB);";

    private static final String SQL_SELECT = "SELECT * FROM " + DB_TABLE;

    private static final String SQL_DELETE_ONE = "DELETE FROM "+DB_TABLE+" WHERE ";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        memoAdapter=MemoAdapter.getInstance();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_IMAGE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //업그레이드할때 옛날 테이블을 지우고
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);

        //새로운 테이블 생성성
        onCreate(db);
    }

    public void setDb(SQLiteDatabase db) {
        this.db = db;
    }

    public int addEntry(String title, String main, String address, String day, byte[] image) throws SQLiteException {
        ContentValues cv = new ContentValues();
        cv.put(KEY_TITLE, title);
        cv.put(KEY_MAIN, main);
        cv.put(KEY_ADDRESS, address);
        cv.put(KEY_DAY, day);
        cv.put(KEY_IMAGE, image);
        //db테이블에 인덱스는 알아서 들어감
        db.insert(DB_TABLE, null, cv);

//        private static final String SQL_SELECT = "SELECT * FROM " + DB_TABLE;
        String SELECT_ID = "SELECT ID FROM "+ DB_TABLE;
        Cursor cursor=db.rawQuery(SELECT_ID, null);
        //id에는 현재 메모의 마지막 인덱스가 저장
        int id=0;
        if(cursor.moveToFirst()){
            do{
                id=cursor.getInt(cursor.getColumnIndex("ID"));
            } while (cursor.moveToNext());
        }
        Log.d("addEntry", id+"");

        return id;
    }

    public void updateDB(int id, String title, String main, String address, String day, byte[] image) throws SQLException {
        ContentValues cv = new ContentValues();
        cv.put(KEY_TITLE, title);
        cv.put(KEY_MAIN, main);
        cv.put(KEY_ADDRESS, address);
        cv.put(KEY_DAY, day);
        cv.put(KEY_IMAGE, image);

        Log.d("row id", id+"");

        db.update(DB_TABLE, cv, "id="+id, null);
    }

    public void startLoadData() {
        Cursor cursor = db.rawQuery(SQL_SELECT, null);
//        ArrayList<MemoData> memos = new ArrayList<>();

        //첫번째 행으로 커서가 이동
        if (cursor.moveToFirst()) {
            do {
                int id=cursor.getInt(cursor.getColumnIndex("ID"));
                String title = cursor.getString(cursor.getColumnIndex("TITLE"));
                String main = cursor.getString(cursor.getColumnIndex("MAIN"));
                String address = cursor.getString(cursor.getColumnIndex("ADDRESS"));
                String day = cursor.getString(cursor.getColumnIndex("DAY"));
                byte[] image = cursor.getBlob(cursor.getColumnIndex("IMAGE"));
                MemoData memoData = new MemoData(title, main, image, address, day);
                memoData.setId(id);
                memoAdapter.addItem(memoData);

                //다음커서로 간다
            } while (cursor.moveToNext());
        }
    }

    public void deleteEntry(int id){
        String DELTE_QUERY_WHERE= SQL_DELETE_ONE+"id="+id;

        db.execSQL(DELTE_QUERY_WHERE);
    }
}
