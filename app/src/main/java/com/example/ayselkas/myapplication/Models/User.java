package com.example.ayselkas.myapplication.Models;

/**
 * Created by artyomkuznetsov on 9/21/17.
 */

public class User {
    public String email;
    public String uid;
    public String name;
    public String photoUrl;
    public User(){
    }

    public User(String email, String uid, String name) {
        this.email = email;
        this.uid = uid;
        this.name = name;

    }

    public String getEmail() {
        return email;
    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }
}
