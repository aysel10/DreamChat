package com.example.ayselkas.myapplication.Fragments;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ayselkas.myapplication.Activity.LoginActivity;
import com.example.ayselkas.myapplication.Activity.RegisterActivity;
import com.example.ayselkas.myapplication.Activity.UserListingActivity;
import com.example.ayselkas.myapplication.LogicImplementation.LoginSystem;
import com.example.ayselkas.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A placeholder fragment containing a simple view.
 */
public class LoginActivityFragment extends Fragment {
   private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private FirebaseAuth currentAuth;
    private TextView createAccount;
    LoginSystem login;

    public LoginActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_login, container, false);
        emailEditText =  view.findViewById(R.id.emailEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        loginButton = view.findViewById(R.id.loginButton);
        loginButton.setOnClickListener(buttonListener);
        createAccount= (TextView) view.findViewById(R.id.createAccount);
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),RegisterActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    View.OnClickListener buttonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            login();
        }
    };


    private void login(){
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
//        login = new LoginSystem(getActivity(),email, password);
         FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()) {
                           onLoginSuccess();
                       }else{
                          onLoginFail();
                        }
                    }
                });

    }

    private void onLoginSuccess() {
        if(FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()) {
            UserListingActivity.startActivity(getContext());

        }else{
            onLoginFail();
        }
    }

    private void onLoginFail() {
        Toast.makeText(getActivity(),R.string.toast_email_not_verified,Toast.LENGTH_LONG)
                .show();
    }




}
