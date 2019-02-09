package com.example.jack.eee521_finalyearproject_b00708431_fitnesstracker;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CreateProfile extends AppCompatActivity {

    private Button createBtn;
    private EditText emailEditTxt, passwordEditTxt, usernameEditTxt, userHeightEditTxt, userWeightEditTxt, userDOBEditTxt;
    private String TAG = "";
    RadioButton maleRadBtn, femaleRadBtn ;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        //Getting an instance of firebase authorisation for adding a user to the database
        firebaseAuth = FirebaseAuth.getInstance();

        emailEditTxt = (EditText)findViewById(R.id.Create_Profile_email_editTxt);
        passwordEditTxt = (EditText)findViewById(R.id.Create_Profile_password_EditTxt);
        usernameEditTxt = (EditText)findViewById(R.id.Create_Profile_name_editTxt);
        userWeightEditTxt = (EditText)findViewById(R.id.Create_Profile_weight_editTxt);
        userHeightEditTxt = (EditText)findViewById(R.id.Create_Profile_height_editTxt);
        userDOBEditTxt = (EditText)findViewById(R.id.Create_Profile_dob_editTxt);
        maleRadBtn = findViewById(R.id.radBtn_Reg_Male);
        femaleRadBtn = findViewById(R.id.radBtn_Reg_Female);
        createBtn = (Button)findViewById(R.id.Start_Up_Create_Profile_btn);
    }

    public void Register_Accepted(View view) throws ParseException {

        //TODO date format not working.

        String userEmail = emailEditTxt.getText().toString();
        String userPassword = passwordEditTxt.getText().toString();
        String userName = usernameEditTxt.getText().toString();
        double userHeight = Double.parseDouble(userHeightEditTxt.getText().toString());
        double userWeight = Double.parseDouble(userWeightEditTxt.getText().toString());
        Date userDob = new SimpleDateFormat("dd/mm/yyyy").parse(((EditText)findViewById(R.id.Create_Profile_dob_editTxt)).getText().toString());
        String gender = "";

        //Validation(userEmail, userPassword, userName, userHeight, userWeight, userDob, gender);

        if(maleRadBtn.isChecked() == true && femaleRadBtn.isChecked() == false){
            gender = "Male";
        }
        else if (femaleRadBtn.isChecked() == true && maleRadBtn.isChecked() == true){
            gender = "Female";
        }

        if(Validation(userEmail, userPassword, userName, userHeight, userWeight, userDob, gender) == true){
            Map<String, Object> userMap =  new HashMap<>();
            userMap.put("Email", userEmail);
            userMap.put("Name", userName);
            userMap.put("DOB", userDob);
            userMap.put("Gender", gender);
            userMap.put("Height(cm)", userHeight);
            userMap.put("Weight(kg)", userWeight);

            firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(CreateProfile.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(CreateProfile.this, "Profile created successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(CreateProfile.this, StartUp.class));


                    }
                    else{
                        FirebaseAuthException e = (FirebaseAuthException) task.getException();
                        Toast.makeText(CreateProfile.this, "Failed to create profile. " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });


            // TODO Connect UID from Auth to Firestore -----------------------------------------------------------------------------------------------
            String userUid = firebaseAuth.getCurrentUser().getUid();


            db.collection("Users")
                    .add(userMap)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "Document added with ID: " + documentReference.getId());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w(TAG, "Error adding document");
                }
            });
        }
    }

    //intent to open Sign In Screen when user clicks on message
    public void SignIn(View view){
        Intent intent = new Intent(CreateProfile.this, StartUp.class);
        startActivity(intent);
    }

    public boolean Validation(String email, String password,String username, double height, double weight, Date dob, String gender){

        try {

            if(gender != "Male" || gender != "Female") {
                //setting the gender of the user
                Toast.makeText(CreateProfile.this, "Please select a gender", Toast.LENGTH_LONG).show();
                return false;
            }

            if(TextUtils.isEmpty(email)) {
                Toast.makeText(this, "Please enter Email", Toast.LENGTH_SHORT).show();
                return false;
            }
            else if(TextUtils.isEmpty(password)){
                Toast.makeText(this, "Please enter Password", Toast.LENGTH_SHORT).show();
                return false;
            }
            //checking all of the fields are filled out and valid
            if (username.equals("") || height <= 0 || weight <= 0 || dob.equals("")) {
                Toast.makeText(CreateProfile.this, "Please fill out all fields provided", Toast.LENGTH_LONG).show();
                return false;
            }
            else if(username.length() < 3 || username.length() > 12) {
                Toast.makeText(CreateProfile.this, "Username must be between 3 and 12 characters", Toast.LENGTH_LONG).show();
                return false;
            }
            else if(height < 100 || height > 250){
                Toast.makeText(CreateProfile.this, "Height must be between 100cm and 250cm", Toast.LENGTH_SHORT).show();
                return false;
            }
            else if(weight < 35 || weight > 200){
                Toast.makeText(CreateProfile.this, "Weight must be between 35kg and 200kg", Toast.LENGTH_SHORT).show();
                return false;
            }
            else {
                return true;
            }
        }
        catch(NullPointerException ex){
                Log.d(TAG, ex.getMessage());
        }
        return false;
    }

}
