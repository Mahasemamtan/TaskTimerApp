package bojanantic.example.tasktimerapp

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri
import android.util.Log

/**
 * Created by Bojan Antić on 3/30/20
 *
 * Provider for the TaskTimer app. This is the only class that knows about [AppDataBase].
 */

private const val TAG = "AppProvider"
const val CONTENT_AUTHORITY = "bojanantic.example.tasktimerapp.provider"

private const val TASKS = 100
private const val TASKS_ID = 101

private const val TIMINGS = 200
private const val TIMINGS_ID = 201

private const val TASK_DURATION = 400
private const val TASK_DURATION_ID = 401

val CONTENT_AUTHORITY_URI: Uri = Uri.parse("content://$CONTENT_AUTHORITY")

class AppProvider : ContentProvider() {

    private val uriMatcher by lazy { buildUriMatcher() }

    private fun buildUriMatcher(): UriMatcher {
        Log.d(TAG, ".buildUriMatcher: starts")
        val matcher = UriMatcher(UriMatcher.NO_MATCH)

        // e.g. content://bojanantic.example.tasktimerapp.provider/Tasks
        matcher.addURI(CONTENT_AUTHORITY, TasksContract.TABLE_NAME, TASKS)

        // e.g. content://bojanantic.example.tasktimerapp.provider/Tasks/8
        matcher.addURI(CONTENT_AUTHORITY, "${TasksContract.TABLE_NAME}/#", TASKS_ID)

//        matcher.addURI(CONTENT_AUTHORITY, TimingsContract.TABLE_NAME, TIMINGS)
//        matcher.addURI(CONTENT_AUTHORITY, "${TimingsContract.TABLE_NAME}/#", TIMINGS_ID)
//
//        matcher.addURI(CONTENT_AUTHORITY, DurationsContract.TABLE_NAME, TASK_DURATION)
//        matcher.addURI(CONTENT_AUTHORITY, "${DurationsContract.TABLE_NAME}/#", TASK_DURATION_ID)

        return matcher
    }

    override fun onCreate(): Boolean {
        Log.d(TAG, ".onCreate: starts")
        return true
    }

    override fun getType(p0: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        Log.d(TAG, ".query: called with uri $uri")
        val match = uriMatcher.match(uri)
        Log.d(TAG, ".query: matches $match")

        val queryBuilder = SQLiteQueryBuilder()

        when (match) {
            TASKS -> {
                queryBuilder.tables = TasksContract.TABLE_NAME
            }

            TASKS_ID -> {
                queryBuilder.tables = TasksContract.TABLE_NAME
                val taskId = TasksContract.getId(uri)
                queryBuilder.appendWhere("${TasksContract.Columns.ID} = ")
                queryBuilder.appendWhereEscapeString("$taskId")

            }

//            TIMINGS -> {
//                queryBuilder.tables = TimingsContract.TABLE_NAME
//            }
//
//            TIMINGS_ID -> {
//                queryBuilder.tables = TimingsContract.TABLE_NAME
//                val timingId = TimingsContract.getId(uri)
//                queryBuilder.appendWhere("${TimingsContract.Columns.ID} = ")
//                queryBuilder.appendWhereEscapeString("$timingId")

//            }
//
//            TASK_DURATION -> {
//                queryBuilder.tables = DurationsContract.TABLE_NAME
//            }
//
//            TASK_DURATION_ID -> {
//                queryBuilder.tables = DurationsContract.TABLE_NAME
//                val durationId = DurationsContract.getId(uri)
//                queryBuilder.appendWhere("${DurationsContract.Columns.ID} = ")
//                queryBuilder.appendWhereEscapeString("$durationId")

//            }
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }

        val db = AppDataBase.getInstance(context!!).readableDatabase
        val cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder)
        Log.d(TAG, ".query rows in returned cursor: ${cursor.count}") //TODO: Remove this line

        return cursor
    }

    override fun insert(p0: Uri, p1: ContentValues?): Uri? {
        TODO("Not yet implemented")
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        TODO("Not yet implemented")
    }
}