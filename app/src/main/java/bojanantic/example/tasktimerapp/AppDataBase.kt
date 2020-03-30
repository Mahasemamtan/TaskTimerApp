package bojanantic.example.tasktimerapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

/**
 * Created by Bojan Antić on 3/27/20
 *
 * Basic database class for the app.
 *
 * The only class that should use this is [AppProvider].
 */

private const val TAG = "AppDatBase"
private const val DATABASE_NAME = "TaskApp.db"
private const val DATABASE_VERSION = 3

internal class AppDataBase  private constructor(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    init {
        Log.d(TAG, "AppDataBase: initialising")
    }
    override fun onCreate(db: SQLiteDatabase) {
        // CREATE TABLE Tasks (_id INTEGER PRIMARY KEY NOT NULL, Name TEXT NOT NULL, Description TEXT, SortOrder INTEGER);
        Log.d(TAG, ".onCreate: starts")
        val sSQL = """CREATE TABLE ${TasksContract.TABLE_NAME} (
            ${TasksContract.Columns.ID} INTEGER PRIMARY KEY NOT NULL,
            ${TasksContract.Columns.TASK_NAME} TEXT NOT NULL,
            ${TasksContract.Columns.TASK_DESCRIPTION} TEXT,
            ${TasksContract.Columns.TASK_SORT_ORDER} INTEGER);
        """.replaceIndent(" ")

        Log.d(TAG, sSQL)
        db.execSQL(sSQL)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    companion object: SingletonHolder <AppDataBase, Context>(::AppDataBase)

//    companion object {
//
//        @Volatile
//        private var instance: AppDataBase? = null
//
//        fun getInstance(context: Context): AppDataBase =
//            instance ?: synchronized(this) {
//                instance ?: AppDataBase(context).also { instance = it }
//            }
//    }
}