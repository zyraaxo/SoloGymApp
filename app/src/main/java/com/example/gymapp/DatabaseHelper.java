package com.example.gymapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "myDatabase.db";
    public static final String TABLE_NAME = "myTable";
    public static final String WORKOUT_TABLE_NAME = "workout";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_USER_ID = "user_id"; // Added column for user reference
    public static final String COLUMN_WORKOUT_NAME = "workout_name";
    public static final String COLUMN_SETS = "sets";
    public static final String COLUMN_REPS = "reps";
    public static final String COLUMN_DAY = "day"; // New column for day

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_PASSWORD + " TEXT"
                + ")";
        db.execSQL(CREATE_USER_TABLE);

        String CREATE_WORKOUT_TABLE = "CREATE TABLE " + WORKOUT_TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_USER_ID + " INTEGER, "
                + COLUMN_WORKOUT_NAME + " TEXT, "
                + COLUMN_SETS + " TEXT, "
                + COLUMN_REPS + " TEXT, "
                + COLUMN_DAY + " INTEGER, " // Add this line for the day column
                + "FOREIGN KEY(" + COLUMN_USER_ID + ") REFERENCES " + TABLE_NAME + "(" + COLUMN_ID + "))";
        db.execSQL(CREATE_WORKOUT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 3) {
            db.execSQL("ALTER TABLE " + WORKOUT_TABLE_NAME + " ADD COLUMN " + COLUMN_DAY + " INTEGER");
        }
    }

    // Method to check if a user exists
    public int checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_ID + " FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME + "=? AND " + COLUMN_PASSWORD + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});

        if (cursor.moveToFirst()) {
            int userId = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            cursor.close();
            return userId; // Return the user ID
        }

        cursor.close();
        return -1; // Return -1 if no user ID is found
    }


    // Insert workout for a specific user
    public void insertWorkout(int userId, String workoutName, String sets, String reps, int day) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USER_ID, userId);
        contentValues.put(COLUMN_WORKOUT_NAME, workoutName);
        contentValues.put(COLUMN_SETS, sets);
        contentValues.put(COLUMN_REPS, reps);
        contentValues.put(COLUMN_DAY, day); // Add this line to insert the day
        db.insert(WORKOUT_TABLE_NAME, null, contentValues);
        db.close();
    }

    // Retrieve workouts for a specific user
    public Cursor getWorkoutsForUser(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(WORKOUT_TABLE_NAME, new String[]{
                COLUMN_WORKOUT_NAME,
                COLUMN_SETS,
                COLUMN_REPS,
                COLUMN_DAY // Include this column
        }, COLUMN_USER_ID + "=?", new String[]{String.valueOf(userId)}, null, null, null);
    }

}
