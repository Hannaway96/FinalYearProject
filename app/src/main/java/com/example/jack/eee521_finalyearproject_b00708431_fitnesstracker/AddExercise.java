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


    FirebaseFirestore exerciseDB;
    DocumentReference docRef;
    private String TAG = "MyApp";
    public String exerciseType = "";
    private Spinner typeSpinner, exerciseSpinner;
    private EditText repsEditText, setsEditText;

    private List<String> exerciseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);

        exerciseDB = FirebaseFirestore.getInstance();
        exerciseList = new ArrayList<String>();

        typeSpinner = (Spinner)findViewById(R.id.addExercise_Type_Spinner);
        exerciseSpinner = (Spinner)findViewById(R.id.addExercise_Exercise_Spinner);
        repsEditText = (EditText) findViewById(R.id.addExercise_Rep_PlainText);
        setsEditText = (EditText)findViewById(R.id.addExercise_Sets_PlainTxt);

        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this, R.array.ExerciseTypes, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);
        typeAdapter.notifyDataSetChanged();

        exerciseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

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

    }


    public void AddExerciseToWorkout(View view){

        String exerciseType = typeSpinner.getSelectedItem().toString();
        String exerciseName = exerciseSpinner.getSelectedItem().toString();
        int noReps = Integer.parseInt(repsEditText.getText().toString());
        int noSets = Integer.parseInt(setsEditText.getText().toString());

        Exercise exercise = new Exercise(exerciseType, exerciseName, noReps, noSets);

        Toast.makeText(this, "Exercise Added to workout", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(AddExercise.this, WorkoutLog.class);
        intent.putExtra("serialized_data", exercise);
        startActivity(intent);
    }

    public void GetExercises(String exerciseType) {

        exerciseList = new ArrayList<String>(); //Equal exerciseList as a new List so that the list doesn't append on to the end
        docRef = exerciseDB.collection("Exercises").document(exerciseType);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        //If the document exists then populate the exercise list with all of the exercises from that document
                        for(int i = 0; i < document.getData().size()-1; i++){
                            exerciseList.add(document.get(i+"").toString());
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
        dataAdapter.notifyDataSetChanged();

        //TODO Have exercise stay on spinner after it has been selected.
    }

    //TODO have some validation in this as well!!!
}
