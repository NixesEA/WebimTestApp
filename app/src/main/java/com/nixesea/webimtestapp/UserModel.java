package com.nixesea.webimtestapp;

public class UserModel {
    long id;
    String firstName;
    String secondName;
    String photoURL;
    boolean online;

    public boolean isOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online == 1;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public String getName(){
        return firstName + " " + secondName;
    }
}
