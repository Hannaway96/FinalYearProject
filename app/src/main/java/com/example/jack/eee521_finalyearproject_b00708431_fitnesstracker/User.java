package com.example.jack.eee521_finalyearproject_b00708431_fitnesstracker;

import java.util.Date;

public class User {

    String userName;
    double userHeight;
    double userWeight;
    Date userDOB;
    String userGender;
    int userExercises_Completed;
    int userWorkouts_Completed;

    public User(){

    }

    public User(String userName, double userHeight, double userWeight, Date userDOB, String userGender) {
        this.userName = userName;
        this.userHeight = userHeight;
        this.userWeight = userWeight;
        this.userDOB = userDOB;
        this.userGender = userGender;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public double getUserHeight() {
        return userHeight;
    }

    public void setUserHeight(double userHeight) {
        this.userHeight = userHeight;
    }

    public double getUserWeight() {
        return userWeight;
    }

    public void setUserWeight(double userWeight) {
        this.userWeight = userWeight;
    }

    public Date getUserDOB() {
        return userDOB;
    }

    public void setUserDOB(Date userDOB) {
        this.userDOB = userDOB;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public int getUserExercises_Completed() {
        return userExercises_Completed;
    }

    public void setUserExercises_Completed(int userExercises_Completed) {
        this.userExercises_Completed = userExercises_Completed;
    }

    public int getUserWorkouts_Completed() {
        return userWorkouts_Completed;
    }

    public void setUserWorkouts_Completed(int userWorkouts_Completed) {
        this.userWorkouts_Completed = userWorkouts_Completed;
    }


    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", userHeight=" + userHeight +
                ", userWeight=" + userWeight +
                ", userDOB=" + userDOB +
                ", userGender='" + userGender + '\'' +
                ", userExercises_Completed=" + userExercises_Completed +
                ", userWorkouts_Completed=" + userWorkouts_Completed +
                '}';
    }
}
