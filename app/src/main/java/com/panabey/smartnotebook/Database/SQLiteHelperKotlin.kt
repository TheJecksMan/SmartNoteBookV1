package com.panabey.smartnotebook.Database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.browser.browseractions.BrowserActionsIntent

/**
 * Класс управления базой данных.
 */
class SQLiteHelperKotlin (context: Context): SQLiteOpenHelper (context, db_table_name, null, version) {

    companion object {
        const val version = 6
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
            "PRIMARY KEY($keyIDNotes));")

        db.execSQL("CREATE TABLE $tableTask (" +
            "$keyIDTask	INTEGER NOT NULL, " +
            "$keyNameTask	TEXT, " +
            "$keyTaskBoolean	INTEGER, " +
            "FOREIGN KEY($keyIDTask) REFERENCES $tableNotes($keyIDNotes) " +
                "ON DELETE CASCADE " +
                "ON UPDATE CASCADE);")

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

    /**
     * Запись в базу даннных в классе CreateNote.
     * Используется при возврате на главный экран.
     */
    fun insertNotesInDatabase(db: SQLiteDatabase, HeadText: String?, BodyText: String?, DateTime: String?) {
        val contentValuesInsertNotes = ContentValues()

        contentValuesInsertNotes.put(keyHeadNotes, HeadText)
        contentValuesInsertNotes.put(keyBodyNotes, BodyText)
        contentValuesInsertNotes.put(keyDateTimeNotes, DateTime)
        db.insert(tableNotes, null, contentValuesInsertNotes)
    }

    /**
     * Перезапись заметки, елси та была уже создана.
     * Используется при возврате на главный экран.
     */
    fun updateNotes(db: SQLiteDatabase, HeadText: String, BodyText: String, DateTime: String, IDNotes: Int) {
        val contentValuesUpdateNotes = ContentValues()

        contentValuesUpdateNotes.put(keyHeadNotes, HeadText)
        contentValuesUpdateNotes.put(keyBodyNotes, BodyText)
        contentValuesUpdateNotes.put(keyDateTimeNotes, DateTime)
        db.update(tableNotes, contentValuesUpdateNotes,"$keyIDNotes = $IDNotes", null )
    }

    /**
     * Запись подзадач в базу данных.
     */
    fun insertTaskInDatabase(db: SQLiteDatabase, IDNotes: Int, NameTask: String, TaskBoolean: Int ){
        val contentValuesInsertTask = ContentValues()
        contentValuesInsertTask.put(keyIDTask, IDNotes)
        contentValuesInsertTask.put(keyNameTask, NameTask)
        contentValuesInsertTask.put(keyTaskBoolean, TaskBoolean)
        db.insert(tableTask,null, contentValuesInsertTask)
    }

    /**
     * Удаление заметок.
     * Используется как для перезаписи, так и для полного удаления из базы данных.
     */
    fun deleteTaskInDatabase(db: SQLiteDatabase, IDNotes: Int){
        db.delete(tableTask, "$keyIDTask = $IDNotes",null)
    }

    /**
     * Получение нового ID для записи Подзадач
     */
    fun getItemID(db: SQLiteDatabase): Int{
        val cursor: Cursor = db.rawQuery("SELECT count($keyIDNotes)+1 from $tableNotes", null)
        cursor.moveToFirst()
        return cursor.getInt(0)
    }
}