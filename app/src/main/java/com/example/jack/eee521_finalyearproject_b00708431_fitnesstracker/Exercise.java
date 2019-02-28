package com.example.jack.eee521_finalyearproject_b00708431_fitnesstracker;


import android.os.Parcel;
import android.os.Parcelable;

public class Exercise implements Parcelable {

    //enum ExerciseType {Abs, Arms, Back, Chest, Legs}

    private String exerciseType;
    private String exerciseName;
    private int noOfReps;
    private int noOfSets;


    public Exercise(){

    }

    //Standard Constructor
    public Exercise(String exerciseType, String exerciseName, int noOfReps, int noOfSets){
        this.exerciseType = exerciseType;
        this.exerciseName = exerciseName;
        this.noOfReps = noOfReps;
        this.noOfSets = noOfSets;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    //Writes the current Class to parcel
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.exerciseType);
        dest.writeString(this.exerciseName);
        dest.writeInt(this.noOfReps);
        dest.writeInt(this.noOfSets);
    }

    //Reads from parcel
    protected Exercise(Parcel in){
        this.exerciseType = in.readString();
        this.exerciseName = in.readString();
        this.noOfReps = in.readInt();
        this.noOfSets = in.readInt();
    }

    public static final Creator<Exercise> CREATOR = new Creator<Exercise>() {
        @Override
        public Exercise createFromParcel(Parcel source) {
            return new Exercise(source);
        }

        @Override
        public Exercise[] newArray(int size) {
            return new Exercise[size];
        }
    };

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
