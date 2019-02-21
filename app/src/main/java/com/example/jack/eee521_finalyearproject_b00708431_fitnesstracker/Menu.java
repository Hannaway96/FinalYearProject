package com.example.jack.eee521_finalyearproject_b00708431_fitnesstracker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Menu extends AppCompatActivity {

    CardView workoutLogCardView, workoutGenCardView, exitCardView;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        firebaseAuth = FirebaseAuth.getInstance();                      //BE WARY OF THIS
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        //Checks if the user has requested that the program should exit
        if(getIntent().getBooleanExtra("EXIT", false)){
            finish();
        }

        //Declaring resources used within the XML
        workoutLogCardView = (CardView)findViewById(R.id.Menu_WorkoutLog_card_view);
        workoutGenCardView = (CardView)findViewById(R.id.Menu_WorkoutGen_card_view);
        exitCardView = (CardView)findViewById(R.id.Menu_Exit_CardView);
    }



    //This changes activities from the current activity to the workout log activity
    public void GoToWorkoutLog(View view){
        Intent intent = new Intent(Menu.this, WorkoutLog.class);
        startActivity(intent);
        finish();
    }

    //This changes activities from the current activity to the workout generator activity
    public void GoToWorkoutGen(View view){
        Intent intent = new Intent(Menu.this, WorkoutGenerator.class);
        startActivity(intent);
        finish();
    }

    //Ths changes activities but runs the current activity again this time with an exit flag requesting the activity/app closes
    public void Exit(View view){
        firebaseAuth.signOut();
        Intent intent = new Intent(Menu.this, Menu.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("EXIT", true);
        startActivity(intent);
    }
}
