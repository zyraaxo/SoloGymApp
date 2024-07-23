package com.example.gymapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.ComponentActivity;

import java.io.InputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class apiRecevier extends ComponentActivity {
    private ListView listView;
    private String searchQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results); // Make sure this layout contains the ListView

        // Initialize UI component
        listView = findViewById(R.id.listView);

        // Get the search query from the Intent
        searchQuery = getIntent().getStringExtra("EXTRA_SEARCH_QUERY");

        // Enable strict mode policy for quick network testing (not recommended for production)
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        fetchExercises();
    }

    private void fetchExercises() {
        new Thread(() -> {
            try {
                String apiUrl = "https://api.api-ninjas.com/v1/exercises?muscle=" + searchQuery;
                String apiKey = "fyL/rfKoLjujVUTNHtnCbw==fFVCrisTBu14sqh0"; // Replace with your actual API key

                URL url = new URL(apiUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("accept", "application/json");
                connection.setRequestProperty("X-Api-Key", apiKey); // Set API key for authentication

                InputStream responseStream = connection.getInputStream();
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(responseStream);

                List<String> exercisesList = new ArrayList<>();
                List<String> exerciseNamesList = new ArrayList<>();
                for (JsonNode exercise : root) {
                    String exerciseName = exercise.path("name").asText();
                    String exerciseDetails = String.format("Name: %s\nType: %s\nMuscle: %s\nEquipment: %s\nDifficulty: %s\nInstructions: %s",
                            exerciseName,
                            exercise.path("type").asText(),
                            exercise.path("muscle").asText(),
                            exercise.path("equipment").asText(),
                            exercise.path("difficulty").asText(),
                            exercise.path("instructions").asText());

                    exercisesList.add(exerciseDetails);
                    exerciseNamesList.add(exerciseName); // Store exercise names separately
                }

                final List<String> finalExercisesList = exercisesList;
                final List<String> finalExerciseNamesList = exerciseNamesList;

                runOnUiThread(() -> {
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                            android.R.layout.simple_list_item_1, finalExercisesList);
                    listView.setAdapter(adapter);

                    listView.setOnItemClickListener((parent, view, position, id) -> {
                        String selectedExerciseDetails = finalExercisesList.get(position);
                        String selectedExerciseName = finalExerciseNamesList.get(position);

                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("SELECTED_EXERCISE_DETAILS", selectedExerciseDetails);
                        resultIntent.putExtra("SELECTED_EXERCISE_NAME", selectedExerciseName);
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    });
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }


    // Method to extract the exercise name from the details string
    private String extractExerciseName(String exerciseDetails) {
        // Assuming the name is always the first line and starts with "Name: "
        int nameStartIndex = exerciseDetails.indexOf("Name: ");
        if (nameStartIndex != -1) {
            int nameEndIndex = exerciseDetails.indexOf("\n", nameStartIndex);
            if (nameEndIndex != -1) {
                return exerciseDetails.substring(nameStartIndex + 6, nameEndIndex).trim(); // Extract and trim the name
            }
        }
        return ""; // Return an empty string if name extraction fails
    }
}
