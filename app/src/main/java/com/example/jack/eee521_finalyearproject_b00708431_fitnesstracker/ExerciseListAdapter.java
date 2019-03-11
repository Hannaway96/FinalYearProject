package com.example.jack.eee521_finalyearproject_b00708431_fitnesstracker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ExerciseListAdapter extends ArrayAdapter<Exercise> {

    private static final String TAG = "ExerciseListAdapter";
    private Context mContext;
    int mResource;

    public ExerciseListAdapter(Context context, int resource, ArrayList<Exercise> objects){

        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //this function is responsible for getting the view and then attaching it to the list view
        //Exercise Information
        String exerciseName = getItem(position).getExerciseName();
        String exerciseType = getItem(position).getExerciseType();
        int noOfReps = getItem(position).getNoOfReps();
        int noOfSets = getItem(position).getNoOfSets();

        //exercise Object with the information
       // Exercise exercise = new Exercise(exerciseType, exerciseName, noOfReps, noOfSets);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvExerciseName = (TextView)convertView.findViewById(R.id.ExerciseNameTxtView);
        TextView tvReps = (TextView)convertView.findViewById(R.id.repsTxtView);
        TextView tvSets = (TextView)convertView.findViewById(R.id.setsTxtView);
        TextView tvType = (TextView)convertView.findViewById(R.id.typeTxtView);

        tvExerciseName.setText(exerciseName);
        tvType.setText("Targets: " + exerciseType);
        tvReps.setText("# Of Reps " + noOfReps);
        tvSets.setText("# Of Sets " + noOfSets);

        return convertView;
    }
}
