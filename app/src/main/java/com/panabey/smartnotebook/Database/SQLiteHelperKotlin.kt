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
        const val version = 5
        const val db_table_name = "contactsNotes"
    }
    /**
     * Таблица Заметок,
     * используется для отображения на главном Activity и хранения данных.
     */
    private val tableNotes = "Notes"
    private val keyIDNotes = "IDNotes"
    private val keyHeadNotes= "HeadNotes"
    private val keyBodyNotes= "BodyNotes"
    private val keyDateTimeNotes = "DateTime"

    /**
     * Таблица подзадач,
     * Используется для хранения подзадач заметок
     */
    private val tableTask = "Tasks"
    private val keyIDTask = "IDNotes"
    private val keyNameTask = "Task"
    private val keyTaskBoolean = "TaskBoolean"

    /**
     * Триггер автодекремента.
     * Срабатывает при удалении заметки. Используется для сохранения индексов.
     */
    private val triggerDecrementID = "DecID"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE $tableNotes (" +
            "$keyIDNotes    INTEGER, " +
            "$keyHeadNotes	TEXT NOT NULL, " +
            "$keyBodyNotes	TEXT, " +
            "$keyDateTimeNotes	TEXT, " +
            "PRIMARY KEY($keyIDNotes));"
        );
        db.execSQL("CREATE TABLE $tableTask (" +
            "$keyIDTask	INTEGER NOT NULL, " +
            "$keyNameTask	TEXT, " +
            "$keyTaskBoolean	INTEGER, " +
            "FOREIGN KEY($keyIDTask) REFERENCES $tableNotes($keyIDNotes) " +
                "ON DELETE CASCADE " +
                "ON UPDATE CASCADE);"
        );
        db.execSQL("CREATE TRIGGER $triggerDecrementID AFTER DELETE \n" +
                "\tON $tableNotes\n" +
                "    BEGIN \n" +
                "    UPDATE $tableNotes SET $keyIDNotes = $keyIDNotes - 1 WHERE $keyIDNotes > old.$keyIDNotes;\n" +
                "    END;")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("drop table if exists $tableNotes")
        db.execSQL("drop table if exists $tableTask")
        db.execSQL("drop trigger if exists $triggerDecrementID")
        onCreate(db)
    }

    /**
     * Удаление из базы данных.
     * Удаление происходит по id записи.
     */
    fun deleteNote(db: SQLiteDatabase, id: Int): Boolean {
        return db.delete(tableNotes, "$keyIDNotes=$id", null) > 0
    }

    //запись в базу данных
    fun UploadInDatabaseNotes(db: SQLiteDatabase, HeadText: String?, BodyText: String?, DateTime: String?) {
        val contentValues = ContentValues()

        contentValues.put(keyHeadNotes, HeadText)
        contentValues.put(keyBodyNotes, BodyText)
        contentValues.put(keyDateTimeNotes, DateTime)
        db.insert(tableNotes, null, contentValues)
    }

    //Изменение заметки в базе данных после выхода из Activity
    fun UpdateNotes(db: SQLiteDatabase, HeadText: String, BodyText: String, DateTime: String, id: Int) {
        /*
        db.execSQL("UPDATE $tableNotes SET HeadNotes = '" + HeadText + "' , BodyNotes = '" + BodyText
                + "' , DateTime = '" + DateTime + "' WHERE IDNotes =" + id)

         */
        val contentValuesUpdate = ContentValues();
        contentValuesUpdate.put(keyHeadNotes, HeadText)
        contentValuesUpdate.put(keyBodyNotes, BodyText)
        contentValuesUpdate.put(keyDateTimeNotes, DateTime)
        db.update(tableNotes, contentValuesUpdate,"$keyIDNotes = $id", null )

    }
}