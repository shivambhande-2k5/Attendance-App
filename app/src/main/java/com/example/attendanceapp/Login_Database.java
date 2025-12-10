package com.example.attendanceapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Login_Database extends SQLiteOpenHelper {

    public static final String DBName = "Login.db";         // database name

    public Login_Database(Context context) {
        super(context.getApplicationContext(), "Login.db", null,1);

    }
    @Override
    public void onCreate(SQLiteDatabase MyDB) {
    MyDB.execSQL("CREATE TABLE users (username TEXT PRIMARY KEY, password TEXT )");
    }
    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int oldVersion, int newVersion) {
    MyDB.execSQL("DROP TABLE IF EXISTS users");
    onCreate(MyDB);
    }

    public Boolean insertData (String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("username" , username);
        contentValues.put("password" , password);
        long result = MyDB.insert("users", null,contentValues);

        if (result == -1) return false ;
        else
            return true ;
    }

    public Boolean checkusername (String username){
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * FROM users WHERE username = ?", new String[] {username} );

        if (cursor.getCount() > 0)
        return true ;
        else
            return false;
    }

    public Boolean checkusernamepassword (String username , String password){
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * FROM users WHERE username = ? AND password = ? ", new String[] {username , password});

        if (cursor.getCount() > 0)
            return true ;
        else
            return false;
    }
}
