package com.example.jack.eee521_finalyearproject_b00708431_fitnesstracker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class WorkoutLog extends AppCompatActivity {

    Button addExerciseBtn;
    Button saveWorkoutBtn;
    ImageButton backButton;
    ListView workoutListView;
    ArrayList<Exercise> exerciseList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_log);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // Sets screen to portrait

        //Retrieve Parceable workout from AddExercise and equal it's exercises to this current exercises
        if(getIntent().getParcelableArrayListExtra("newList") != null){
            exerciseList= getIntent().getParcelableArrayListExtra("newList");
        }

        addExerciseBtn = (Button)findViewById(R.id.WorkoutLog_addExerciseBtn);
        saveWorkoutBtn = (Button)findViewById(R.id.workout_Log_SaveWorkoutBtn);
        backButton = (ImageButton)findViewById(R.id.workoutLog_backBtn);

        workoutListView = (ListView)findViewById(R.id.WorkoutListView);
        //Creating a custom list view adapter to display exercise details
        ExerciseListAdapter adapter = new ExerciseListAdapter(this, R.layout.adapter_view_layout, exerciseList);
        workoutListView.setAdapter(adapter);
    }

    public void AddExercise(View view){

        Intent intent = new Intent(WorkoutLog.this, AddExercise.class);

        if(exerciseList.size() > 0) {
            intent.putExtra("serialized_exerciseList", exerciseList);
        }
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


        //Make sure the user has saved their progress otherwise
        if(exerciseList.size() > 0) {
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setTitle("Workout not saved")
                    .setMessage("You have not saved your current Workout session, do you wish to save it?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            //TODO SAVE WORKOUT TO FIREBASE
                            Toast.makeText(WorkoutLog.this, "Workout Saved", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(WorkoutLog.this, Menu.class);
                            startActivity(intent);

                        }
                    })

                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(WorkoutLog.this, "Workout Discarded", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(WorkoutLog.this, Menu.class);
                            startActivity(intent);
                        }
                    });

            adb.show();
        }
        else {
            Intent intent = new Intent(WorkoutLog.this, Menu.class);
            startActivity(intent);
        }
    }
}
