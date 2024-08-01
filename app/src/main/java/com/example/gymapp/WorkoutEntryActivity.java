package com.example.gymapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.ComponentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class WorkoutEntryActivity extends ComponentActivity {

    private EditText editTextExerciseName, editTextSets, editTextReps, editTextRPE, editTextNotes, searchBar;
    private Button buttonSave;
    private ImageView searchButton;
    private RecyclerView recyclerView;
    private int reps, sets;

    private static final int REQUEST_CODE = 1;
    private List<String> selectedExercisesList = new ArrayList<>();
    private ExercisesAdapter adapter;

    private List<String> setsRepsRPE = new ArrayList<String>();
    private List<String> repsRPE = new ArrayList<String>();
    private List<String> name = new ArrayList<String>();


    public WorkoutEntryActivity() {
        super();
    }



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
        searchButton = findViewById(R.id.search_icon);
        recyclerView = findViewById(R.id.recyclerViewExercises);

        // Enable strict mode policy for quick network testing (not recommended for production)
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Set up button click listener
        buttonSave.setOnClickListener(v -> saveWorkout());

        searchButton.setOnClickListener(view -> saveWorkouts());

        adapter = new ExercisesAdapter(selectedExercisesList, exercise -> {});
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }
    private List<Workout> workoutList = new ArrayList<>();
    private WorkoutAdapter adapters;

    private void saveWorkout() {
        String exerciseName = editTextExerciseName.getText().toString();
        String sets = editTextSets.getText().toString();
        String reps = editTextReps.getText().toString();
        String rpe = editTextRPE.getText().toString();
        String notes = editTextNotes.getText().toString();

        if (exerciseName.isEmpty() || sets.isEmpty() || reps.isEmpty()) {
            return;
        }

        Intent resultIntent = new Intent();
        resultIntent.putExtra("EXERCISE_NAME", exerciseName);
        resultIntent.putExtra("SETS", sets);
        resultIntent.putExtra("REPS", reps);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    private String getSearchQuery() {
        return searchBar.getText().toString().trim();
    }

    private void saveWorkouts() {
        // Retrieve search query
        String searchQuery = getSearchQuery();

        // Create an Intent to start the new activity
        Intent intent = new Intent(this, apiRecevier.class);
        intent.putExtra("EXTRA_SEARCH_QUERY", searchQuery);

        // Start the new activity for result
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            String selectedExerciseDetails = data.getStringExtra("SELECTED_EXERCISE_DETAILS");
            String selectedExerciseName = data.getStringExtra("SELECTED_EXERCISE_NAME");

            if (selectedExerciseDetails != null) {
                // Set the exercise name to the EditText
                editTextExerciseName.setText(selectedExerciseName);

                // Add the full details to the RecyclerView list
                selectedExercisesList.add(selectedExerciseDetails);
                adapter.notifyDataSetChanged();
            }
        }
    }



}
