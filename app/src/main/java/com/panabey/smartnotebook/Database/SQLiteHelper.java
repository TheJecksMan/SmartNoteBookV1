package com.panabey.smartnotebook.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {

    //Database Notes
    public static final int VERSION = 1;
    public static final String BD_TABLE_NAME = "Notes";
    public static final String TABLE_CONTACTS = "contactsNotes";
    public static final String KEY_ID = "ID";
    public static final String KEY_HEAD_NOTES = "HeadNotes";
    public static final String KEY_BODY_NOTES = "BodyNotes";
    public static final String KEY_DATETIME = "DateTime";

    public SQLiteHelper(Context context)
    {
        super(context, BD_TABLE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TABLE_CONTACTS + "(" + KEY_ID + " integer primary key,"
                + KEY_HEAD_NOTES + " text not null,"
                + KEY_BODY_NOTES + " text,"
                + KEY_DATETIME + " text" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_CONTACTS);
        onCreate(db);
    }

    //удаление
    public boolean deleteNote(SQLiteDatabase db, int id)
    {
        return db.delete(TABLE_CONTACTS, KEY_ID + "=" + id, null) > 0;
    }

    //переиндексация при удалении
    public void ReindexNotes(SQLiteDatabase db, int id){
        new Thread(new Runnable() {
            public void run() {

                db.execSQL("UPDATE contactsNotes SET ID = ID - 1 WHERE ID >" + id);
            }
        }).start();
    }

    //запись в базу данных
    public void UploadInDatabaseNotes(SQLiteDatabase db, String HeadText, String BodyText, String DateTime){

                ContentValues contentValues = new ContentValues();

                contentValues.put(SQLiteHelper.KEY_HEAD_NOTES, HeadText);
                contentValues.put(SQLiteHelper.KEY_BODY_NOTES, BodyText);
                contentValues.put(SQLiteHelper.KEY_DATETIME, DateTime);

                db.insert(TABLE_CONTACTS, null, contentValues);
    }

    //Изменение заметки в базе данных
    public void UpdateNotes(SQLiteDatabase db,String HeadText, String BodyText, String DateTime, int id ){
        db.execSQL("UPDATE contactsNotes SET HeadNotes = '" + HeadText + "' , BodyNotes = '"+ BodyText
                +"' , DateTime = '" + DateTime + "' WHERE id =" + id);
    }
}
