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

import java.util.ArrayList;
import java.util.List;

public class AddExercise extends AppCompatActivity implements AdapterView.OnItemSelectedListener{


    FirebaseFirestore exerciseDB;
    DocumentReference docRef;
    private String TAG = "MyApp";
    private Spinner typeSpinner, exerciseSpinner;
    private EditText repsEditText, setsEditText;

    private List<String> exerciseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);

        exerciseDB = FirebaseFirestore.getInstance();
        docRef = exerciseDB.collection("Exercises").document("Abs");
        exerciseList = new ArrayList<String>();

        typeSpinner = (Spinner)findViewById(R.id.addExercise_Type_Spinner);
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this, R.array.ExerciseTypes, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);

        //typeSpinner.setOnItemClickListener(this, );
        exerciseSpinner = (Spinner)findViewById(R.id.addExercise_Exercise_Spinner);
        repsEditText = (EditText) findViewById(R.id.addExercise_Rep_PlainText);
        setsEditText = (EditText)findViewById(R.id.addExercise_Sets_PlainTxt);

        getExercises();
        exerciseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                exerciseSpinner.getSelectedItem();
                Log.d(TAG, exerciseSpinner.getSelectedItem().toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    public void AddExerciseToWorkout(View view){

        String exerciseName = exerciseSpinner.getSelectedItem().toString();
        String exerciseType = typeSpinner.getSelectedItem().toString();
        int noReps = Integer.parseInt(repsEditText.getText().toString());
        int noSets = Integer.parseInt(setsEditText.getText().toString());

        Exercise exercise = new Exercise(exerciseName, exerciseType, noReps, noSets);

        Toast.makeText(this, "Exercise Added to workout", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(AddExercise.this, WorkoutLog.class);
        intent.putExtra("serialized_data", exercise);
        startActivity(intent);
    }

    public void getTypes(){
        //TODO functionality for Exercise and type spinners
    }

    public void getExercises() {

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());

                        for(int i = 0; i < document.getData().size()-1; i++){

                            exerciseList.add(document.get(i + "").toString());
                        }
                    } else {
                       Log.d(TAG, "No such document");
                    }
              } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, exerciseList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        exerciseSpinner.setAdapter(dataAdapter);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
