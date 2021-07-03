package com.panabey.smartnotebook.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {

    public static final int VERSION = 1;
    public static final String BD_TABLE_NAME = "Notes";
    public static final String TABLE_CONTACTS = "contactsNotes";
    public static  final String KEY_ID = "ID";
    public static final String KEY_HEAD_NOTES = "HeadNotes";
    public static final String KEY_BODY_NOTES = "BodyNotes";
    public static final String KEY_DATETIME = "DateTime";

    public SQLiteHelper(Context context)
    {
        super(context, BD_TABLE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TABLE_CONTACTS + "(" + KEY_ID + " integer primary key," + KEY_HEAD_NOTES + " text not null," + KEY_BODY_NOTES + " text," + KEY_DATETIME + " text" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_CONTACTS);
        onCreate(db);
    }

    public boolean deleteNote(SQLiteDatabase db, int id)
    {
        return db.delete(TABLE_CONTACTS, KEY_ID + "=" + id, null) > 0;
    }

    /*
    public boolean updateNotes(SQLiteDatabase db, int toPos, int fromPos)
    {
        return  db.update(TABLE_CONTACTS, )
    }
     */

    public void SearchItem(SQLiteDatabase db, String Item)
    {
        db.execSQL("select * from " + TABLE_CONTACTS + "like '%" + Item + "%'");
    }
}
