package com.example.jack.eee521_finalyearproject_b00708431_fitnesstracker;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Workout {

    private int workoutDate;
    private ArrayList<Exercise> exercises;
    private int totalExercises;
    private int totalReps;
    private int totalSets;

    public Workout(){

    }


    //TODO Sort of Workout date to epoch


    public Workout(int workoutDate){
        this.workoutDate = workoutDate;
        this.exercises = new ArrayList<Exercise>();
    }

    public void addExercise(Exercise input) {

        exercises.add(input);
    }

    public void removeExercise(Exercise input){
        for(int i=0; i<=exercises.size(); i++){
            if (exercises.get(i) == input) {
                exercises.remove(input);
            }
        }
    }

    public int getTotalExercises() {
        return totalExercises;
    }

    public void setTotalExercises() {

        int count = 0;
        for(int i =0; i <= exercises.size(); i++){
            count++;
        }

        this.totalExercises = count;
    }

    public int getTotalReps() {
        return totalReps;
    }

    public void setTotalReps() {
        int repNum = 0;

        for(int i = 0; i <= exercises.size(); i++){

            repNum += exercises.get(i).getNoOfReps();
        }

        this.totalReps = repNum;
    }

    public int getTotalSets() {
        return totalSets;
    }

    public void setTotalSets() {
        int setNum = 0;

        for(int i = 0; i <= exercises.size(); i++){

            setNum += exercises.get(i).getNoOfSets();
        }

        this.totalSets = setNum;
    }
}
