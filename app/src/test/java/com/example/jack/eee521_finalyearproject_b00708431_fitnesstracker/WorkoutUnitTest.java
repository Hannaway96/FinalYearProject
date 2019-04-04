package com.example.jack.eee521_finalyearproject_b00708431_fitnesstracker;

import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

public class WorkoutUnitTest {

    public static final Pattern VALID_DATE_OF_BIRTH_FORMAT = Pattern.compile("^([0-2][0-9]|(3)[0-1])((\\/)|(.))(((0)[0-9])|((1)[0-2]))((\\/)|(.))\\d{4}$");

    @Test
    public void testWorkout_TotalsCorrect(){
        Workout workout = CreateWorkout();
        assertEquals(6, workout.getTotalExercises());
    }

    @Test
    public void testWorkout_TotalReps(){
        Workout workout = CreateWorkout();
        assertEquals(300, workout.getTotalReps());
    }

    @Test
    public void testWorkout_TotalSets(){
        Workout workout = CreateWorkout();
        assertEquals(30, workout.getTotalSets());
    }

    @Test
    public void testWorkout_TotalsIncrement() {
        Workout workout = CreateWorkout();
        Exercise exercise = new Exercise("Back", "Decline Row", 20, 10);
        workout.addExercise(exercise);
        assertEquals(500, workout.getTotalReps());
    }

    @Test
    public void testWorkout_TotalsDecrement(){
        Workout workout = CreateWorkout();

        Exercise exercise = new Exercise("Back", "Decline Row", 20, 10);
        workout.addExercise(exercise);
        assertEquals(7, workout.getTotalExercises());
        assertEquals(500, workout.getTotalReps());

        workout.removeExercise(exercise);
        assertEquals(6, workout.getTotalExercises());
        assertEquals(300, workout.getTotalReps());
    }

    @Test
    public void testWorkout_ValidDate(){

        Workout testWorkout = CreateWorkout();
        assertTrue(validDate(testWorkout.getWorkoutDateStr()));

        testWorkout.setWorkoutDateStr("32/3/2019");
        assertFalse(validDate(testWorkout.getWorkoutDateStr()));
    }

    public Workout CreateWorkout(){
        Exercise exercise1 = new Exercise("Chest", "Bench Press", 10, 5);
        Exercise exercise2 = new Exercise("Back", "Row", 10, 5);
        Exercise exercise3 = new Exercise("Chest", "Decline Press", 10, 5);
        Exercise exercise4 = new Exercise("Legs", "Squat", 10, 5);
        Exercise exercise5 = new Exercise("Abs", "Crunch", 10, 5);
        Exercise exercise6 = new Exercise("Arms", "Dip", 10, 5);

        Workout workout = new Workout();
        workout.setWorkoutUID("RANDOM123");
        workout.setWorkoutDateStr("15/5/2019");

        workout.addExercise(exercise1);
        workout.addExercise(exercise2);
        workout.addExercise(exercise3);
        workout.addExercise(exercise4);
        workout.addExercise(exercise5);
        workout.addExercise(exercise6);

        return workout;
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