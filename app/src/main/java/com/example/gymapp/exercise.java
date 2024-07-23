package com.example.gymapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.ComponentActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class exercise extends ComponentActivity {
    private Button addBtn;
    private ListView showExercises;
    private TextView noneAdded;
    private HorizontalScrollView dayAmount;
    private WorkoutAdapter adapter;
    private ArrayList<Workout> workoutList;
    private HashMap<Integer, ArrayList<Workout>> exercisesByDay;
    private int currentDay = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_log);

        addBtn = findViewById(R.id.addBtn);
        showExercises = findViewById(R.id.listViewLog);
        noneAdded = findViewById(R.id.noneAdded);
        dayAmount = findViewById(R.id.dayAmount); // Initialize the HorizontalScrollView

        workoutList = new ArrayList<>();
        exercisesByDay = new HashMap<>(); // Initialize the HashMap
        adapter = new WorkoutAdapter(this, workoutList);
        showExercises.setAdapter(adapter);

        // Retrieve selected days from the intent
        int selectedDays = getIntent().getIntExtra("selectedDays", 1);
        updateDays(selectedDays);

        addBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, WorkoutEntryActivity.class);
            intent.putExtra("selectedDay", currentDay); // Pass the current day to the new activity
            startActivityForResult(intent, 1);
        });
    }

    public void updateDays(int days) {
        LinearLayout dayLayout = (LinearLayout) ((HorizontalScrollView) findViewById(R.id.dayAmount)).getChildAt(0);
        dayLayout.removeAllViews();

        for (int i = 1; i <= days; i++) {
            TextView dayTextView = new TextView(this);
            dayTextView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            dayTextView.setText("Day " + i);
            dayTextView.setTextSize(18);
            dayTextView.setTextColor(getResources().getColor(android.R.color.black));
            dayTextView.setPadding(8, 8, 8, 8);
            dayTextView.setGravity(Gravity.CENTER);
            int finalI = i;
            dayTextView.setOnClickListener(v -> {
                currentDay = finalI;
                updateWorkoutListForDay();
            });

            dayLayout.addView(dayTextView);
        }
    }

    private void updateWorkoutListForDay() {
        workoutList.clear();
        if (exercisesByDay != null && exercisesByDay.containsKey(currentDay)) {
            workoutList.addAll(exercisesByDay.get(currentDay));
        }
        adapter.notifyDataSetChanged();
        noneAdded.setVisibility(workoutList.isEmpty() ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Retrieve the workout details and day from the intent
            String name = data.getStringExtra("EXERCISE_NAME");
            String sets = data.getStringExtra("SETS");
            String reps = data.getStringExtra("REPS");
            int day = data.getIntExtra("selectedDay", currentDay);

            if (exercisesByDay == null) {
                exercisesByDay = new HashMap<>(); // Initialize if null
            }

            if (!exercisesByDay.containsKey(day)) {
                exercisesByDay.put(day, new ArrayList<>());
            }
            Workout workout = new Workout(name, sets, reps, day);
            exercisesByDay.get(day).add(workout);

            updateWorkoutListForDay();
        }
    }
}
