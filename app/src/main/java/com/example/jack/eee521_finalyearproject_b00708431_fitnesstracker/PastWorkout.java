package com.example.jack.eee521_finalyearproject_b00708431_fitnesstracker;

import android.content.Intent;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PastWorkout extends AppCompatActivity {

    private FirebaseFirestore db;
    private FirebaseAuth firebaseAuth;
    private String workoutID;
    private TextView totalRepsValueTxtView, totalSetsValueTxtView, dateCompletedTxtView;
    private ListView pastExerciseList;
    private Button returnBtn;
    private Workout workout = new Workout();
    private ArrayList<Exercise> pastExercises = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_workout);

        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        workoutID = getIntent().getStringExtra("serialized_Workout_ID");

        totalRepsValueTxtView = (TextView)findViewById(R.id.PastWorkout_totalRepsValtxtView);
        totalSetsValueTxtView = (TextView)findViewById(R.id.PastWorkout_totalSetsValTxtView);
        dateCompletedTxtView = (TextView)findViewById(R.id.PastWorkout_DateCompletedValTxtView);
        pastExerciseList = (ListView)findViewById(R.id.PastWorkout_ExercisesListView);
        returnBtn = (Button)findViewById(R.id.PastWorkout_ReturnBtn);

        populateActivity(workoutID);
    }


    public void populateActivity(final String workoutID){

        //Retrieve document relating to workoutChosen by user
        DocumentReference docRef = db.collection("Workouts").document(workoutID);

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                workout.setWorkoutUID(workoutID);
                workout.setWorkoutDateStr(documentSnapshot.get("workoutDateStr").toString());
                workout.setTotalExercises(Integer.parseInt(documentSnapshot.get("totalExercises").toString()));
                workout.setTotalReps(Integer.parseInt(documentSnapshot.get("totalReps").toString()));
                workout.setTotalSets(Integer.parseInt(documentSnapshot.get("totalSets").toString()));

                Map<String, Object> map = documentSnapshot.getData();   //Get Workout Document
                ArrayList mapExerciseList = (ArrayList)map.get("exercises");    //Get exercises maps and put them into an array list

                for(int i = 0; i < mapExerciseList.size(); i++) {

                    Map<String, Object> exerciseMap = (Map<String, Object>) mapExerciseList.get(i); //Loop through each exercise in the map

                    Exercise tempExercise = new Exercise();             //create temp  exercise class
                    tempExercise.setExerciseName(exerciseMap.get("exerciseName").toString());
                    tempExercise.setExerciseType(exerciseMap.get("exerciseType").toString());
                    tempExercise.setNoOfReps(Integer.parseInt(exerciseMap.get("noOfReps").toString()));
                    tempExercise.setNoOfSets(Integer.parseInt(exerciseMap.get("noOfSets").toString()));

                    pastExercises.add(tempExercise);    //add exercise class to the pastExercises list
                }


                //Set values of the past workout onto the activity
                totalRepsValueTxtView.setText(String.valueOf(workout.getTotalReps()));
                totalSetsValueTxtView.setText(String.valueOf(workout.getTotalSets()));
                dateCompletedTxtView.setText(workout.getWorkoutDateStr());

                //Set exercise list adapter
                ExerciseListAdapter exerciseListAdapter = new ExerciseListAdapter(PastWorkout.this, R.layout.adapter_view_layout, pastExercises);
                pastExerciseList.setAdapter(exerciseListAdapter);
                exerciseListAdapter.notifyDataSetChanged();
            }
        });
    }

    public void Return (View view){
        Intent intent = new Intent(PastWorkout.this, PastWorkouts.class);
        startActivity(intent);
        finish();
    }
}
