package com.example.jack.eee521_finalyearproject_b00708431_fitnesstracker;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditUserDetails extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseFirestore db;
    User user;
    EditText usernameEditText, dobEditText, heightEditText, weightEditText;
    RadioButton maleRadBtn, femaleRadBtn;
    String userUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_details);

        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        user = new User();

        usernameEditText = findViewById(R.id.EditUserDetails_name_editTxt);
        dobEditText = findViewById(R.id.EditUserDetails_dobTxt);
        heightEditText = findViewById(R.id.EditUserDetails_height_editTxt);
        weightEditText = findViewById(R.id.EditUserDetails_weight_editTxt);
        maleRadBtn = findViewById(R.id.radBtn_Reg_Male);
        femaleRadBtn = findViewById(R.id.radBtn_Reg_Female);

        userUID = firebaseAuth.getCurrentUser().getUid();
        FillDetails(userUID);
    }


    public void FillDetails(String userUID){

        DocumentReference docRef = db.collection("Users").document(userUID);

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                user.setUserName(documentSnapshot.get("userName").toString());
                user.setUserDOB(documentSnapshot.get("userDOB").toString());
                user.setUserHeight(Double.parseDouble(documentSnapshot.get("userHeight").toString()));
                user.setUserWeight(Double.parseDouble(documentSnapshot.get("userWeight").toString()));
                user.setUserGender(documentSnapshot.get("userGender").toString());

                usernameEditText.setText(user.getUserName());
                dobEditText.setText(user.getUserDOB());
                heightEditText.setText(String.valueOf(user.getUserHeight()));
                weightEditText.setText(String.valueOf(user.getUserWeight()));

                switch(user.getUserGender()) {
                    case "Male":
                        maleRadBtn.setChecked(true);
                        femaleRadBtn.setChecked(false);
                        break;

                    case "Female":
                        femaleRadBtn.setChecked(true);
                        maleRadBtn.setChecked(false);
                        break;
                }
            }
        });
    }

    public void Cancel(View view){
        Intent intent = new Intent(EditUserDetails.this, UserStats.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(EditUserDetails.this, UserStats.class);
        startActivity(intent);
        finish();
    }

    public void SaveDetails(View view) {

        User newDetails = new User();
        newDetails.setUserName(usernameEditText.getText().toString());
        newDetails.setUserHeight(Double.parseDouble(heightEditText.getText().toString()));
        newDetails.setUserWeight(Double.parseDouble(weightEditText.getText().toString()));
        newDetails.setUserDOB(dobEditText.getText().toString());
        String gender = (maleRadBtn.isChecked()) ? "Male" : "Female";
        newDetails.setUserGender(gender);

        UpdateFirebase(newDetails);

        Toast.makeText(EditUserDetails.this, "Details updated", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(EditUserDetails.this, UserStats.class);
        startActivity(intent);
        finish();
    }

    public void UpdateFirebase(final User updateUser){

        final DocumentReference docRef = db.collection("Users").document(userUID);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();

                    if (document.exists()) {

                        if (user.getUserName() != updateUser.getUserName()) {
                            docRef.update("userName", updateUser.getUserName());
                        }
                        if (user.getUserHeight() != updateUser.getUserHeight()) {
                            docRef.update("userHeight", updateUser.getUserHeight());
                        }
                        if (user.getUserWeight() != updateUser.getUserWeight()) {
                            docRef.update("userWeight", updateUser.getUserWeight());
                        }
                        if (user.getUserDOB() != updateUser.getUserDOB()) {
                            docRef.update("userDOB", updateUser.getUserDOB());
                        }
                        if(user.getUserGender() != updateUser.getUserGender()){
                            docRef.update("userGender", updateUser.getUserGender());
                        }
                    }
                }
            }
        });
    }
}
