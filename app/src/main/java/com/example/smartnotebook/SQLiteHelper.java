package com.example.smartnotebook;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String BD_TABLE_NAME = "Notes";
    private static final String TABLE_CONTACTS = "contactsNotes";
    private static  final String KEY_ID = "_id";
    private static final String COL_HEAD_NOTES = "HeadNotes";
    private static final String COL_BODY_NOTES = "BodyNotes";

    public SQLiteHelper(Context context)
    {
        super(context, BD_TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       try{
           //db.execSQL(ВНУТРИ ЗАПРОС!);
       }
       catch (Exception e)
       {

       }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_CONTACTS);
        onCreate(db);
    }
}
