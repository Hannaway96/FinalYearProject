package com.example.jack.eee521_finalyearproject_b00708431_fitnesstracker;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PastWorkout extends AppCompatActivity {

    private FirebaseFirestore db;
    private FirebaseAuth firebaseAuth;
    String workoutID;
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
        final DocumentReference docRef = db.collection("Workouts").document(workoutID);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>(){
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task){
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){

                        Workout workout = new Workout();

                        workout.setWorkoutUID(workoutID);
                        workout.setWorkoutDateStr(document.get("workoutDateStr").toString());
                        workout.setTotalExercises(Integer.parseInt(document.get("totalExercises").toString()));
                        workout.setTotalReps(Integer.parseInt(document.get("totalReps").toString()));
                        workout.setTotalSets(Integer.parseInt(document.get("totalSets").toString()));

                         //TODO FIGURE OUT HOW TO READ IN EXERCISES FROM OBJEXT ARRAY ON FIREBASE

                        totalRepsValueTxtView.setText(workout.getTotalReps());
                        totalSetsValueTxtView.setText(workout.getTotalSets());
                        dateCompletedTxtView.setText(workout.getWorkoutDateStr());
                    }
                }
            }
        });
    }

    public void Return (View view){
        Intent intent = new Intent(PastWorkout.this, PastWorkouts.class);
        startActivity(intent);
        finish();
    }
}
