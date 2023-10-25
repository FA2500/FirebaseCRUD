package com.fac.firebasecrud;

import androidx.annotation.NonNull;

public class User {
    //E2
    //Properties
    private String uid;
    private String username;
    private int age;
    private double saving;
    private boolean married;

    //No Param Constructor
    public User()
    {

    }

    //Param Constructor
    public User(String uid, String username, int age, double saving, boolean married)
    {
        this.uid = uid;
        this.username = username;
        this.age = age;
        this.saving = saving;
        this.married = married;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getSaving() {
        return saving;
    }

    public void setSaving(double saving) {
        this.saving = saving;
    }

    public boolean isMarried() {
        return married;
    }

    public void setMarried(boolean married) {
        this.married = married;
    }

    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", username='" + username + '\'' +
                ", age=" + age +
                ", saving=" + saving +
                ", married=" + married +
                '}';
    }
}
