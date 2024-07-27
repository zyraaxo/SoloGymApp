package com.example.gymapp;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.ComponentActivity;
import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends ComponentActivity {

    private ListView listView;
    private String[] items = {"Workouts", "History","Create"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);


        listView = findViewById(R.id.lists);

        // Create an instance of the custom adapter and set it to the ListView
        listAdapter adapter = new listAdapter(this, items);
        listView.setAdapter(adapter);

        // Set an item click listener (optional)
        listView.setOnItemClickListener((parent, view, position, id) -> {
            if (position == 0) {
                // Handle "Workouts" click
            } else if (position == 1) {
                // Handle "History" click
            }
        });
    }
}
