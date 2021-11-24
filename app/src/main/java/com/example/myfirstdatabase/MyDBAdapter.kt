package com.example.myfirstdatabase

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDBAdapter(context: Context) {
    private val DATABASE_NAME: String = "name"
    private var mContext: Context? = null
    private var mDbHelper: MyDBHelper? = null
    private var mSQLiteDatabase : SQLiteDatabase? = null
    private val DATABASE_VERSION = 1


    init {
        this.mContext = context
        mDbHelper = MyDBHelper(context, DATABASE_NAME, null, DATABASE_VERSION)
    }


    public fun open() {
        mSQLiteDatabase = mDbHelper?.writableDatabase
    }

    inner class MyDBHelper(context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(
        context, name, factory, version) {

        override fun onCreate(db: SQLiteDatabase?) {
            val query = "CREATE TABLE students(id integer primary key autoincrement, name text, faculty integer);"
            db?.execSQL(query)
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            val query = "DROP TABLE IF EXISTS students;"
            db?.execSQL(query)
        }
    }

    public fun insertStudent(name: String?, faculty: Int) {
        val cv: ContentValues = ContentValues()
        cv.put("name", name)
        cv.put("faculty",faculty)
        mSQLiteDatabase?.insert("students", null, cv)
    }

    public fun selectAllStudents(): List<String> {
        var allStudents: MutableList<String> = ArrayList()
        var cursor :Cursor = mSQLiteDatabase?.query("students", null,null,null,null,null,null)!!
        if (cursor.moveToFirst()) {
            do {
                allStudents.add(cursor.getString(1))
            } while (cursor.moveToNext())
        }
        return allStudents
    }

    public fun deleteAllEngineers() {
        mSQLiteDatabase?.delete("students",null,null)
    }
}

