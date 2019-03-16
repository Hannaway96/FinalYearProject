package com.example.jack.eee521_finalyearproject_b00708431_fitnesstracker;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserStats extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseFirestore db;
    User user = new User();
    TextView nameTxtView, dobTxtView, heightTxtView, weightTxtView, genderTxtView,
                exercisesCompTxtView, workoutsCompTxtView, userBMITxtView, userHealthStatusTxtView;

    ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_stats);

        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

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

    public void FillDetails(String UserID){

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
                exercisesCompTxtView.setText(String.valueOf(user.getUserExercises_Completed())+"");
                workoutsCompTxtView.setText(String.valueOf(user.getUserWorkouts_Completed())+"");

                CalBMI();
            }
        });
    }

    public void CalBMI(){

        double weight = user.getUserWeight();
        double height = user.getUserHeight();
        height = height / 100;
        double userBMI = weight / (height * height);
        String bmiStr = String.format("%.2f", userBMI);

        if(userBMI <= 18.5){
            userBMITxtView.setTextColor(Color.parseColor("#FF0000"));
            userBMITxtView.setText(bmiStr);
            userHealthStatusTxtView.setText("Under Weight");
        }
        else if(userBMI > 18.5  && userBMI <= 24.9){
            userBMITxtView.setTextColor(Color.parseColor("#008000"));
            userBMITxtView.setText(bmiStr);
            userHealthStatusTxtView.setText("Healthy Weight");
        }
        else{
            userBMITxtView.setTextColor(Color.parseColor("#FF0000"));
            userBMITxtView.setText(bmiStr);
            userHealthStatusTxtView.setText("Over Weight");
        }
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
}
