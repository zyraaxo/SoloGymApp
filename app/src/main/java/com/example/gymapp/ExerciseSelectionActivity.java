package com.example.gymapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.ComponentActivity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class ExerciseSelectionActivity extends ComponentActivity {

    private CardView cardOne, cardTwo, cardThree, cardFour, cardFive, cardSix, cardSeven;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day_amount);

        // Initialize CardViews
        cardOne = findViewById(R.id.one);
        cardTwo = findViewById(R.id.two);
        cardThree = findViewById(R.id.three);
        cardFour = findViewById(R.id.four);
        cardFive = findViewById(R.id.five);
        cardSix = findViewById(R.id.six);
        cardSeven = findViewById(R.id.seven);

        // Set OnClickListener for each CardView
        setCardClickListener(cardOne, 1);
        setCardClickListener(cardTwo, 2);
        setCardClickListener(cardThree, 3);
        setCardClickListener(cardFour, 4);
        setCardClickListener(cardFive, 5);
        setCardClickListener(cardSix, 6);
        setCardClickListener(cardSeven, 7);
    }

    private void setCardClickListener(CardView cardView, final int days) {
        cardView.setOnClickListener(v -> {
            Intent intent = new Intent(ExerciseSelectionActivity.this, exercise.class);
            intent.putExtra("selectedDays", days);
            startActivity(intent);
        });
    }
}
