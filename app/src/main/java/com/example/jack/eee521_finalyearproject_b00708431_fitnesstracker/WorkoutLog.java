package com.example.jack.eee521_finalyearproject_b00708431_fitnesstracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

public class WorkoutLog extends AppCompatActivity {

    Button addExerciseBtn;
    Button saveWorkoutBtn;
    ImageButton backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_log);

        //changes the title of the ActionBar
        //getSupportActionBar().setTitle("Workout Log");

        addExerciseBtn = (Button)findViewById(R.id.WorkoutLog_addExerciseBtn);
        saveWorkoutBtn = (Button)findViewById(R.id.workout_Log_SaveWorkoutBtn);
        backButton = (ImageButton)findViewById(R.id.workoutLog_backBtn);
    }

    public void AddExercise(View view){
        Intent intent = new Intent(WorkoutLog.this, AddExercise.class);
        startActivity(intent);
    }

    public void SaveWorkout(View view){
        Intent intent = new Intent(WorkoutLog.this, Menu.class);
        startActivity(intent);
    }
}
