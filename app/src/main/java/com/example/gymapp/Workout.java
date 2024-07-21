package com.example.gymapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Workout {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String exerciseName;
    public int sets;
    public int reps;
    public float rpe;
    public String notes;
}
