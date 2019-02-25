package com.example.jack.eee521_finalyearproject_b00708431_fitnesstracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class WorkoutLog extends AppCompatActivity {


    FirebaseFirestore exerciseDB;
    DocumentReference docRef;

    Button addExerciseBtn;
    Button saveWorkoutBtn;
    ImageButton backButton;
    ListView workoutListView;

    ArrayList<Exercise> exerciseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_log);
        exerciseDB = FirebaseFirestore.getInstance();

        exerciseList = new ArrayList<>(); //Initialise Exercise list to display in the workout list view
        //Exercise exercise = (Exercise)getIntent().getSerializableExtra("serialized_data"); //Retrieve the Exercise from the add exercise Screen

        DummyData();// Populating Array list of example exercies

        addExerciseBtn = (Button)findViewById(R.id.WorkoutLog_addExerciseBtn);
        saveWorkoutBtn = (Button)findViewById(R.id.workout_Log_SaveWorkoutBtn);
        backButton = (ImageButton)findViewById(R.id.workoutLog_backBtn);


        workoutListView = (ListView)findViewById(R.id.WorkoutListView);
        //Creating a custom list view adapter to display exercise details
        ExerciseListAdapter adapter = new ExerciseListAdapter(this, R.layout.adapter_view_layout, exerciseList);
        workoutListView.setAdapter(adapter);
    }

    public void DummyData(){

        Exercise exercise =  new Exercise("Chest", "Bench Press", 20, 5);
        Exercise exercise2 =  new Exercise("Chest", "Flies", 20, 5);
        Exercise exercise3 =  new Exercise("Chest", "Incline Bench Press", 20, 5);
        Exercise exercise4 =  new Exercise("Chest", "Decline Bench Press", 20, 5);

        exerciseList.add(exercise);
        exerciseList.add(exercise2);
        exerciseList.add(exercise3);
        exerciseList.add(exercise4);
    }

    public void AddExercise(View view){
        Intent intent = new Intent(WorkoutLog.this, AddExercise.class);
        startActivity(intent);
    }

    public void SaveWorkout(View view){

        //TODO add workout to firestore.

        Toast.makeText(this, "Workout saved Successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(WorkoutLog.this, Menu.class);
        startActivity(intent);
        finish();
    }

    public void MenuReturn(View view){

        //Display a Dialog asking if the user wants to quit

        //IF yes then call back Workout Doc and delete it

        //IF no then call saveWorkout

        Intent intent = new Intent(WorkoutLog.this, Menu.class);
        startActivity(intent);
        finish();
    }
}
