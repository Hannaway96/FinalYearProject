package com.example.jack.eee521_finalyearproject_b00708431_fitnesstracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.view.View;

public class WorkoutPlans extends AppCompatActivity {

    ImageView chestImgView, backImgView, absImgView, legsImgView, armsImgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_plans);

        chestImgView = (ImageView)findViewById(R.id.WorkoutPlans_Chest_imgView);
        backImgView = (ImageView)findViewById(R.id.WorkoutPlans_Back_imgView);
        absImgView = (ImageView)findViewById(R.id.WorkoutPlans_Abs_imgView);
        legsImgView = (ImageView)findViewById(R.id.WorkoutPlans_Legs_imgView);
        armsImgView = (ImageView)findViewById(R.id.WorkoutPlans_Arms_imgView);

        //Round the edges of the image views
        chestImgView.setClipToOutline(true);
        backImgView.setClipToOutline(true);
        absImgView.setClipToOutline(true);
        legsImgView.setClipToOutline(true);
        armsImgView.setClipToOutline(true);
    }

    public void GoToSelectedPlan(View view){
        Intent intent = new Intent(WorkoutPlans.this, SelectedPlan.class);
        startActivity(intent);
        finish();
    }

    public void Return(View view){
        Intent intent = new Intent(WorkoutPlans.this, Menu.class);
        startActivity(intent);
        finish();
    }
}
