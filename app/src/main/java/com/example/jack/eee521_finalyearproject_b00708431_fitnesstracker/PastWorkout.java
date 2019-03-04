package com.example.jack.eee521_finalyearproject_b00708431_fitnesstracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class PastWorkout extends AppCompatActivity {

    private FirebaseFirestore db;
    private FirebaseAuth firebaseAuth;
    TextView totalRepsValueTxtView, totalSetsValueTxtView, dateCompletedTxtView;
    ListView pastExerciseList;
    Button returnBtn;
    ArrayList<Exercise> pastExercises = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_workout);

        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        totalRepsValueTxtView = (TextView)findViewById(R.id.PastWorkout_totalRepsValtxtView);
        totalSetsValueTxtView = (TextView)findViewById(R.id.PastWorkout_totalSetsValTxtView);
        dateCompletedTxtView = (TextView)findViewById(R.id.PastWorkout_DateCompletedValTxtView);
        pastExerciseList = (ListView)findViewById(R.id.PastWorkout_ExercisesListView);
        returnBtn = (Button)findViewById(R.id.PastWorkout_ReturnBtn);
    }



    public void Return (View view){
        Intent intent = new Intent(PastWorkout.this, PastWorkouts.class);
        startActivity(intent);
        finish();
    }
}
