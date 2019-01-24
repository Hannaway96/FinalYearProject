package com.example.jack.eee521_finalyearproject_b00708431_fitnesstracker;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Tag;


public class StartUp extends AppCompatActivity {

    final private int REQUEST_INTERNET = 123;
    private Button createProfileBtn;
    private Spinner profileNameSpinner;
    private String TAG = "MyApp";

    DatabaseReference dbUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        FirebaseApp.initializeApp(this);
        //CheckNetwork checks if the user is connected to the internet
        CheckNetwork();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);

        //Changing title of activity
        getSupportActionBar().setTitle("Workout Tracker");

        if(getIntent().getBooleanExtra("EXIT", false)){
            finish();
        }

        createProfileBtn = (Button)findViewById(R.id.Start_Up_Create_Profile_btn);
        profileNameSpinner = (Spinner)findViewById(R.id.Start_Up_UserSpinner);
        //dbUsers = FirebaseDatabase.getInstance().getReference("User/List");
    }

   public void CheckNetwork(){
       if (ContextCompat.checkSelfPermission(StartUp.this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
           ActivityCompat.requestPermissions(StartUp.this, new String[]{Manifest.permission.INTERNET}, REQUEST_INTERNET);
       }

       if (isOnline() == false) {

           AlertDialog.Builder adb = new AlertDialog.Builder(this);
           adb.setTitle("Check Network Connection")
                   .setMessage("Features within this app require internet access. Do you want to connect to the Internet?")
                   .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {

                           Intent turnWifiOn = new Intent(Settings.ACTION_WIFI_SETTINGS);
                           startActivity(turnWifiOn);
                       }
                   })

                   .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                           Intent intent = new Intent(getApplicationContext(), StartUp.class);
                           intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                           intent.putExtra("EXIT", true);
                           startActivity(intent);
                       }
                   });

           adb.show();
       }
   }

    private Boolean isOnline(){
        int returnVal = 0;
        boolean reachable = false;

        try {
            Process p1 = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.com");
            returnVal = p1.waitFor();
            reachable = (returnVal==0);
            return reachable;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void CreateProfile(View view){
        Intent intent = new Intent(StartUp.this, CreateProfile.class);
        startActivity(intent);
    }
}
