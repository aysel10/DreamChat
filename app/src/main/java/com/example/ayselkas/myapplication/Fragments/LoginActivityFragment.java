package com.example.ayselkas.myapplication.Fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.ayselkas.myapplication.LogicImplementation.LoginSystem;
import com.example.ayselkas.myapplication.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class LoginActivityFragment extends Fragment {
   private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    LoginSystem login;

    public LoginActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_login, container, false);
        emailEditText =  view.findViewById(R.id.emailEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        loginButton = view.findViewById(R.id.loginBtn);
        loginButton.setOnClickListener(buttonListener);
        return view;
    }

    View.OnClickListener buttonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            login = new LoginSystem(getActivity(),email, password);
            login.attemptToLogin();
        }
    };




}
