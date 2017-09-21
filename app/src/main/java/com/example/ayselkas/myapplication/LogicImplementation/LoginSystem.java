package com.example.ayselkas.myapplication.LogicImplementation;


import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by artyomkuznetsov on 9/21/17.
 */

public class LoginSystem {
    private FirebaseAuth currentAuth;
    private String email;
    private String password;
    private Activity activity;

    public LoginSystem(Activity activity, String email, String password) {
        this.email = email;
        this.password = password;
        this.activity = activity;
    }

    public void attemptToLogin() {
        // try to login
        // verify user
    }

    public void logout() {
        // Logout from the account implementation
    }




}
