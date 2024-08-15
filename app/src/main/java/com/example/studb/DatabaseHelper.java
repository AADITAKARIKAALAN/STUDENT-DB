package com.example.studb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Student.db";
    private static final String TABLE_NAME = "student_table";
    private static final String COL_1 = "ROLL_NO";
    private static final String COL_2 = "NAME";
    private static final String COL_3 = "MARKS";
    private static final String COL_4 = "ADDRESS";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 2); // Updated version for upgrade
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                COL_1 + " INTEGER PRIMARY KEY, " +
                COL_2 + " TEXT, " +
                COL_3 + " INTEGER, " +
                COL_4 + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + COL_4 + " TEXT");
        }
    }

    public boolean insertStudent(int rollNo, String name, int marks, String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, rollNo);
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, marks);
        contentValues.put(COL_4, address);

        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public Cursor getAllStudents() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    public boolean updateStudent(int rollNo, String name, int marks, String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, marks);
        contentValues.put(COL_4, address);

        int result = db.update(TABLE_NAME, contentValues, COL_1 + " = ?", new String[]{String.valueOf(rollNo)});
        return result > 0;
    }

    public int deleteStudent(int rollNo) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, COL_1 + " = ?", new String[]{String.valueOf(rollNo)});
    }

    public Cursor getStudent(int rollNo) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_1 + " = ?", new String[]{String.valueOf(rollNo)});
    }
}
