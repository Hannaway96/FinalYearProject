package com.example.jack.eee521_finalyearproject_b00708431_fitnesstracker;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;

public class PastWorkouts extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    ListView pastWorkoutsListView;
    Button returnBtn;
    ArrayList<Workout> workoutsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_workouts);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // Sets screen to portrait

        //Initialise firebase instances
        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        pastWorkoutsListView = (ListView) findViewById(R.id.PastWorkout_ExercisesListView);
        returnBtn = (Button) findViewById(R.id.PastWorkouts_ReturnBtn);

        //Populate workout list with past workouts the user performed
        GetPastWorkouts();

       pastWorkoutsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //get the workout that the user clicks on and then get the Doc ID
                Workout workout = workoutsList.get(position);
                String workoutID = workout.getWorkoutUID();

                //Pass the doc ID to the next activity
                Intent intent = new Intent(PastWorkouts.this, PastWorkout.class);
                intent.putExtra("serialized_Workout_ID", workoutID);
                startActivity(intent);
            }
        });
    }

    public void GetPastWorkouts() {

        //Get current users ID
        String userID = firebaseAuth.getCurrentUser().getUid();
        //Query all the workouts within Workouts that contain the current users ID
        Query queryWorkouts = db.collection("Workouts").whereEqualTo("userUID", userID);

        queryWorkouts.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        Workout workout = new Workout();
                        workout.setWorkoutUID(document.getId());
                        workout.setWorkoutDateStr(document.get("workoutDateStr").toString());
                        workout.setTotalExercises(Integer.parseInt(document.get("totalExercises").toString()));
                        workout.setTotalSets(Integer.parseInt(document.get("totalSets").toString()));
                        workout.setTotalReps(Integer.parseInt(document.get("totalReps").toString()));
                        workoutsList.add(workout);
                    }

                    //TODO SORT PAST WORKOUTS BY DATE
                    //Collections.sort(workoutsList);

                    PastWorkoutsListAdapter workoutsListAdapter = new PastWorkoutsListAdapter(PastWorkouts.this, R.layout.adapter_past_workouts_list_view, workoutsList);
                    pastWorkoutsListView.setAdapter(workoutsListAdapter);
                    workoutsListAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    public void Return(View view){
        Intent intent = new Intent(PastWorkouts.this, WorkoutLog.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(PastWorkouts.this, WorkoutLog.class);
        startActivity(intent);
        finish();
    }
}
