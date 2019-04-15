package com.example.jack.eee521_finalyearproject_b00708431_fitnesstracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.widget.ImageView;
import android.view.View;

public class WorkoutPlans extends AppCompatActivity {

    ImageView chestImgView, backImgView, absImgView, legsImgView, armsImgView;
    CardView chestCardView, backCardView, absCardView, legsCardView, armsCardView;
    String planType = "";

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

        chestCardView = (CardView)findViewById(R.id.WorkoutPlans_Chest_card_view);
        backCardView = (CardView)findViewById(R.id.WorkoutPlans_Back_card_view);
        absCardView = (CardView)findViewById(R.id.WorkoutPlans_Abs_card_view);
        legsCardView = (CardView)findViewById(R.id.WorkoutPlans_Legs_card_view);
        armsCardView = (CardView)findViewById(R.id.WorkoutPlans_Arms_card_view);


        chestCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                planType = "Chest";
                GoToSelectedPlan(planType);
            }
        });

        backCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                planType = "Back";
                GoToSelectedPlan(planType);
            }
        });

        absCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                planType = "Abs";
                GoToSelectedPlan(planType);
            }
        });

        legsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                planType = "Legs";
                GoToSelectedPlan(planType);
            }
        });

        armsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                planType = "Arms";
                GoToSelectedPlan(planType);
            }
        });

    }

    public void GoToSelectedPlan(String type){
        Intent intent = new Intent(WorkoutPlans.this, SelectedPlan.class);
        intent.putExtra("serialized_type", type);
        startActivity(intent);
        finish();
    }

    public void Return(View view){
        Intent intent = new Intent(WorkoutPlans.this, Menu.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(WorkoutPlans.this, Menu.class);
        startActivity(intent);
        finish();
    }
}
