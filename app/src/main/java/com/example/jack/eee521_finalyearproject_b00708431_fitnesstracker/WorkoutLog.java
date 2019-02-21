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

public class WorkoutLog extends AppCompatActivity {


    FirebaseFirestore exerciseDB;
    DocumentReference docRef;

    Button addExerciseBtn;
    Button saveWorkoutBtn;
    ImageButton backButton;
    ListView workoutListView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_log);

        exerciseDB = FirebaseFirestore.getInstance();

        Workout userWorkout = new Workout();
        Exercise exericse = (Exercise)getIntent().getSerializableExtra("serialized_data");
        userWorkout.addExercise(exericse);

        addExerciseBtn = (Button)findViewById(R.id.WorkoutLog_addExerciseBtn);
        saveWorkoutBtn = (Button)findViewById(R.id.workout_Log_SaveWorkoutBtn);
        workoutListView = (ListView)findViewById(R.id.WorkoutListView);
        backButton = (ImageButton)findViewById(R.id.workoutLog_backBtn);
    }

    public void AddExercise(View view){
        Intent intent = new Intent(WorkoutLog.this, AddExercise.class);
        startActivity(intent);
    }

    public void SaveWorkout(View view, Workout userWorkout){



        userWorkout.setTotalReps();
        userWorkout.setTotalSets();

            //Write workout to Firestore
        //TODO add worout to firestore.

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
