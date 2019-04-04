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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CreateProfile extends AppCompatActivity {

    private Button createBtn;
    private EditText emailEditTxt, passwordEditTxt, usernameEditTxt, userHeightEditTxt, userWeightEditTxt, userDOBEditTxt;
    private String TAG = "MyApp";
    private RadioButton maleRadBtn, femaleRadBtn;
    private FirebaseFirestore db;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        //Getting an instance of firebase authorisation for adding a user to the database
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        emailEditTxt = (EditText) findViewById(R.id.Create_Profile_email_editTxt);
        passwordEditTxt = (EditText) findViewById(R.id.Create_Profile_password_EditTxt);
        usernameEditTxt = (EditText) findViewById(R.id.Create_Profile_name_editTxt);
        userWeightEditTxt = (EditText) findViewById(R.id.Create_Profile_weight_editTxt);
        userHeightEditTxt = (EditText) findViewById(R.id.Create_Profile_height_editTxt);
        userDOBEditTxt = (EditText) findViewById(R.id.Create_Profile_dobTxt);
        maleRadBtn = findViewById(R.id.radBtn_Reg_Male);
        femaleRadBtn = findViewById(R.id.radBtn_Reg_Female);
        createBtn = (Button) findViewById(R.id.Start_Up_Create_Profile_btn);
    }

    public void Register_Accepted(View view) {

        String userEmail = "", userPassword = "", userName = "", userDob = "", gender = "";
        double userHeight = 0.00, userWeight = 0.00;


        try {
            userEmail = emailEditTxt.getText().toString();
            userPassword = passwordEditTxt.getText().toString();
            userName = usernameEditTxt.getText().toString();
            userHeight = Double.parseDouble(userHeightEditTxt.getText().toString());
            userWeight = Double.parseDouble(userWeightEditTxt.getText().toString());
            userDob = userDOBEditTxt.getText().toString();
            gender = "";
        }
        catch(Exception e){}

        if (maleRadBtn.isChecked() == true) {
            gender = "Male";
        } else if (femaleRadBtn.isChecked() == true) {
            gender = "Female";
        }
        //Instantiate new User class and add values from Create profile into values.
        final User user = new User(userEmail, userName, userHeight, userWeight, userDob, gender);

        if(Validation(userEmail, userPassword, userName, userHeight, userWeight, userDob, gender) == true) {
            firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                    .addOnCompleteListener(CreateProfile.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            String userUid = firebaseAuth.getCurrentUser().getUid();                //Get the same user UID belonging to user auth
                            db.collection("Users").document(userUid).set(user);        //Add the user object to Firestore document using the authUID
                            Toast.makeText(CreateProfile.this, "Profile created successfully " + firebaseAuth.getCurrentUser().getUid(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(CreateProfile.this, StartUp.class);
                            startActivity(intent);

                        } else {
                            FirebaseAuthException e = (FirebaseAuthException) task.getException();
                            Toast.makeText(CreateProfile.this, "Failed to create profile. " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}

    //intent to open Sign In Screen when user clicks on message
    public void SignIn(View view){
        Intent intent = new Intent(CreateProfile.this, StartUp.class);
        startActivity(intent);
    }

    public boolean Validation(String email, String password,String username, double height, double weight, String dob, String gender) {

        try {
            //Null validation
            if (email.equals("") || password.equals("") || username.equals("") || height <= 0 || weight <= 0 || dob.equals("") || gender.equals("")) {
                Toast.makeText(CreateProfile.this, "Please fill out all fields provided", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (gender.equals("") || gender.equals("")) {
                //setting the gender of the user
                Toast.makeText(CreateProfile.this, "Please select a gender", Toast.LENGTH_SHORT).show();
                return false;
            }

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(this, "Please enter Email", Toast.LENGTH_SHORT).show();
                return false;
            } else if (TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Please enter Password", Toast.LENGTH_SHORT).show();
                return false;
            }


            //TODO look over date validation again using regular expressions
            //Date validation
            if(validDate(dob) == false){
                Toast.makeText(CreateProfile.this, "Please enter a valid date of birth (dd/mm/yyyy)", Toast.LENGTH_SHORT).show();
                return false;
            }

            //Boundary validation
            if (username.length() < 3 || username.length() > 12) {
                Toast.makeText(CreateProfile.this, "Username must be between 3 and 12 characters", Toast.LENGTH_SHORT).show();
                return false;
            } else if (height < 100 || height > 250) {
                Toast.makeText(CreateProfile.this, "Height must be between 100cm and 250cm", Toast.LENGTH_SHORT).show();
                return false;
            } else if (weight < 35 || weight > 150) {
                Toast.makeText(CreateProfile.this, "Weight must be between 35kg and 150kg", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                return true;
            }
        } catch (NullPointerException ex) {
            Log.d(TAG, ex.getMessage());
        }
        return true;
    }

    public static boolean validDate(String date){

        try{
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            format.setLenient(false);
            format.parse(date);
            return true;
        }catch(ParseException e){
            return false;
        }
    }
}
