package com.example.jack.eee521_finalyearproject_b00708431_fitnesstracker;

public class User {

    private String userEmail;
    private String userName;
    private double userHeight;
    private double userWeight;
    private String userDOB;
    private String userGender;
    private int userExercises_Completed;
    private int userWorkouts_Completed;

    public User(){

    }

    public User(String userEmail, String userName, double userHeight, double userWeight, String userDOB, String userGender) {
        this.userEmail = userEmail;
        this.userName = userName;
        this.userHeight = userHeight;
        this.userWeight = userWeight;
        this.userDOB = userDOB;
        this.userGender = userGender;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
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

    public String getUserDOB() {
        return userDOB;
    }

    public void setUserDOB(String userDOB) {
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
