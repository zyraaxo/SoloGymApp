package com.example.gymapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ExercisesAdapter extends RecyclerView.Adapter<ExercisesAdapter.ExerciseViewHolder> {

    private final List<String> exercisesList;
    private final OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(String exerciseDetails);
    }

    public ExercisesAdapter(List<String> exercisesList, OnItemClickListener onItemClickListener) {
        this.exercisesList = exercisesList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        String exerciseDetails = exercisesList.get(position);
        holder.bind(exerciseDetails);
    }

    @Override
    public int getItemCount() {
        return exercisesList.size();
    }

    class ExerciseViewHolder extends RecyclerView.ViewHolder {

        private final TextView textView;

        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1); // Adjust if using a custom layout
        }

        public void bind(String exerciseDetails) {
            textView.setText(exerciseDetails);
            itemView.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(exerciseDetails);
                }
            });
        }
    }
}
