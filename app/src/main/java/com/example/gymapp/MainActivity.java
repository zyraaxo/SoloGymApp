package com.example.gymapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.ComponentActivity;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends ComponentActivity {

    private EditText editTextUsername, editTextPassword;
    private Button buttonLogin;
    private Button test;

    private TextView textViewRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        test = findViewById(R.id.test);

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
                // Handle register click (e.g., open registration activity)
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
            // Show error (e.g., Toast message)
            return;
        }

        // Handle login logic here
        // For example, verify credentials or start another activity
    }
}
