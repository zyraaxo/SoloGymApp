package com.example.gymapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class WorkoutEntryActivity extends AppCompatActivity {

    private EditText editTextExerciseName, editTextSets, editTextReps, editTextRPE, editTextNotes;
    private Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_entry);

        // Initialize UI components
        editTextExerciseName = findViewById(R.id.editTextExerciseName);
        editTextSets = findViewById(R.id.editTextSets);
        editTextReps = findViewById(R.id.editTextReps);
        editTextRPE = findViewById(R.id.editTextRPE);
        editTextNotes = findViewById(R.id.editTextNotes);
        buttonSave = findViewById(R.id.buttonSave);

        // Set up button click listener
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveWorkout();
            }
        });
    }

    private void saveWorkout() {
        // Retrieve input values
        String exerciseName = editTextExerciseName.getText().toString();
        String sets = editTextSets.getText().toString();
        String reps = editTextReps.getText().toString();
        String rpe = editTextRPE.getText().toString();
        String notes = editTextNotes.getText().toString();

        // Validation (optional)
        if (exerciseName.isEmpty() || sets.isEmpty() || reps.isEmpty() || rpe.isEmpty()) {
            // Handle validation error (e.g., show a Toast)
            return;
        }

        // Save data (e.g., to a database or shared preferences)
        // For simplicity, this example uses a Toast message
        String message = String.format("Saved: %s, Sets: %s, Reps: %s, RPE: %s, Notes: %s",
                exerciseName, sets, reps, rpe, notes);
        // Display the saved data (replace this with actual saving logic)
        // Toast.makeText(this, message, Toast.LENGTH_LONG).show();

        // Optionally, start another activity or finish this one
        // startActivity(new Intent(this, AnotherActivity.class));
        // finish();
    }
}
