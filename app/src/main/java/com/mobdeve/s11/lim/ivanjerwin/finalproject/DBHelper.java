package com.mobdeve.s11.lim.ivanjerwin.finalproject;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DB_NAME ="NAVINotes.db";
    private static final int DB_VER = 1;

    private static final String TABLE_NAME = "notes";
    private static final String COL_ID = "_id";
    private static final String COL_TITLE = "note_title";
    private static final String COL_CONTENT = "note_content";
    private static final String COL_IMG = "note_img";
    private static final String COL_IS_FAV = "note_fav";
    private static final String COL_IS_LOCKED = "note_lock";

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VER);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE " + TABLE_NAME + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COL_TITLE + " TEXT, " + COL_CONTENT + " TEXT, "+ COL_IMG + " BLOB, " +
                        COL_IS_FAV + " INTEGER , " + COL_IS_LOCKED + " INTEGER )";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

    void addNote(String title, String content, byte[] img, int fav, int lock){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COL_TITLE, title);
        cv.put(COL_CONTENT, content);
        cv.put(COL_IMG, img);
        cv.put(COL_IS_FAV, fav);
        cv.put(COL_IS_LOCKED, lock);

        long res = db.insert(TABLE_NAME, null, cv);
        if(res == -1){
            // fail
            Toast.makeText(context, "fail", Toast.LENGTH_SHORT).show();
        }
    }

    void updateNote(String _id, String title, String content, byte[] img, int fav, int lock){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COL_TITLE, title);
        cv.put(COL_CONTENT, content);
        cv.put(COL_IMG, img);
        cv.put(COL_IS_FAV, fav);
        cv.put(COL_IS_LOCKED, lock);

        long result = db.update(TABLE_NAME, cv, "_id = ?", new String[]{_id});

    }

    void delNote(String _id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id = ?", new String[]{_id});
    }

    Cursor readAll(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db!= null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NAME);
    }

    Cursor sortAscending(){

        String query = "SELECT * FROM " + TABLE_NAME; //+ " ORDER BY " + COL_ID;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db!= null){
            cursor = db.rawQuery(query, null);
        }

        else Toast.makeText(context, "SORT ASC FAIL", Toast.LENGTH_SHORT).show();

        return cursor;
    }

    Cursor sortDescending(){

        String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COL_ID +" DESC";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db!= null){
            cursor = db.rawQuery(query, null);
        }

        else Toast.makeText(context, "SORT DESC FAIL", Toast.LENGTH_SHORT).show();

        return cursor;

    }
}
