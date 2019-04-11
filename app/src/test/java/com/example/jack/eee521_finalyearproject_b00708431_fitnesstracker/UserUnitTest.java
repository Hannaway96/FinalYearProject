package com.example.jack.eee521_finalyearproject_b00708431_fitnesstracker;

import org.junit.Test;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

public class UserUnitTest {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static final Pattern VALID_DATE_OF_BIRTH_FORMAT = Pattern.compile("^(([1-2][0-9])|([1-9])|(3[0-1]))(\\/)((1[0-2])|([1-9]))(\\/)[0-9]{4}$");


    @Test
    public void testUser_hasDetails(){
        User testUser = CreateUser();
        assertNotNull(testUser);
    }

    @Test
    public void testUser_ExercisesIncrease(){
        User testUser =  CreateUser();
        testUser.setUserExercises_Completed(testUser.getUserExercises_Completed() + 39);
        assertEquals(139, testUser.getUserExercises_Completed());
    }

    @Test
    public void testUser_WorkoutsIncrease(){
        User testUser =  CreateUser();
        testUser.setUserWorkouts_Completed(testUser.getUserWorkouts_Completed() + 3);
        assertEquals(8, testUser.getUserWorkouts_Completed());
    }

    @Test
    public void testUser_EmailValid(){
        User testUser = CreateUser();
        assertTrue(validateEmail(testUser.getUserEmail()));
    }

    @Test
    public void testUser_DOBFormatIsValid(){
        User testUser = CreateUser();
        testUser.setUserDOB("30_4_2017");
        assertFalse(validateDateFormat(testUser.getUserDOB()));
    }

    @Test
    public void testUser_DOBIsValid(){
        User testUser = CreateUser();
        testUser.setUserDOB("30/03/2010");
        assertTrue(validDate(testUser.getUserDOB()));
    }

    public static boolean validateEmail(String emailStr){
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    public static boolean validateDateFormat(String date){
        Matcher matcher = VALID_DATE_OF_BIRTH_FORMAT.matcher(date);
        return matcher.find();
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

    public User CreateUser(){
        String email = "jack@mail.co.uk", name = "Jack", userDob = "20/07/1996", gender = "Male";
        Double height = 178.0, weight = 78.0;

        User testUser = new User(email, name, height, weight, userDob, gender);

        testUser.setUserExercises_Completed(100);
        testUser.setUserWorkouts_Completed(5);
        return testUser;
    }
}