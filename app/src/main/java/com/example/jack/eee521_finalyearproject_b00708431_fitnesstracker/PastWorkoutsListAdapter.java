package com.example.jack.eee521_finalyearproject_b00708431_fitnesstracker;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PastWorkoutsListAdapter extends ArrayAdapter<Workout> {

    private static final String TAG = "PastWorkoutsListAdapter";
    private Context mContext;
    int mResource;

    public PastWorkoutsListAdapter(Context context, int resource, ArrayList<Workout> objects){

        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //this function is responsible for getting the view and then attaching it to the list view
        //Workout Information
        String workoutDate = getItem(position).getWorkoutDateStr();
        int totalExercises = getItem(position).getTotalExercises();
        int noOfReps = getItem(position).getTotalReps();
        int noOfSets = getItem(position).getTotalSets();

        //Workout Object with the information

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvWorkoutDate = (TextView)convertView.findViewById(R.id.WorkoutDateTxtView);
        TextView tvExercisesComp = (TextView)convertView.findViewById(R.id.ExercisesCompleteTxtView);
        TextView tvTotalRepsComp = (TextView)convertView.findViewById(R.id.TotalRepsTxtView);
        TextView tvTotalSetsComp = (TextView)convertView.findViewById(R.id.TotalSetsTxtView);

        tvWorkoutDate.setText(workoutDate);
        tvExercisesComp.setText("Total Exercises: " + totalExercises);
        tvTotalRepsComp.setText("Total Reps: " + noOfReps);
        tvTotalSetsComp.setText("Total Sets: " + noOfSets);

        return convertView;
    }
}
