// Workout.java
package com.example.gymapp;

public class Workout {
    private String name;
    private String sets;
    private String reps;
    private int day; // Add this attribute

    public Workout(String name, String sets, String reps, int day) {
        this.name = name;
        this.sets = sets;
        this.reps = reps;
        this.day = day;
    }

    public String getName() {
        return name;
    }

    public String getSets() {
        return sets;
    }

    public String getReps() {
        return reps;
    }

    public int getDay() {
        return day;
    }
}
