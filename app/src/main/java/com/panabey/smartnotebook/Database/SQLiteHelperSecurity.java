package com.panabey.smartnotebook.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelperSecurity extends SQLiteOpenHelper {

    public static final int VERSION_TABLE = 1;
    public static final String BD_TABLE_NAME_GENERAL = "General";
    public static final String TABLE_CONTACTS_GENERAL = "Account";
    public static  final String KEY_ID = "ID";
    public static final String KEY_HEAD_NOTES = "Name";

    public SQLiteHelperSecurity(Context context)
    {
        super(context, BD_TABLE_NAME_GENERAL, null, VERSION_TABLE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("drop table if exists " + TABLE_CONTACTS_GENERAL);
        onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
