package com.example.gymapp;

import static android.app.PendingIntent.getActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.ComponentActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class editExercise extends ComponentActivity {

    private EditText editTextName, editTextSets, editTextReps;
    private TextView textViewDays;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_workout); // Ensure this is the correct layout file

        // Initialize the TextView for the date range
        textViewDays = findViewById(R.id.dayText);

        // Initialize the EditTexts for exercise details
        editTextName = findViewById(R.id.edit);
        editTextSets = findViewById(R.id.editTextSets);
        editTextReps = findViewById(R.id.editTextReps);

        // Get the current week starting from Monday
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String firstDay = format.format(calendar.getTime());

        calendar.add(Calendar.DAY_OF_MONTH, 6);
        String lastDay = format.format(calendar.getTime());

        // Set the date range in the TextView
        String dateRange = firstDay + " - " + lastDay;
        textViewDays.setText(dateRange);

        // Retrieve workout details from the intent
        Intent intent = getIntent();
        String name = intent.getStringExtra("EXERCISE_NAME");
        String sets = intent.getStringExtra("SETS");
        String reps = intent.getStringExtra("REPS");
        position = intent.getIntExtra("POSITION", -1);

        // Populate the fields with the workout details
        if (name != null) {
            editTextName.setText(name);
        }
        if (sets != null) {
            editTextSets.setText(sets);
        }
        if (reps != null) {
            editTextReps.setText(reps);
        }

        Button saveBtn = findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(v -> {
            // Handle saving the edited workout details
            String updatedName = editTextName.getText().toString();
            String updatedSets = editTextSets.getText().toString();
            String updatedReps = editTextReps.getText().toString();

            // Pass the updated details back to the previous activity
            Intent resultIntent = new Intent();
            resultIntent.putExtra("UPDATED_NAME", updatedName);
            resultIntent.putExtra("UPDATED_SETS", updatedSets);
            resultIntent.putExtra("UPDATED_REPS", updatedReps);
            resultIntent.putExtra("POSITION", position);
            setResult(RESULT_OK, resultIntent);
            Toast.makeText(this, "Exercise updated successfully",
                    Toast.LENGTH_LONG).show();
            finish();
        });

        Button editBtn = findViewById(R.id.editBtn);
        editBtn.setOnClickListener(v -> {
            editTextName.setEnabled(true);
            editTextSets.setEnabled(true);
            editTextReps.setEnabled(true);
            editTextName.setFocusable(true);
            editTextReps.setFocusable(true);
            editTextSets.setFocusable(true);

            editTextSets.setFocusableInTouchMode(true);
            editTextReps.setFocusableInTouchMode(true);



            saveBtn.setEnabled(true);
            saveBtn.setVisibility(View.VISIBLE);


        });
    }
}
