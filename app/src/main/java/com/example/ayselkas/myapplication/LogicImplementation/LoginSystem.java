package com.example.ayselkas.myapplication.LogicImplementation;


import android.app.Activity;
import android.support.annotation.NonNull;

import com.example.ayselkas.myapplication.Activity.UserListingActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
//        currentAuth=FirebaseAuth.getInstance();
//        final FirebaseUser user=currentAuth.getCurrentUser();
//        currentAuth.createUserWithEmailAndPassword(email,password)
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if(task.getResult().getUser()!=null&&currentAuth.getCurrentUser().isEmailVerified()){
//                            UserListingActivity.startActivity();
//                        }else{
//
//                        }
//                    }
//                });
//

    }

    public void logout() {
        // Logout from the account implementation
    }

}
