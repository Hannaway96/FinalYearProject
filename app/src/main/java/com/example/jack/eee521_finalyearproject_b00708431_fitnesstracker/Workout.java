package com.example.jack.eee521_finalyearproject_b00708431_fitnesstracker;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Workout {

    private int workoutDate;
    private Exercise[] exercises;
    private int totalExercises;
    private int totalReps;
    private int totalSets;

    public Workout(){

    }

    public Workout(int exerciseDate){
        this.workoutDate = exerciseDate;
        this.exercises = new Exercise[25];
    }

    public void addExercise(Exercise input) {

        for(int i = 0; i <= exercises.length -1; i++){
            if(exercises[i] == null){
                exercises[i] = input;
                break;
            }
        }
    }

    public void removeExercise(Exercise input){

        for(int i = 0; i <= exercises.length -1; i++){
            if(exercises[i].getExerciseName() == input.getExerciseName()){
                exercises[i] = null;
            }
        }
    }

    public int getTotalExercises() {
        return totalExercises;
    }

    public void setTotalExercises() {

        int count = 0;
        for(int i =0; i <= exercises.length -1; i++){
            count++;
        }

        this.totalExercises = count;
    }

    public int getTotalReps() {
        return totalReps;
    }

    public void setTotalReps() {
        int repNum = 0;

        for(int i = 0; i <= exercises.length-1; i++){

            repNum += exercises[i].getNoOfReps();
        }

        this.totalReps = repNum;
    }

    public int getTotalSets() {
        return totalSets;
    }

    public void setTotalSets() {
        int setNum = 0;

        for(int i = 0; i <= exercises.length -1; i++){

            setNum += exercises[i].getNoOfSets();
        }

        this.totalSets = setNum;
    }
}
