package com.panabey.smartnotebook.Database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.lang.StringBuilder
/**
 * Класс управления базой данных.
 */
class SQLiteHelperKotlin (context: Context): SQLiteOpenHelper (context, db_table_name, null, version) {

    companion object {
        const val version = 4
        const val db_table_name = "Notes"

        const val tableConcats = "contactsNotes"
        const val keyID = "ID"
        const val keyHeadNotes= "HeadNotes"
        const val keyBodyNotes= "BodyNotes"
        const val keyTask = "Task"
        const val keyTaskBoolean = "TaskBoolean"
        const val keyDatetime = "DateTime"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("create table " + tableConcats + "(" + keyID + " integer primary key,"
                + keyHeadNotes + " text not null,"
                + keyBodyNotes + " text,"
                + keyTask+ " text,"
                + keyTaskBoolean+ " text,"
                + keyDatetime + " text" + ")")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("drop table if exists $tableConcats")
        onCreate(db)
    }

    //удаление
    fun deleteNote(db: SQLiteDatabase, id: Int): Boolean {
        return db.delete(tableConcats, "$keyID=$id", null) > 0
    }

    //переиндексация при удалении
    fun ReindexNotes(db: SQLiteDatabase, id: Int) {
        Thread { db.execSQL("UPDATE contactsNotes SET ID = ID - 1 WHERE ID >$id") }.start()
    }

    //запись в базу данных
    fun UploadInDatabaseNotes(db: SQLiteDatabase, HeadText: String?, BodyText: String?, DateTime: String?, Task: StringBuilder, BooleanTask: StringBuilder) {
        val contentValues = ContentValues()
        contentValues.put(keyHeadNotes, HeadText)
        contentValues.put(keyBodyNotes, BodyText)
        contentValues.put(keyDatetime, DateTime)
        contentValues.put(keyTask, Task.toString())
        contentValues.put(keyTaskBoolean, BooleanTask.toString())
        db.insert(tableConcats, null, contentValues)
    }

    //Изменение заметки в базе данных после выхода из Activity
    fun UpdateNotes(db: SQLiteDatabase, HeadText: String, BodyText: String, DateTime: String, id: Int) {
        db.execSQL("UPDATE contactsNotes SET HeadNotes = '" + HeadText + "' , BodyNotes = '" + BodyText
                + "' , DateTime = '" + DateTime + "' WHERE id =" + id)
    }
}