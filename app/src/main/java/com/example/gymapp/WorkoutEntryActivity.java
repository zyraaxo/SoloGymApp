package com.example.gymapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.ComponentActivity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

public class WorkoutEntryActivity extends ComponentActivity {

    private EditText editTextExerciseName, editTextSets, editTextReps, editTextRPE, editTextNotes, searchBar;
    private Button buttonSave;
    private ImageView searchButton;

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
        searchBar = findViewById(R.id.search_bar);
        searchButton = findViewById(R.id.searchBtn);

        // Set up button click listener
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveWorkout();
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WorkoutEntryActivity.this, apiRecevier.class);
                startActivity(intent);
                saveWorkouts();
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

    // Method to get the query from the search bar
    private String getSearchQuery() {
        return searchBar.getText().toString().trim();
    }
    private void saveWorkouts() {
        // Retrieve search query
        String searchQuery = getSearchQuery();

        // Create an Intent to start the new activity
        Intent intent = new Intent(this, apiRecevier.class);
        intent.putExtra("EXTRA_SEARCH_QUERY", searchQuery);

        // Start the new activity
        startActivity(intent);
    }

}

