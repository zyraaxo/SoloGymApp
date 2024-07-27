package com.example.gymapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.ComponentActivity;

public class signUp extends ComponentActivity {
    DatabaseHelper dbHelper;
    Button submitBtn;

    EditText editTextName, editTextPassword;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        dbHelper = new DatabaseHelper(this);

        editTextName = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        submitBtn = findViewById(R.id.submitBtn);

        submitBtn.setOnClickListener(v -> {
            String name = editTextName.getText().toString();
            String password = editTextPassword.getText().toString();

            if (name.isEmpty() || password.isEmpty()) {
                Toast.makeText(signUp.this, "Please enter all the details", Toast.LENGTH_SHORT).show();
            } else {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("name", name);
                values.put("password", password);

                long newRowId = db.insert("myTable", null, values);
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
        });
    }
}
