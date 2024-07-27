package com.example.gymapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.ComponentActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

public class exercise extends ComponentActivity {
    private Button addBtn;
    private ListView showExercises;
    private TextView noneAdded;
    private HorizontalScrollView dayAmount;
    private WorkoutAdapter adapter;
    private ArrayList<Workout> workoutList;
    private HashMap<Integer, ArrayList<Workout>> exercisesByDay;
    private int currentDay = 1;
    public Button saveBtn;
    private TextView dayTextView;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_log);

        // Retrieve the saved number of days
        SharedPreferences preferences = getSharedPreferences("GymAppPrefs", MODE_PRIVATE);
        int savedDays = preferences.getInt("number_of_days", 7); // Default to 7 if not found

        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        saveBtn = findViewById(R.id.saveBtn1);

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String firstDay = format.format(calendar.getTime());

        calendar.add(Calendar.DAY_OF_MONTH, 6);
        String lastDay = format.format(calendar.getTime());

        String dateRange = firstDay + " - " + lastDay;
        dayTextView = findViewById(R.id.dates);
        dayTextView.setText(dateRange);

        addBtn = findViewById(R.id.addBtn);
        showExercises = findViewById(R.id.listViewLog);
        noneAdded = findViewById(R.id.noneAdded);
        dayAmount = findViewById(R.id.dayAmount);

        workoutList = new ArrayList<>();
        exercisesByDay = new HashMap<>();
        adapter = new WorkoutAdapter(this, workoutList);
        showExercises.setAdapter(adapter);

        dbHelper = new DatabaseHelper(this);
        int selectedDays = getIntent().getIntExtra("selectedDays", savedDays);
        updateDays(selectedDays);

        addBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, WorkoutEntryActivity.class);
            intent.putExtra("selectedDay", currentDay);
            startActivityForResult(intent, 1);
        });

        showExercises.setOnItemClickListener((parent, view, position, id) -> {
            Workout selectedWorkout = workoutList.get(position);
            Intent intent = new Intent(exercise.this, editExercise.class);
            intent.putExtra("EXERCISE_NAME", selectedWorkout.getName());
            intent.putExtra("SETS", selectedWorkout.getSets());
            intent.putExtra("REPS", selectedWorkout.getReps());
            intent.putExtra("POSITION", position);
            startActivityForResult(intent, 2);
        });

        Intent intent = getIntent();
        if (intent != null && intent.getBooleanExtra("HIDE_BUTTON", false)) {
            saveBtn.setVisibility(View.INVISIBLE);
        }

        saveBtn.setOnClickListener(v -> {
            int userId = getCurrentUserId();
            saveWorkoutsToDatabase(userId);
        });

        loadWorkoutsForUser();
    }

    private void loadWorkoutsForUser() {
        int userId = getCurrentUserId();
        Cursor cursor = dbHelper.getWorkoutsForUser(userId);

        if (cursor != null) {
            String[] columnNames = cursor.getColumnNames();
            for (String columnName : columnNames) {
                Log.d("CursorColumn", "Column: " + columnName);
            }

            if (cursor.moveToFirst()) {
                do {
                    String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_WORKOUT_NAME));
                    String sets = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_SETS));
                    String reps = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_REPS));
                    int day = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_DAY)); // Read the day from cursor

                    Workout workout = new Workout(name, sets, reps, day);
                    if (!exercisesByDay.containsKey(day)) {
                        exercisesByDay.put(day, new ArrayList<>());
                    }
                    exercisesByDay.get(day).add(workout);
                } while (cursor.moveToNext());

                cursor.close();
                updateWorkoutListForDay();
            }
        }
    }


    private void saveNumberOfDays(int days) {
        SharedPreferences preferences = getSharedPreferences("GymAppPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("number_of_days", days);
        editor.apply();
    }



    private void saveWorkoutsToDatabase(int userId) {
        for (int day : exercisesByDay.keySet()) {
            ArrayList<Workout> workouts = exercisesByDay.get(day);
            if (workouts != null) {
                for (Workout workout : workouts) {
                    dbHelper.insertWorkout(userId, workout.getName(), workout.getSets(), workout.getReps(), day);
                }
            }
        }

        Toast.makeText(this, "Workouts saved to database", Toast.LENGTH_SHORT).show();
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

        // Save the number of days
        saveNumberOfDays(days);
    }


    private void updateWorkoutListForDay() {
        workoutList.clear();
        if (exercisesByDay != null && exercisesByDay.containsKey(currentDay)) {
            ArrayList<Workout> workouts = exercisesByDay.get(currentDay);
            if (workouts != null) {
                workoutList.addAll(workouts);
            }
        }
        adapter.notifyDataSetChanged();
        noneAdded.setVisibility(workoutList.isEmpty() ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String name = data.getStringExtra("EXERCISE_NAME");
            String sets = data.getStringExtra("SETS");
            String reps = data.getStringExtra("REPS");
            int day = data.getIntExtra("selectedDay", currentDay);

            if (exercisesByDay == null) {
                exercisesByDay = new HashMap<>();
            }

            if (!exercisesByDay.containsKey(day)) {
                exercisesByDay.put(day, new ArrayList<>());
            }
            Workout workout = new Workout(name, sets, reps, day);
            exercisesByDay.get(day).add(workout);

            updateWorkoutListForDay();
        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            String updatedName = data.getStringExtra("UPDATED_NAME");
            String updatedSets = data.getStringExtra("UPDATED_SETS");
            String updatedReps = data.getStringExtra("UPDATED_REPS");
            int position = data.getIntExtra("POSITION", -1);

            if (position != -1) {
                Workout updatedWorkout = workoutList.get(position);
                updatedWorkout.setName(updatedName);
                updatedWorkout.setSets(updatedSets);
                updatedWorkout.setReps(updatedReps);

                adapter.notifyDataSetChanged();
            }
        }
    }

    private int getCurrentUserId() {
        UserSessionManager sessionManager = new UserSessionManager(this);
        return sessionManager.getUserId();
    }
}

