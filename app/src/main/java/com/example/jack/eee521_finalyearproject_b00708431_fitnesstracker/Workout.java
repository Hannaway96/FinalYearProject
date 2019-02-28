package com.example.jack.eee521_finalyearproject_b00708431_fitnesstracker;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Workout implements Parcelable {

    //TODO Sort of Workout date to epoch

    private int workoutDate;
    ArrayList<Exercise> exercises =  new ArrayList<Exercise>();;
    private int totalExercises;
    private int totalReps;
    private int totalSets;

    public Workout(){

    }

    public Workout( ArrayList<Exercise> exercises){
        this.exercises = exercises;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(this.workoutDate);
        dest.writeTypedList(this.exercises);
        dest.writeInt(this.totalExercises);
        dest.writeInt(this.totalReps);
        dest.writeInt(this.totalSets);
    }

    protected Workout(Parcel in){
        this.workoutDate = in.readInt();
        in.readTypedList(this.exercises, Exercise.CREATOR);
        this.totalExercises = in.readInt();
        this.totalReps = in.readInt();
        this.totalSets = in.readInt();
    }

    public static final Creator<Workout> CREATOR = new Creator<Workout>() {
        @Override
        public Workout createFromParcel(Parcel source) {
            return new Workout(source);
        }

        @Override
        public Workout[] newArray(int size) {
            return new Workout[size];
        }
    };

    public void addExercise(Exercise input) {

        exercises.add(input);
    }

    public ArrayList<Exercise> getExercises(){
        return exercises;
    }

    public void removeExercise(Exercise input){
        for(int i=0; i<=exercises.size()-1; i++){
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

        for(int i = 0; i <= exercises.size()-1; i++){

            repNum += exercises.get(i).getNoOfReps();
        }

        this.totalReps = repNum;
    }

    public int getTotalSets() {
        return totalSets;
    }

    public void setTotalSets() {
        int setNum = 0;

        for(int i = 0; i <= exercises.size()-1; i++){

            setNum += exercises.get(i).getNoOfSets();
        }

        this.totalSets = setNum;
    }


}
