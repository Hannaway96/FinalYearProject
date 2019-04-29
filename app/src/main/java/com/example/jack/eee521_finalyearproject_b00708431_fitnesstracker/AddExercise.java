package com.example.jack.eee521_finalyearproject_b00708431_fitnesstracker;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class AddExercise extends AppCompatActivity{

    private String TAG = "MyApp";
    private String exerciseType = "";
    private Spinner typeSpinner, exerciseSpinner;
    private EditText repsEditText, setsEditText;
    private ArrayList<Exercise> tempList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);

        //Retrieve Parceable List from Workout Log and equal it to a tempList so the exercise
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
                ((TextView) parent.getChildAt(0)).setTextColor(Color.rgb(192, 192, 192));
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
                ((TextView) parent.getChildAt(0)).setTextColor(Color.rgb(192, 192, 192));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void GoToExerciseInfo(View view){
        String exerciseName = exerciseSpinner.getSelectedItem().toString();
        Intent intent = new Intent(AddExercise.this, ExerciseHelp.class);
        intent.putExtra("serializedExerciseName", exerciseName);
        startActivity(intent);
        finish();
    }

    private void GetExercises(String type){
        //Sets the content of the exercise spinner based on the type

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

            default:
                exerciseAdapter = ArrayAdapter.createFromResource(this, R.array.AbExercises, android.R.layout.simple_spinner_item);
            break;
        }

        //Set the array adapter to the spinner
        exerciseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        exerciseSpinner.setAdapter(exerciseAdapter);
    }

    public void AddExerciseToWorkout(View view){

        String exerciseType = typeSpinner.getSelectedItem().toString();
        String exerciseName = exerciseSpinner.getSelectedItem().toString();
        int noReps=0;
        int noSets=0;

        //ensure data is valid
        if(TextUtils.isEmpty(repsEditText.getText())){
            Toast.makeText(this, "Enter # of Reps", Toast.LENGTH_SHORT).show();
        }
        else{
            noReps = Integer.parseInt(repsEditText.getText().toString());
        }
        if(TextUtils.isEmpty(setsEditText.getText())) {
            Toast.makeText(this, "Enter # of Sets", Toast.LENGTH_SHORT).show();
        }
        else{
            noSets = Integer.parseInt(setsEditText.getText().toString());
        }

        //Create exercise object and add it to the temp list
        if(exerciseName != null && exerciseType != null && noReps>0 && noSets>0){
            Exercise exercise = new Exercise(exerciseType, exerciseName, noReps, noSets);
            tempList.add(exercise);

            //Pass the new exercise list to the workout log again
            Toast.makeText(this, "Exercise Added to workout", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(AddExercise.this, WorkoutLog.class);
            intent.putExtra("newList", tempList);
            startActivity(intent);
        }
    }

    public void Return(View view){
        Intent intent = new Intent( AddExercise.this, WorkoutLog.class);
        intent.putExtra("newList", tempList);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent( AddExercise.this, WorkoutLog.class);
        intent.putExtra("newList", tempList);
        startActivity(intent);
        finish();
    }
}
