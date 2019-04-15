package com.example.jack.eee521_finalyearproject_b00708431_fitnesstracker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserStats extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    private User user;
    private TextView nameTxtView, dobTxtView, heightTxtView, weightTxtView, genderTxtView,
                exercisesCompTxtView, workoutsCompTxtView, userBMITxtView, userHealthStatusTxtView;

    ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_stats);

        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        user = new User();

        imgView = (ImageView)findViewById(R.id.PStats_ImgView);
        imgView.setClipToOutline(true);

        nameTxtView = (TextView)findViewById(R.id.PStats_UserName_Val_TxtView);
        dobTxtView = (TextView)findViewById(R.id.PStats_DOBVal_TxtView);
        heightTxtView = (TextView)findViewById(R.id.PStats_HeightVal_TxtView);
        weightTxtView = (TextView)findViewById(R.id.PStats_WeightVal_TxtView);
        genderTxtView = (TextView)findViewById(R.id.PStats_GenderVal_TxtView);
        exercisesCompTxtView = (TextView)findViewById(R.id.PStats_ExercisesCompVal_TxtView);
        workoutsCompTxtView = (TextView)findViewById(R.id.PStats_WorkoutsCompVal_TxtView);
        userBMITxtView = (TextView)findViewById(R.id.PStats_UsersBMIVal_TxtView);
        userHealthStatusTxtView = (TextView)findViewById(R.id.PStats_HealthStatusVal_TxtView);


        String userUID = firebaseAuth.getCurrentUser().getUid();
        FillDetails(userUID);
    }

    private void FillDetails(String UserID){

        DocumentReference docRef = db.collection("Users").document(UserID);

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                user.setUserName(documentSnapshot.get("userName").toString());
                user.setUserDOB(documentSnapshot.get("userDOB").toString());
                user.setUserHeight(Double.parseDouble(documentSnapshot.get("userHeight").toString()));
                user.setUserWeight(Double.parseDouble(documentSnapshot.get("userWeight").toString()));
                user.setUserGender(documentSnapshot.get("userGender").toString());
                user.setUserExercises_Completed(Integer.parseInt(documentSnapshot.get("userExercises_Completed").toString()));
                user.setUserWorkouts_Completed(Integer.parseInt(documentSnapshot.get("userWorkouts_Completed").toString()));

                nameTxtView.setText(user.getUserName()+"");
                dobTxtView.setText(user.getUserDOB()+"");
                heightTxtView.setText(String.valueOf(user.getUserHeight()));
                weightTxtView.setText(String.valueOf(user.getUserWeight()));
                genderTxtView.setText(user.getUserGender()+"");
                exercisesCompTxtView.setText(String.valueOf(user.getUserExercises_Completed()));
                workoutsCompTxtView.setText(String.valueOf(user.getUserWorkouts_Completed()));

                CalculateBMI();
            }
        });
    }

    private void CalculateBMI(){

        double weight = user.getUserWeight();
        double height = user.getUserHeight();
        height = height / 100;
        double userBMI = weight / (height * height);
        String bmiStr = String.format("%.2f", userBMI);

        if(userBMI <= 18.5){
            userBMITxtView.setText(bmiStr);
            userHealthStatusTxtView.setText("Under Weight");
            userHealthStatusTxtView.setTextColor(Color.parseColor("#FF0000"));
        }
        else if(userBMI > 18.5  && userBMI <= 24.9){
            userBMITxtView.setText(bmiStr);
            userHealthStatusTxtView.setText("Healthy Weight");
            userHealthStatusTxtView.setTextColor(Color.parseColor("#008000"));
        }
        else{
            userBMITxtView.setText(bmiStr);
            userHealthStatusTxtView.setText("Over Weight");
            userHealthStatusTxtView.setTextColor(Color.parseColor("#FF0000"));
        }
    }

    public void AlertUser(View view){
        AlertDialog.Builder adb = new AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Dialog_Alert);
        adb.setTitle("Delete Profile");
        adb.setMessage("All personal information will be deleted forever and you will be sent to the starting screen, are you sure you wish to delete your profile?");
        adb.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //function saves data and sends it to firebase
                DeleteUserFromFirebase();
            }
        });

        adb.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(UserStats.this, "Profile was not deleted", Toast.LENGTH_SHORT).show();
            }
        });

        adb.show();
    }

    private void DeleteUserFromFirebase(){

        //Create firebase user object
        final FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();

        //Remove the user profile from firestore using user.gerUID
        db.collection("Users").document(fbUser.getUid())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //Once the profile document has been deleted, then remove their login details.
                        DeleteLoginDetails(fbUser);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UserStats.this, "Error when deleting, " + e, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void DeleteLoginDetails(FirebaseUser user){

        //Remove user and their log in details from Fireabse Auth
        user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                //Alert the user has been removed and take them to the start up screen.
                Toast.makeText(UserStats.this, "Profile and credentials deleted successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UserStats.this, StartUp.class);
                startActivity(intent);
            }
        });
    }

    public void GoToEditDetails(View view){
        Intent intent = new Intent(UserStats.this, EditUserDetails.class);
        startActivity(intent);
        finish();
    }

    public void GoToBMIInfo(View view){
        Intent intent = new Intent(UserStats.this, BMIInfo.class);
        startActivity(intent);
        finish();
    }

    public void Return(View view){
        Intent intent = new Intent(UserStats.this, Menu.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(UserStats.this, Menu.class);
        startActivity(intent);
        finish();
    }
}
