package com.example.jack.eee521_finalyearproject_b00708431_fitnesstracker;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AddExercise extends AppCompatActivity{

    private String TAG = "MyApp";
    public String exerciseType = "";
    private Spinner typeSpinner, exerciseSpinner;
    private EditText repsEditText, setsEditText;
    ArrayList<Exercise> tempList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);

        //Retrieve Parceable List from WorkoutLog and equal it to a tempList so the exercise
        //is added to the current Exercise List then passed back to be displayed, where another exercise
        // will be added on top of it again.

        if(getIntent().getParcelableArrayListExtra("serialized_exerciseList") != null){
            tempList = getIntent().getParcelableArrayListExtra("serialized_exerciseList");
        }

        typeSpinner = (Spinner)findViewById(R.id.addExercise_Type_Spinner);
        exerciseSpinner = (Spinner)findViewById(R.id.addExercise_Exercise_Spinner);
        repsEditText = (EditText) findViewById(R.id.addExercise_Rep_PlainText);
        setsEditText = (EditText)findViewById(R.id.addExercise_Sets_PlainTxt);

        //Setting data adapter for type spinner
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this, R.array.ExerciseTypes, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);

        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                exerciseType = typeSpinner.getSelectedItem().toString();
                GetExercises(exerciseType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        exerciseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    public void GetExercises(String type){

        ArrayAdapter<CharSequence> exerciseAdapter = null;

        switch(type){
            case "Abs":
                exerciseAdapter =  ArrayAdapter.createFromResource(this, R.array.AbExercises, android.R.layout.simple_spinner_item);
                break;

            case "Arms":
                exerciseAdapter = ArrayAdapter.createFromResource(this, R.array.ArmExercises, android.R.layout.simple_spinner_item);
                break;

            case "Back":
                exerciseAdapter = ArrayAdapter.createFromResource(this, R.array.BackExercises, android.R.layout.simple_spinner_item);
                break;

            case "Chest":
                exerciseAdapter = ArrayAdapter.createFromResource(this, R.array.ChestExercises, android.R.layout.simple_spinner_item);
                break;

            case "Legs":
                exerciseAdapter = ArrayAdapter.createFromResource(this, R.array.LegExercises, android.R.layout.simple_spinner_item);
                break;

            default: exerciseAdapter = ArrayAdapter.createFromResource(this, R.array.LegExercises, android.R.layout.simple_spinner_item);
            break;
        }

        exerciseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        exerciseSpinner.setAdapter(exerciseAdapter);
    }

    public void AddExerciseToWorkout(View view){

        String exerciseType = typeSpinner.getSelectedItem().toString();
        String exerciseName = exerciseSpinner.getSelectedItem().toString();
        int noReps = Integer.parseInt(repsEditText.getText().toString());
        int noSets = Integer.parseInt(setsEditText.getText().toString());

        Exercise exercise = new Exercise(exerciseType, exerciseName, noReps, noSets);
        tempList.add(exercise);

        Toast.makeText(this, "Exercise Added to workout", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(AddExercise.this, WorkoutLog.class);
        intent.putExtra("newList", tempList);
        startActivity(intent);
    }

    public void Return(View view){
        Intent intent = new Intent( AddExercise.this, WorkoutLog.class);
        startActivity(intent);
        finish();
    }

}
