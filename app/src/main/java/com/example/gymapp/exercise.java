package com.example.gymapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.ComponentActivity;

public class exercise extends ComponentActivity {
    private Button addBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_log);
        addBtn = findViewById(R.id.addBtn);

        addBtn.setOnClickListener(v -> {Intent intent = new Intent(this, WorkoutEntryActivity.class);startActivity(intent);});
    }
}