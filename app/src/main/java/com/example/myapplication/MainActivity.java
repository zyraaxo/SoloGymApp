package com.example.myapplication;

import android.os.Bundle;
import android.os.StrictMode;
import android.widget.TextView;

import androidx.activity.ComponentActivity;
import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MainActivity extends ComponentActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

        // Enable strict mode policy for quick network testing (not recommended for production)
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        fetchExercises();
    }

    private void fetchExercises() {
        new Thread(() -> {
            try {
                String apiUrl = "https://api.api-ninjas.com/v1/exercises?muscle=chest";
                String apiKey = "fyL/rfKoLjujVUTNHtnCbw==fFVCrisTBu14sqh0"; // Replace with your actual API key

                URL url = new URL(apiUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("accept", "application/json");
                connection.setRequestProperty("X-Api-Key", apiKey); // Set API key for authentication

                InputStream responseStream = connection.getInputStream();
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(responseStream);

                StringBuilder exercisesStringBuilder = new StringBuilder();
                // Assuming the API response is an array of exercises
                for (JsonNode exercise : root) {
                    exercisesStringBuilder.append("Name: ").append(exercise.path("name").asText()).append("\n")
                            .append("Type: ").append(exercise.path("type").asText()).append("\n")
                            .append("Muscle: ").append(exercise.path("muscle").asText()).append("\n")
                            .append("Equipment: ").append(exercise.path("equipment").asText()).append("\n")
                            .append("Difficulty: ").append(exercise.path("difficulty").asText()).append("\n")
                            .append("Instructions: ").append(exercise.path("instructions").asText()).append("\n\n");
                }

                final String exercisesString = exercisesStringBuilder.toString();
                runOnUiThread(() -> textView.setText(exercisesString));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
