package com.example.gymapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.ComponentActivity;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends ComponentActivity {

    DatabaseHelper dbHelper;

    private EditText editTextUsername, editTextPassword;
    private Button buttonLogin;
    private Button test;

    private TextView textViewRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        test = findViewById(R.id.test);

        dbHelper = new DatabaseHelper(this);


        // Set the content view to login.xml

        // Initialize UI components
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewRegister = findViewById(R.id.textViewRegister);

        // Set up button click listener
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        // Set up register click listener
        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, signUp.class);
                startActivity(intent);
            }
        });
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start DashboardActivity
                Intent intent = new Intent(MainActivity.this, DashboardActivity.class);

                // Start the DashboardActivity
                startActivity(intent);
            }
        });

    }

    private void loginUser() {
        // Retrieve input values
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();

        // Simple validation (check if fields are empty)
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(MainActivity.this, "Please enter both username and password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if user exists and get user ID
        int userId = dbHelper.checkUser(username, password);
        if (userId != -1) {
            // User exists, handle successful login
            Toast.makeText(MainActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();

            // Create user session
            UserSessionManager sessionManager = new UserSessionManager(MainActivity.this);
            sessionManager.createLoginSession(userId);

            // Redirect to the dashboard
            Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
            startActivity(intent);
        } else {
            // User does not exist or credentials are incorrect
            Toast.makeText(MainActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
        }
    }
}