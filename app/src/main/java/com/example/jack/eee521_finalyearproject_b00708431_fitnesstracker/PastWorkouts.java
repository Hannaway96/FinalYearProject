package com.example.jack.eee521_finalyearproject_b00708431_fitnesstracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class PastWorkouts extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    ListView pastWorkoutsListView;
    Button returnBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_workouts);

        //Initialise firebase instances
        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        pastWorkoutsListView = (ListView)findViewById(R.id.PastWorkout_ExercisesListView);
        returnBtn = (Button)findViewById(R.id.PastWorkouts_ReturnBtn);
    }



    public void Return(View view){
        Intent intent = new Intent(PastWorkouts.this, WorkoutLog.class);
        startActivity(intent);
        finish();
    }
}
