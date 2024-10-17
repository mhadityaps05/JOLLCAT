package com.example.finalprojectmcs

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.finalprojectmcs.datalink.User

class DataBaseHelper(context: Context) : SQLiteOpenHelper(context,"JollyCat", null,
    1) {

    companion object {

        const val TABLE_USER = "User"
        const val COLUMN_USER_ID = "userId"
        const val COLUMN_USERNAME = "username"
        const val COLUMN_PASSWORD = "password"
        const val COLUMN_PHONE_NUMBER = "phoneNumber"


        private const val CREATE_TABLE_USER = "CREATE TABLE $TABLE_USER (" +
                "$COLUMN_USER_ID TEXT PRIMARY KEY, " +
                "$COLUMN_USERNAME TEXT UNIQUE, " +
                "$COLUMN_PASSWORD TEXT, " +
                "$COLUMN_PHONE_NUMBER TEXT)"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE_USER)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_USER")
        onCreate(db)
    }


    fun addUser(user: User): Long {
        val db = writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_USER_ID, user.userId)
            put(COLUMN_USERNAME, user.username)
            put(COLUMN_PASSWORD, user.password)
            put(COLUMN_PHONE_NUMBER, user.phoneNumber)
        }
        return db.insert(TABLE_USER, null, contentValues)
    }

    fun getUserByUsername(username: String): User? {
        val db = readableDatabase
        val cursor: Cursor = db.query(
            TABLE_USER,
            null,
            "$COLUMN_USERNAME = ?",
            arrayOf(username),
            null,
            null,
            null
        )
        return if (cursor.moveToFirst()) {
            val user = User(
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE_NUMBER))
            )
            cursor.close()
            user
        } else {
            cursor.close()
            null
        }
    }


    fun validateUser(username: String, password: String): Boolean {
        val user = getUserByUsername(username)
        return user?.password == password
    }

    fun getUserById(userId: String): User? {
        val db = readableDatabase
        val cursor: Cursor = db.query(
            TABLE_USER,
            null,
            "$COLUMN_USER_ID = ?",
            arrayOf(userId),
            null,
            null,
            null
        )
        return if (cursor.moveToFirst()) {
            val user = User(
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE_NUMBER))
            )
            cursor.close()
            user
        } else {
            cursor.close()
            null
        }
    }
}


