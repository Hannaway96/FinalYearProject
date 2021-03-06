package com.example.jack.eee521_finalyearproject_b00708431_fitnesstracker;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;

public class SelectedPlan extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    private TextView titleTxtView;
    private ListView workoutPlanList;
    private String[] exerciseNames;
    private ArrayList<Exercise> exerciseList = new ArrayList<>();
    private String planType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_plan);

        //Get workoutPlan type
        planType = getIntent().getStringExtra("serialized_type");

        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        workoutPlanList = (ListView) findViewById(R.id.SelectedPlan_ListView);
        titleTxtView = (TextView)findViewById(R.id.SelectedPlan_Title_TxtView);

        //switch to set the exercise name array's content
        switch (planType) {
            case "Chest":
                exerciseNames = getResources().getStringArray(R.array.ChestExercises);
                break;

            case "Back":
                exerciseNames = getResources().getStringArray(R.array.BackExercises);
                break;

            case "Abs":
                exerciseNames = getResources().getStringArray(R.array.AbExercises);
                break;

            case "Legs":
                exerciseNames = getResources().getStringArray(R.array.LegExercises);
                break;

            case "Arms":
                exerciseNames = getResources().getStringArray(R.array.ArmExercises);
                break;
        }

        //Set the title text to reflect the chosen workout plan
        titleTxtView.setText( planType + " Workout Plan");
        GetExercises();

        workoutPlanList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Exercise exercise = exerciseList.get(position);
                String exerciseName = exercise.getExerciseName();

                Intent intent = new Intent(SelectedPlan.this, ExerciseHelp.class);
                intent.putExtra("serializedExerciseName", exerciseName);
                startActivity(intent);
            }
        });
    }

    private void GetExercises(){
        //Converting the array of exercise names to a list so it can be shuffled
        List<String> exerciseNameList = Arrays.asList(exerciseNames);
        Collections.shuffle(exerciseNameList);

        for(int i = 0; i < 10; i++){

            Exercise exercise = new Exercise();

            //Ranges for random rep and set values
            Random r = new Random();
            int[] reps = new int[]{8, 10, 12};
            int randReps = r.nextInt(3 - 0) + 0;
            int randSets = r.nextInt(4 - 1) + 1;

            exercise.setExerciseName(exerciseNameList.get(i));
            exercise.setExerciseType(planType);
            exercise.setNoOfReps(reps[randReps]);
            exercise.setNoOfSets(randSets);

            exerciseList.add(exercise);
        }

        ExerciseListAdapter adapter = new ExerciseListAdapter(this, R.layout.adapter_view_layout, exerciseList);
        workoutPlanList.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    public void SaveWorkout(View view){

        //Creating workout
        final Workout workout = new Workout();
        final User user = new User();

        //get the current date and format it to a string
        //DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        //Date date = new Date();
        //String dateStr = dateFormat.format(date);

        //creates workout date
        workout.setWorkoutDateStr();

        //Add workout details to workout object
        for (int i = 0; i < exerciseList.size(); i++) {
            workout.addExercise(exerciseList.get(i));
        }

        String userUid = firebaseAuth.getCurrentUser().getUid();    //gets current user signed in
        workout.setUserUID(userUid);                                //Adds current users id to workout

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
        Intent intent = new Intent(SelectedPlan.this, Menu.class);
        startActivity(intent);
        finish();
    }

    public void Return(View view){
        Intent intent = new Intent(SelectedPlan.this, WorkoutPlans.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(SelectedPlan.this, WorkoutPlans.class);
        startActivity(intent);
        finish();
    }
}
