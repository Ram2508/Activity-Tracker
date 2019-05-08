package com.example.testproject;

        import android.content.ContentValues;
        import android.content.Context;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{
    private static DatabaseHelper myDb = null;

    private static final String DATABASE_NAME = "activity_db.db";
    private static final String TABLE_NAME = "activity_data";
    private static final String NOTE_NAME = "ACTIVITYNAME";
    private static final String NOTE_TIME = "TIME";

    private DatabaseHelper( Context context) {
        super(context, DATABASE_NAME, null, 1);
    }
    static DatabaseHelper getDatabaseHelper( Context context)
    {
        if(myDb == null)
        {
            myDb =  new DatabaseHelper(context);
        }
            return  myDb;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, ACTIVITYNAME TEXT, TIME TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
     void insertData(String activityName, String time)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues  = new ContentValues();
        contentValues.put(NOTE_NAME, activityName);
        contentValues.put(NOTE_TIME, time);
        db.insert(TABLE_NAME, null, contentValues);
    }
}