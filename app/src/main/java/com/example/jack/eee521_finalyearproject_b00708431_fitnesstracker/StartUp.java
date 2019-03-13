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
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;


public class StartUp extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    final private int REQUEST_INTERNET = 123;
    private Button createProfileBtn;
    private EditText usernameEditTxt, passwordEditTxt;
    private ImageView imageView;
    private List<String> userList = new ArrayList<String>();
    private String TAG = "MyApp";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);

        firebaseAuth = FirebaseAuth.getInstance();

        //Checks if the user wishes to close the application
        if(getIntent().getBooleanExtra("EXIT", false)){
            finish();
        }
        //Create an instance of Authorisation link to Firebase

        createProfileBtn = (Button)findViewById(R.id.Start_Up_Create_Profile_btn);
        usernameEditTxt = (EditText)findViewById(R.id.StartUp_EditText_Username);
        passwordEditTxt = (EditText)findViewById(R.id.StartUp_EditText_Password);
        imageView = (ImageView)findViewById(R.id.PStats_ImgView);


        imageView.setClipToOutline(true);

        //CheckNetwork checks if the user is connected to the internet
        CheckNetwork();

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

        //Try to ping google to see if the user has an internet connection
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


    public void LogIn(View view) {

        String email = ((EditText) findViewById(R.id.StartUp_EditText_Username)).getText().toString();
        String password = ((EditText) findViewById(R.id.StartUp_EditText_Password)).getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter Password", Toast.LENGTH_SHORT).show();
        }

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(StartUp.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(getApplicationContext(), Menu.class));
                } else {
                    Toast.makeText(StartUp.this, "Login Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void CreateProfile(View view){

        //Opens CreateProfile Activity
        Intent intent = new Intent(StartUp.this, CreateProfile.class);
        startActivity(intent);
    }
}
