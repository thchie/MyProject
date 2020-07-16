package com.my.newproject2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TAG = "DatabaseHelper";

    public static final String DATABASE_NAME = "MySuaraDB";

    public static final String TABLE_NAME_TEACHER = "teacher_table";
    public static final String teacher_COL1 = "id";
    public static final String teacher_COL2 = "name";
    public static final String teacher_COL3 = "email";
    public static final String teacher_COL4 = "password";
    public static final String teacher_COL5 = "student1";
    public static final String teacher_COL6 = "student2";
    public static final String teacher_COL7 = "student3";
    public static final String teacher_COL8 = "image_file_path";

    public static final String TABLE_NAME_STU = "student_table";
    public static final String stu_COL1 = "id";
    public static final String stu_COL3 = "name";
    public static final String stu_COL4 = "age";
    public static final String stu_COL5 = "school";
    public static final String stu_COL6 = "level";
    public static final String stu_COL7 = "language";
    public static final String stu_COL8 = "image_file_path";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTableTeacher = "CREATE TABLE " + TABLE_NAME_TEACHER + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                teacher_COL2 + " TEXT," +
                teacher_COL3 + " TEXT," +
                teacher_COL4 + " TEXT," +
                teacher_COL5 + " TEXT," +
                teacher_COL6 + " TEXT," +
                teacher_COL7 + " TEXT," +
                teacher_COL8 + " TEXT)";
        db.execSQL(createTableTeacher);

        String createTableStu = "CREATE TABLE " + TABLE_NAME_STU + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                stu_COL3 + " TEXT," +
                stu_COL4 + " TEXT," +
                stu_COL5 + " TEXT," +
                stu_COL6 + " TEXT," +
                stu_COL7 + " TEXT," +
                stu_COL8 + " TEXT)";
        db.execSQL(createTableStu);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_TEACHER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_STU);
        onCreate(db);
    }

    public boolean addDataTeacher(String name, String email, String password, String image_file_path){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(teacher_COL2, name);
        contentValues.put(teacher_COL3, email);
        contentValues.put(teacher_COL4, password);
        contentValues.put(teacher_COL8, image_file_path);

        Log.d(TAG, "addData: Adding " + name + email + password + " to " + TABLE_NAME_TEACHER);
        long result = db.insert(TABLE_NAME_TEACHER, null, contentValues);

        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean addDataStudent(String okuNo, String name, String age, String school, String image_file_path){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(stu_COL3, name);
        contentValues.put(stu_COL4, age);
        contentValues.put(stu_COL5, school);
        contentValues.put(stu_COL6, "1");
        contentValues.put(stu_COL7, "english");
        contentValues.put(stu_COL8, image_file_path);

        Log.d(TAG, "addData: Adding " + name + age + school + image_file_path + " to " + TABLE_NAME_STU);
        long result = db.insert(TABLE_NAME_STU, null, contentValues);

        if (result == -1)
            return false;
        else
            return true;
    }

    public int updateDataTeacher(String id, String student, String stu_id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(student, stu_id);

        return db.update(TABLE_NAME_TEACHER, contentValues, teacher_COL1+"="+id, null);
    }

    public Cursor getDataTeacher(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME_TEACHER;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getDataStudent(String id){

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME_STU + " WHERE id='" + id + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public int getStudentCount(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME_STU;
        Cursor data = db.rawQuery(query, null);
        return data.getCount();
    }

    public int updateDataStudent(String id, String level, String lang){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(stu_COL6, level);
        contentValues.put(stu_COL7, lang);

        return db.update(TABLE_NAME_STU, contentValues, stu_COL1+"="+id, null);
    }

    public Cursor login(String email, String password){

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME_TEACHER + " WHERE email='" + email + "' and password='" + password + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }
}
