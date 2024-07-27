package com.example.gymapp;

import android.content.Context;
import android.content.SharedPreferences;

public class UserSessionManager {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public UserSessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void createLoginSession(int userId) {
        editor.putInt("userId", userId);
        editor.apply();
    }

    public int getUserId() {
        return sharedPreferences.getInt("userId", -1); // Default to -1 if no user ID is found
    }

    public void logoutUser() {
        editor.clear();
        editor.apply();
    }
}
