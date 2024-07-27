package com.example.gymapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.ComponentActivity;

public class signUp extends ComponentActivity {
    private DatabaseHelper dbHelper;
    private Button submitBtn;
    private EditText editTextName, editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        dbHelper = new DatabaseHelper(this);

        editTextName = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        submitBtn = findViewById(R.id.submitBtn);

        submitBtn.setOnClickListener(v -> {
            String name = editTextName.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            if (name.isEmpty() || password.isEmpty()) {
                Toast.makeText(signUp.this, "Please enter all the details", Toast.LENGTH_SHORT).show();
            } else if (isUserExist(name)) {
                Toast.makeText(signUp.this, "User already exists", Toast.LENGTH_SHORT).show();
            } else {
                insertUser(name, password);
            }
        });
    }

    private boolean isUserExist(String name) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_NAME + " WHERE " + DatabaseHelper.COLUMN_NAME + "=?";
        try (Cursor cursor = db.rawQuery(query, new String[]{name})) {
            return cursor.getCount() > 0;
        }
    }

    private void insertUser(String name, String password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, name);
        values.put(DatabaseHelper.COLUMN_PASSWORD, password);

        long newRowId = db.insert(DatabaseHelper.TABLE_NAME, null, values);
        if (newRowId != -1) {
            Toast.makeText(signUp.this, "Sign up successful!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(signUp.this, DashboardActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(signUp.this, "Sign up failed!", Toast.LENGTH_SHORT).show();
            Log.e("signUp", "Database insertion failed for user: " + name);
        }

        db.close();
    }
}
