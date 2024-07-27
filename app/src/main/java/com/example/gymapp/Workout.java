package com.example.gymapp;

public class Workout {
    private String name;
    private String sets;
    private String reps;
    private int day;

    public Workout(String name, String sets, String reps, int day) {
        this.name = name;
        this.sets = sets;
        this.reps = reps;
        this.day = day;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSets() {
        return sets;
    }

    public void setSets(String sets) {
        this.sets = sets;
    }

    public String getReps() {
        return reps;
    }

    public void setReps(String reps) {
        this.reps = reps;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
