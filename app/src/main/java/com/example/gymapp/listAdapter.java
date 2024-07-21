package com.example.gymapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class listAdapter extends ArrayAdapter<String> {

    private Context context;
    private String[] items;

    public listAdapter(Context context, String[] items) {
        super(context, R.layout.list_item, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_item, parent, false);
        }

        TextView textViewItem = convertView.findViewById(R.id.textViewItem);
        ImageView imageViewIcon = convertView.findViewById(R.id.imageViewIcon);

        // Set the text for the item
        textViewItem.setText(items[position]);

        // Set the image resource for the icon based on position
        switch (position) {
            case 0:
                imageViewIcon.setImageResource(R.drawable.workout); // Replace with actual drawable resource
                break;
            case 1:
                imageViewIcon.setImageResource(R.drawable.history); // Replace with actual drawable resource
                break;
            default:
                imageViewIcon.setImageResource(R.drawable.plus); // Default image or handle other cases
                break;
        }

        // Set the click listener for each item
        convertView.setOnClickListener(v -> {
            if (position == 0) {
                // Handle click event for position 0
                context.startActivity(new Intent(context, exercise.class));
            } else if (position == 1) {
                // Handle click event for position 1
            }
            // Add more cases as needed for other positions
        });

        return convertView;
    }
}
