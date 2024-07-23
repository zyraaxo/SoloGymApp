// WorkoutAdapter.java
package com.example.gymapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class WorkoutAdapter extends ArrayAdapter<Workout> {

    public WorkoutAdapter(Context context, List<Workout> workouts) {
        super(context, 0, workouts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Workout workout = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.show_exercise, parent, false);
        }

        TextView nameTextView = convertView.findViewById(R.id.workoutName);
        TextView setsTextView = convertView.findViewById(R.id.workoutSets);
        TextView repsTextView = convertView.findViewById(R.id.workoutReps);

        nameTextView.setText(workout.getName());
        setsTextView.setText("Sets: " + workout.getSets());
        repsTextView.setText("Reps: " + workout.getReps());

        return convertView;
    }
}

