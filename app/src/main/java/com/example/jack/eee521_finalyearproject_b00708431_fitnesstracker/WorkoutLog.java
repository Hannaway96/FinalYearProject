package com.example.jack.eee521_finalyearproject_b00708431_fitnesstracker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class WorkoutLog extends AppCompatActivity {

    private FirebaseFirestore db;
    private FirebaseAuth firebaseAuth;
    Button addExerciseBtn;
    Button saveWorkoutBtn;
    Button backButton;
    ListView workoutListView;
    ArrayList<Exercise> exerciseList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_log);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // Sets screen to portrait

        //Initialise firebase instances
        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        //Retrieve Parceable workout from AddExercise and equal it's exercises to this current exerciseList
        if(getIntent().getParcelableArrayListExtra("newList") != null){
            exerciseList = getIntent().getParcelableArrayListExtra("newList");
        }

        addExerciseBtn = (Button)findViewById(R.id.WorkoutLog_addExerciseBtn);
        saveWorkoutBtn = (Button)findViewById(R.id.workout_Log_SaveWorkoutBtn);
        backButton = (Button)findViewById(R.id.WorkoutLog_ReturnBtn);
        workoutListView = (ListView)findViewById(R.id.WorkoutListView);

        //Creating a custom list view adapter to display exercise details
        final ExerciseListAdapter adapter = new ExerciseListAdapter(this, R.layout.adapter_view_layout, exerciseList);
        workoutListView.setAdapter(adapter);

        //Allows the user to delete an item within the list view
        SwipeDismissListViewTouchListener touchListener = new SwipeDismissListViewTouchListener(workoutListView, new SwipeDismissListViewTouchListener.DismissCallbacks() {
            @Override
            public boolean canDismiss(int position) {
                return true;
            }

            @Override
            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                for(int position : reverseSortedPositions){
                    exerciseList.remove(position);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(WorkoutLog.this, "Exercise deleted", Toast.LENGTH_SHORT).show();
                }
            }
        });
        workoutListView.setOnTouchListener(touchListener);
    }

    public void AddExercise(View view){

        Intent intent = new Intent(WorkoutLog.this, AddExercise.class);

        if(exerciseList.size() > 0) {
            intent.putExtra("serialized_exerciseList", exerciseList);
        }
        startActivity(intent);
}

    public void SaveWorkout(View view){

        if(exerciseList.size() == 0){
            Toast.makeText(WorkoutLog.this, "No exercises entered!", Toast.LENGTH_SHORT).show();
        }
        else {

            //Creating workout
            final Workout workout = new Workout();
            final User user = new User();

            //get the current date and format it to a string
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date();
            String datestr = dateFormat.format(date);

            //creates workout date
            workout.setWorkoutDateStr(datestr);

            //Add workout details to workout object
            for (int i = 0; i < exerciseList.size(); i++) {
                workout.addExercise(exerciseList.get(i));
            }

            workout.setTotalExercises(0);    //counts total exercises
            workout.setTotalReps(0);         //totals reps
            workout.setTotalSets(0);         //totals sets
            String userUid = firebaseAuth.getCurrentUser().getUid();    //gets current user signed in
            workout.setUserUID(userUid);                                //Adds currentusers id to workout

            //Saving workout to firebase
            db.collection("Workouts").document().set(workout);

            //UPDATING USER STATS
            //Get the current user's document on firebase
            final DocumentReference docRef = db.collection("Users").document(userUid);

            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();

                        if (document.exists()) {

                            //set the user's details to user object
                            user.setUserName(document.get("userName").toString());
                            user.setUserHeight(Double.parseDouble(document.get("userHeight").toString()));
                            user.setUserWeight(Double.parseDouble(document.get("userWeight").toString()));
                            user.setUserDOB(document.get("userDOB").toString());
                            user.setUserGender(document.get("userGender").toString());
                            user.setUserExercises_Completed(Integer.parseInt(document.get("userExercises_Completed").toString()));
                            user.setUserWorkouts_Completed(Integer.parseInt(document.get("userWorkouts_Completed").toString()));

                            //Increasing users total workouts count
                            int currWorkoutCompleted = user.getUserWorkouts_Completed();
                            currWorkoutCompleted++;

                            //Increasing user's total exercises count
                            int currExercisesCompleted = user.getUserExercises_Completed();
                            currExercisesCompleted += workout.getTotalExercises();

                            user.setUserWorkouts_Completed(currWorkoutCompleted);
                            user.setUserExercises_Completed(currExercisesCompleted);

                        } else {
                            Log.d("MyApp", "DocumentSnapshot data: " + document.getData());
                        }

                        //Sending updated user statistics to firebase
                        docRef.update("userExercises_Completed", user.getUserExercises_Completed());
                        docRef.update("userWorkouts_Completed", user.getUserWorkouts_Completed());
                    }
                }
            });

            Toast.makeText(this, "Workout saved Successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(WorkoutLog.this, Menu.class);
            startActivity(intent);
            finish();
        }
    }

    public void GoToPastWorkouts(View view){
        Intent intent = new Intent(WorkoutLog.this, PastWorkouts.class);
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
