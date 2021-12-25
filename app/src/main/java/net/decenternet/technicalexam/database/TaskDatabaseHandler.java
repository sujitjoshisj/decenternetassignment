package net.decenternet.technicalexam.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import net.decenternet.technicalexam.domain.Task;

import java.util.ArrayList;

public class TaskDatabaseHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "taskData";

    // Task table name
    private static final String TABLE_TASK = "Task";

    // Task Table Columns names
    private static final String KEY_ID = "id";
    private static final String TASK_DESCRIPTION = "TaskDescription";
    private static final String TASK_STATUS = "TaskStatus";

    public TaskDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TASK_TABLE = "CREATE TABLE " + TABLE_TASK + "(" + KEY_ID + " INTEGER PRIMARY KEY," + TASK_DESCRIPTION + " TEXT," + TASK_STATUS + " TEXT" + ")";
        db.execSQL(CREATE_TASK_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new Task
    public void addTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TASK_DESCRIPTION, task.getDescription());
        values.put(TASK_STATUS, task.getIsCompleted());
        db.insert(TABLE_TASK, null, values);
        db.close();
    }

    // Getting single task
    public Task getTask(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_TASK, new String[]{KEY_ID,
                        TASK_DESCRIPTION, TASK_STATUS}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Task task = new Task();
        task.setId(Integer.parseInt(cursor.getString(0)));
        task.setDescription(cursor.getString(1));
        return task;
    }

    // Getting All Tasks
    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> taskList = new ArrayList();
        String selectQuery = "SELECT  * FROM " + TABLE_TASK;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setId(Integer.parseInt(cursor.getString(0)));
                Log.i("id", ""+task.getId());
                task.setDescription(cursor.getString(1));
                task.setIsCompleted(cursor.getString(2));
                taskList.add(task);
            } while (cursor.moveToNext());
        }
        return taskList;
    }

    // Updating single task
    public int updateTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TASK_DESCRIPTION, task.getDescription());
        values.put(TASK_STATUS, task.getIsCompleted());

        return db.update(TABLE_TASK, values, KEY_ID + " = ?",
                new String[]{String.valueOf(task.getId())});
    }

    // Deleting single task
    public void deleteTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASK, KEY_ID + " = ?",
                new String[]{String.valueOf(task.getId())});
        db.close();
    }

    // Deleting all tasks
    public void deleteAllTasks() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASK, null, null);
        db.close();
    }

    // Getting tasks Count
    public int getTasksCount() {
        int count = 0;
        String countQuery = "SELECT  * FROM " + TABLE_TASK;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null && !cursor.isClosed()) {
            count = cursor.getCount();
            cursor.close();
        }
        return count;
    }
}
