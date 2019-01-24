package com.example.jack.eee521_finalyearproject_b00708431_fitnesstracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class WorkoutGenerator extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_generator);

        //changes the title of the ActionBar
        getSupportActionBar().setTitle("Workouts");
    }
}
