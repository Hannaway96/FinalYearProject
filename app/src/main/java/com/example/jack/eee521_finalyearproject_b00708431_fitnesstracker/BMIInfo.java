package com.example.jack.eee521_finalyearproject_b00708431_fitnesstracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class BMIInfo extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmiinfo);

        imageView = (ImageView)findViewById(R.id.BMI_ImageView);
        imageView.setClipToOutline(true);
    }

    public void Return(View view){
        Intent intent = new Intent(BMIInfo.this, UserStats.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(BMIInfo.this, UserStats.class);
        startActivity(intent);
        finish();
    }
}
