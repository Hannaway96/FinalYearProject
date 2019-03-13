package com.example.jack.eee521_finalyearproject_b00708431_fitnesstracker;

import org.junit.Test;

import static org.junit.Assert.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }


    @Test
    public void subtraction_isCorrect() {
        assertEquals(3, 4 - 1);
    }


    FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    public static final String USER_STRING = "RcS8FOlxiqdmZHqnwamvwgD63Ar1";


}