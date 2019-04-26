package com.example.jack.eee521_finalyearproject_b00708431_fitnesstracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Menu extends AppCompatActivity {

    private ImageView workoutLogImg, workoutGenImg, userStatsImg, exitImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //Checks if the user has requested that the program should exit
        if(getIntent().getBooleanExtra("EXIT", false)){
            finish();
        }

        //Declaring resources used within the XML
        workoutLogImg = (ImageView)findViewById(R.id.Menu_WorkoutLog_imgview);
        workoutGenImg = (ImageView)findViewById(R.id.Menu_WorkoutPlans_imgview);
        userStatsImg = (ImageView)findViewById(R.id.Menu_UserStats_imgview);
        exitImg = (ImageView)findViewById(R.id.Menu_Exit_ImgView);

        //Have to manually set the image views to round their corners
        workoutLogImg.setClipToOutline(true);
        workoutGenImg.setClipToOutline(true);
        userStatsImg.setClipToOutline(true);
        exitImg.setClipToOutline(true);
    }


    //This changes activities from the current activity to the workout log activity
    public void GoToWorkoutLog(View view){
        Intent intent = new Intent(Menu.this, WorkoutLog.class);
        startActivity(intent);
        finish();
    }

    //This changes activities from the current activity to the workout generator activity
    public void GoToWorkoutPlans(View view){
        Intent intent = new Intent(Menu.this, WorkoutPlans.class);
        startActivity(intent);
        finish();
    }

    public void GoToUserStats(View view){
        Intent intent = new Intent(Menu.this, UserStats.class);
        startActivity(intent);
        finish();
    }

    //Ths changes activities but runs the current activity again this time with an exit flag requesting the activity/app closes
    public void Exit(View view){
        Intent intent = new Intent(Menu.this, Menu.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("EXIT", true);
        startActivity(intent);
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(Menu.this, Menu.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("EXIT", true);
        startActivity(intent);
    }
}
