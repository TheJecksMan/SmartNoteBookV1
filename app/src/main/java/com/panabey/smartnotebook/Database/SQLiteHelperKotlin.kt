package com.panabey.smartnotebook.Database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.content.contentValuesOf
import java.util.*

/**
 * Класс управления базой данных.
 */
class SQLiteHelperKotlin (context: Context): SQLiteOpenHelper (context, db_table_name, null, version) {

    companion object {
        const val version = 7
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
     * Таблица Тегов Заметок
     */
    private val tableTag = "Tag"
    private val keyIDNotesTag = "IDNotes"
    private val keyNameTag = "NameTag"
    private val keyColorTag = "ColorTag"

    /**
     *  Таблица хранения данных пользователя
     */
    private val tableAccount = "Account"
    private val keyIDAccount = "ID"
    private val username = "Username"
    private val password = "Password"
    private val firstname = "Firstname"
    private val lastname = "Lastname"
    private val joinedDate = "Joined"

    /**
     * Триггер автодекремента.
     * Срабатывает при удалении заметки. Используется для сохранения индексов.
     */
    private val triggerDecrementID = "DecID"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("PRAGMA foreign_keys=ON")

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

        db.execSQL("CREATE TABLE $tableTag (" +
                "$keyIDNotesTag INTEGER NOT NULL, " +
                "$keyNameTag TEXT, " +
                "$keyColorTag TEXT, " +
                "FOREIGN KEY($keyIDNotesTag) REFERENCES $tableNotes($keyIDNotes) " +
                "ON DELETE CASCADE " +
                "ON UPDATE CASCADE);")

        db.execSQL("CREATE TABLE $tableAccount ("+
            "$keyIDAccount INTEGER NOT NULL, "+
            "$username TEXT, "+
            "$password TEXT, "+
            "$firstname TEXT, "+
            "$lastname TEXT, "+
            "$joinedDate TEXT);")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("drop table if exists $tableNotes")
        db.execSQL("drop table if exists $tableTask")
        db.execSQL("drop trigger if exists $triggerDecrementID")
        db.execSQL("drop table if exists $tableTag")
        db.execSQL("drop table if exists $tableAccount")
        onCreate(db)
    }

    /**
     * Срабатывает каждый раз при открытии базы данных.
     * Требуется для каскадного удаления заметки и её подзадач.
     */
    override fun onOpen(db: SQLiteDatabase?) {
        super.onOpen(db)
        db?.execSQL("PRAGMA foreign_keys=ON")
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
     * Запись тегов в базу данных
     */
    fun insertTagInDatabase(db: SQLiteDatabase, IDNotes: Int, NameTag: String ){
        val contentValuesInsertTask = ContentValues()
        contentValuesInsertTask.put(keyIDTask, IDNotes)
        contentValuesInsertTask.put(keyNameTag, NameTag)
        db.insert(tableTag,null, contentValuesInsertTask)
    }

    /**
     * Удаление заметок.
     * Используется как для перезаписи, так и для полного удаления из базы данных.
     */
    fun deleteTaskInDatabase(db: SQLiteDatabase, IDNotes: Int){
        db.delete(tableTask, "$keyIDTask = $IDNotes",null)
    }

    /**
     * Удаление Тегов.
     * Используется как для перезаписи, так и для полного удаления из базы данных.
     */
    fun deleteTafInDatabase(db: SQLiteDatabase, IDNotes: Int){
        db.delete(tableTag, "$keyIDTask = $IDNotes",null)
    }

    /**
     * Получение нового ID для записи Подзадач
     */
    fun getItemID(db: SQLiteDatabase): Int{
        val cursor: Cursor = db.rawQuery("SELECT count($keyIDNotes)+1 from $tableNotes", null)
        cursor.moveToFirst()
        val itemID: Int = cursor.getInt(0)
        cursor.close()
        return  itemID
    }


    /**
     * Добавление пользователя в базу данных
     */
    fun appendUser(db: SQLiteDatabase, username_user: String, password_user: String, firstname_user
    : String, lastname_user: String, joined: String){
        val contentValuesUser = ContentValues()
        contentValuesUser.put(keyIDAccount, 1)
        contentValuesUser.put(username, username_user)
        contentValuesUser.put(password, password_user)
        contentValuesUser.put(firstname, firstname_user)
        contentValuesUser.put(lastname, lastname_user)
        contentValuesUser.put(joinedDate, joined)

        db.insert(tableAccount, null, contentValuesUser)
    }

    /**
     * Получение пероначальных данных пользователя
     */
    fun getInfoAboutUser(db: SQLiteDatabase): MutableMap<String, String> {
        val userObject = mutableMapOf<String, String>()
        val cursor: Cursor = db.rawQuery("SELECT *  FROM $tableAccount where $keyIDAccount = 1;", null)
        cursor.moveToFirst()
        userObject["username"] = cursor.getString(cursor.getColumnIndex((username)))
        userObject["password"] = cursor.getString(cursor.getColumnIndex(password))
        userObject["firstname"] = cursor.getString(cursor.getColumnIndex(firstname))
        userObject["lastname"] = cursor.getString(cursor.getColumnIndex(lastname))
        userObject["joined"] = cursor.getString(cursor.getColumnIndex((joinedDate)))
        return userObject
    }
}