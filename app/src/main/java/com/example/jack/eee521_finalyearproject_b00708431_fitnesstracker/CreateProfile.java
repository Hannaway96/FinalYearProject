package com.example.jack.eee521_finalyearproject_b00708431_fitnesstracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateProfile extends AppCompatActivity {

    private Button createBtn;
    RadioButton maleRadBtn, femaleRadBtn ;
    //FirebaseDatabase dbUsers = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        //Changing title of activity
        getSupportActionBar().setTitle("Create Profile");
        maleRadBtn = findViewById(R.id.radBtn_Reg_Male);
        femaleRadBtn = findViewById(R.id.radBtn_Reg_Female);
        createBtn = (Button)findViewById(R.id.Start_Up_Create_Profile_btn);
    }

    public void Register_Accepted(View view) throws ParseException {

        String userName = ((EditText)findViewById(R.id.Create_Profile_name_editTxt)).getText().toString();
        double userHeight = Double.parseDouble(((EditText)findViewById(R.id.Create_Profile_height_editTxt)).getText().toString());
        double userWeight = Double.parseDouble(((EditText)findViewById(R.id.Create_Profile_weight_editTxt)).getText().toString());
        Date userDob = new SimpleDateFormat("dd/mm/yyyy").parse(((EditText)findViewById(R.id.Create_Profile_dob_editTxt)).getText().toString());
        String gender = "";

        Validation(userName, userHeight, userWeight, userDob, gender);

        if(Validation(userName, userHeight, userWeight, userDob, gender) == true){

            //String id = dbUsers.push().getKey();
            //User user = new User(userName, userHeight, userWeight, userDob, gender);
            //DatabaseReference dbRef = dbUsers.getReference();
            //dbRef.setValue(user);
            //dbUsers.child(id).setValue(user);

            Toast.makeText(CreateProfile.this, "Your profile has be created successfully!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(CreateProfile.this, StartUp.class);
            startActivity(intent);
        }
    }

    //intent to open Sign In Screen when user clicks on message
    public void SignIn(View view){
        Intent intent = new Intent(CreateProfile.this, StartUp.class);
        startActivity(intent);
    }

    public boolean Validation(String username, double height, double weight, Date dob, String gender){

        try {

            //setting the gender of the user
            if(maleRadBtn.isChecked()==true && femaleRadBtn.isChecked() == false){
                gender = "Male";
            }
            else if (maleRadBtn.isChecked() == false && femaleRadBtn.isChecked()==true){
                gender = "Female";
            }
            else if(maleRadBtn.isChecked()==true && femaleRadBtn.isChecked()==true){
                Toast.makeText(CreateProfile.this, "You can't choose both genders", Toast.LENGTH_LONG).show();
                return false;
            }

            //checking all of the fields are filled out
            if (username.equals("") || height <= 0 || weight <= 0 || dob.equals("")) {
                Toast.makeText(CreateProfile.this, "Please fill out all fields provided", Toast.LENGTH_LONG).show();
                return false;
            }

            if (username.length() < 3 || username.length() > 12) {
                Toast.makeText(CreateProfile.this, "Username must be between 3 and 12 characters", Toast.LENGTH_LONG).show();
                return false;
            }

            if(height < 100 || height > 250){
                Toast.makeText(CreateProfile.this, "Height must be between 100cm and 250cm", Toast.LENGTH_SHORT).show();
                return false;
            }

            if(weight < 35 || weight > 200){
                Toast.makeText(CreateProfile.this, "Weight must be between 35kg and 200kg", Toast.LENGTH_SHORT).show();
                return false;
            }

            else {
                return true;
            }
        }
        catch(NullPointerException ex){

        }
        return false;
    }

}
