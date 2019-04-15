package com.example.jack.eee521_finalyearproject_b00708431_fitnesstracker;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class ExerciseHelp extends AppCompatActivity {

    FirebaseFirestore db;
    ImageView imageView;
    TextView exerciseNameTxtView, infoTextView;
    String exerciseName;
    String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_help);

        exerciseName = getIntent().getStringExtra("serializedExerciseName");

        db = FirebaseFirestore.getInstance();
        exerciseNameTxtView = (TextView)findViewById(R.id.ExerciseHelp_NameValTxtView);
        infoTextView = (TextView)findViewById(R.id.ExerciseHelp_InfoValTxtView);

        imageView = (ImageView)findViewById(R.id.ExerciseHelp_ExerciseImgView);
        imageView.setClipToOutline(true);

        PopulateExerciseInfo(exerciseName);
    }


    public void PopulateExerciseInfo(String exerciseName){

        //reference the document relating to the specified exercise
        DocumentReference docRef = db.collection("Exercises").document(exerciseName);

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                //Populate info on the activity relating to specified exercise
                infoTextView.setText(documentSnapshot.get("exercise_info").toString());
                imageUrl = documentSnapshot.get("image_url").toString();
                exerciseNameTxtView.setText(documentSnapshot.getId());

                //Execute method to retrieve bitmap from the link specified in exercise document
                new DownloadImageTask().execute(imageUrl);
            }
        });
    }

    //Async task to retrieve a bitmap from the internet
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        protected Bitmap doInBackground(String... urls) {
            return DownloadImage(urls[0]);
        }

        protected void onPostExecute(Bitmap result) {
            ImageView exerciseImgView = (ImageView)findViewById(R.id.ExerciseHelp_ExerciseImgView);
            exerciseImgView.setImageBitmap(result);
        }
    }

    //Method retrieves an image from an input stream via a http connection
    private Bitmap DownloadImage(String URL){
        Bitmap bitmap = null;
        InputStream in = null;
        try{
            in = OpenHttpConnection(URL);
            bitmap = BitmapFactory.decodeStream(in);
            in.close();
        }
        catch(IOException e1){
            Log.d("NetworkingActivity", e1.getLocalizedMessage());
        }
        return bitmap;
    }

    //Method opens a http connection with a specified url to retrieve data
    private InputStream OpenHttpConnection(String urlString) throws IOException {

        //declares an input stream object
        InputStream in = null;
        int response = -1;

        //declares a url object and then a url connection
        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();

        if(! (conn instanceof HttpURLConnection)) {
            throw new IOException("Not a HTTP connection!");
        }
        //tries to make a connection with the http server
        try{
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect();
            response = httpConn.getResponseCode();
            if(response == HttpURLConnection.HTTP_OK){
                in = httpConn.getInputStream();
            }
        }
        catch(Exception ex){
            Log.d("Networking", ex.getLocalizedMessage());
            throw new IOException("Error connecting");
        }
        return in;
    }

    //This uses the same method as the standard back button built into android to return to the activity the user came from
    public void Return(View view){
        onBackPressed();
    }
}
