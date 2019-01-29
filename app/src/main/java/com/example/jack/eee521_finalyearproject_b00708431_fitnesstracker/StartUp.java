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
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class StartUp extends AppCompatActivity {

    final private int REQUEST_INTERNET = 123;
    private Button createProfileBtn;
    private Spinner profileNameSpinner;
    private List<String> userList = new ArrayList<String>();
    private String TAG = "MyApp";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);

        //Changing title of activity
        getSupportActionBar().setTitle("Workout Tracker");


        //Checks if the user wishes to close the application
        if(getIntent().getBooleanExtra("EXIT", false)){
            finish();
        }

        //CheckNetwork checks if the user is connected to the internet
        CheckNetwork();

        createProfileBtn = (Button)findViewById(R.id.Start_Up_Create_Profile_btn);
        profileNameSpinner = (Spinner)findViewById(R.id.Start_Up_UserSpinner);

        PopulateSpinner();
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


    public void PopulateSpinner(){

        //TODO Fix PopulateSpinner, currently skips over Query Snapshot as it's not getting the document for some reason.

        int count = 0;
        String countAsString = Integer.toString(count);
        final ArrayList<String> users = new ArrayList<>();

        db.collection("Users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful()){
                    for (DocumentSnapshot document: task.getResult()){

                        User user = document.toObject(User.class);
                        users.add(user.getUserName());
                    }
                }
            }
        });

    //   do{
          //  DocumentReference docRef = db.collection("Users").document(countAsString);
           // docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
              //  @Override
              //  public void onSuccess(DocumentSnapshot documentSnapshot) {

                //    if(documentSnapshot.exists()){
             //
                //    }
           //     }
          //  });


        //}while(count);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, userList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        profileNameSpinner.setAdapter(dataAdapter);
    }



    public void CreateProfile(View view){
        Intent intent = new Intent(StartUp.this, CreateProfile.class);
        startActivity(intent);
    }
}
