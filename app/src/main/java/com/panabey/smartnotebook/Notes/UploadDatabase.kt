package com.panabey.smartnotebook.Notes

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.widget.TextView
import com.panabey.smartnotebook.Database.SQLiteHelperKotlin
import java.text.SimpleDateFormat
import java.util.*

class UploadDatabase(HeadEditText: TextView, BodyEditText: TextView, context: Context) {

    private val headEditText: TextView = HeadEditText
    private val bodyEditText: TextView = BodyEditText

    private val sqLiteHelperKotlin: SQLiteHelperKotlin = SQLiteHelperKotlin(context);
    private val database: SQLiteDatabase = sqLiteHelperKotlin.writableDatabase

    var clickNoteBoolean: Boolean = false
        set(value) { field = value }

    var itemID: Int = 0
        set(value) { field = value }

    fun writeInDatabase(){

        val checkedNullText: String = headEditText.getText().toString()
        if (checkedNullText.trim().isEmpty()) {
            return
        }

        if(!clickNoteBoolean){

            sqLiteHelperKotlin.insertNotesInDatabase(database,headEditText.text.toString(),bodyEditText.text.toString(),getDateTime())
        }
        else{
            sqLiteHelperKotlin.updateNotes(database,headEditText.text.toString(),bodyEditText.text.toString(), getDateTime(),itemID)
        }
    }
    private fun getDateTime(): String {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm:ss")
        return dateFormat.format(Date())
    }
}