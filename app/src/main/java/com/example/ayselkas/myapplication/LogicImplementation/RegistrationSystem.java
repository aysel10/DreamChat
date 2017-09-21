package com.example.ayselkas.myapplication.LogicImplementation;

import android.app.Activity;

/**
 * Created by artyomkuznetsov on 9/22/17.
 */

public class RegistrationSystem {
    private String email;
    private String password;
    private String profileName;
    private Activity activity;

    public RegistrationSystem(String email, String password, String profileName, Activity activity) {
        this.email = email;
        this.password = password;
        this.profileName = profileName;
        this.activity = activity;
    }

    public void registration() {
        // Registration process
        // Binding to database
        // Jumping to another actiivty
    }
}
