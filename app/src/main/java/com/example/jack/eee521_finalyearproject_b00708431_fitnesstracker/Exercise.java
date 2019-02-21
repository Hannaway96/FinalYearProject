package com.example.jack.eee521_finalyearproject_b00708431_fitnesstracker;

import java.io.Serializable;

public class Exercise implements Serializable {

    enum ExerciseType {Abs, Arms, Back, Chest, Legs}

    private String exerciseType;
    private String exerciseName;
    private int noOfReps;
    private int noOfSets;


    public Exercise(){

    }

    public Exercise(String exerciseType, String exerciseName, int noOfReps, int noOfSets){
        this.exerciseType = exerciseType;
        this.exerciseName = exerciseName;
        this.noOfReps = noOfReps;
        this.noOfSets = noOfSets;
    }

    public String getExerciseType(){
        return exerciseType;
    }

    public void setExerciseType(String exerciseType){
        this.exerciseType = exerciseType;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public int getNoOfReps() {
        return noOfReps;
    }

    public void setNoOfReps(int noOfReps) {
        this.noOfReps = noOfReps;
    }

    public int getNoOfSets() {
        return noOfSets;
    }

    public void setNoOfSets(int noOfSets) {
        this.noOfSets = noOfSets;
    }
}
