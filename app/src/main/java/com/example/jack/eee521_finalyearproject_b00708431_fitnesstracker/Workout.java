package com.example.jack.eee521_finalyearproject_b00708431_fitnesstracker;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class Workout implements Parcelable {

    private String workoutUID;
    private String workoutDateStr;
    ArrayList<Exercise> exercises =  new ArrayList<Exercise>();;
    private int totalExercises;
    private int totalReps;
    private int totalSets;
    private String userUID;

    public Workout(){

    }

    public Workout(String workoutUID, String workoutDate,ArrayList<Exercise> exercises, int totalExercises, int totalReps, int totalSets, String userUID){
        this.workoutUID = workoutUID;
        this.workoutDateStr = workoutDate;
        this.exercises = exercises;
        this.totalExercises = totalExercises;
        this.totalReps = totalReps;
        this.totalSets = totalSets;
        this.userUID = userUID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(this.workoutUID);
        dest.writeString(this.workoutDateStr);
        dest.writeTypedList(this.exercises);
        dest.writeInt(this.totalExercises);
        dest.writeInt(this.totalReps);
        dest.writeInt(this.totalSets);
        dest.writeString(this.userUID);
    }

    protected Workout(Parcel in){
        this.workoutUID = in.readString();
        this.workoutDateStr = in.readString();
        in.readTypedList(this.exercises, Exercise.CREATOR);
        this.totalExercises = in.readInt();
        this.totalReps = in.readInt();
        this.totalSets = in.readInt();
        this.userUID = in.readString();
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

        //Every time an exercise is added, the stats automatically update

        setTotalSets();
        setTotalReps();
        setTotalExercises();
    }

    public void setExercises(ArrayList<Exercise> list){
        this.exercises = list;
    }

    public ArrayList<Exercise> getExercises(){
        return exercises;
    }

    public void removeExercise(Exercise input){

        for(int i=exercises.size()-1; i>0; i--){
            if (exercises.get(i) == input) {
                exercises.remove(input);
                break;
            }
        }

        //Update totals
        setTotalExercises();
        setTotalReps();
        setTotalSets();
    }

    public String getWorkoutUID(){
        return workoutUID;
    }

    public void setWorkoutUID(String uid){
        this.workoutUID = uid;
    }

    public String getWorkoutDateStr(){
        return workoutDateStr;
    }

    public void setWorkoutDateStr(String dateStr){
        this.workoutDateStr = dateStr;
    }

    public void setWorkoutDateStr(){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        this.workoutDateStr = dateFormat.format(date);
    }

    public int getTotalExercises() {
        return totalExercises;
    }

    public void setTotalExercises(int totalExercises) {
        this.totalExercises = totalExercises;
    }

    private void setTotalExercises() {
        int count = 0;
        for(int i =0; i < exercises.size(); i++){
            count++;
        }
        this.totalExercises = count;
    }

    public int getTotalReps() {
        return totalReps;
    }

    public void setTotalReps(int totalReps) {
        this.totalReps = totalReps;
    }

    private void setTotalReps() {
        int repNum = 0;
        int timesRep;
        int tot = 0;

        for (int i = 0; i < exercises.size(); i++) {

            repNum = exercises.get(i).getNoOfReps();
            timesRep = repNum * exercises.get(i).getNoOfSets();
            tot += timesRep;
        }

        this.totalReps = tot;
    }

    public int getTotalSets() {
        return totalSets;
    }

    public void setTotalSets(int totalSets) {
        this.totalSets = totalSets;
    }

    private void setTotalSets() {
        int setNum = 0;

        for(int i = 0; i <exercises.size(); i++){
            setNum += exercises.get(i).getNoOfSets();
        }
        this.totalSets = setNum;
    }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }
}
