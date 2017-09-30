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
import android.widget.Toast;

import com.example.ayselkas.myapplication.Activity.LoginActivity;
import com.example.ayselkas.myapplication.Activity.UserListingActivity;
import com.example.ayselkas.myapplication.Models.User;
import com.example.ayselkas.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A placeholder fragment containing a simple view.
 */
public class RegisterActivityFragment extends Fragment {
    private EditText registerName;
    private EditText registerEmail;
    private EditText registerPassword;
    private Button registerButton;
    private String email;
    private String firebaseToken;
    private String password;
    private String name;


    public RegisterActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_register,container,false);
        registerButton=view.findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
        registerPassword=view.findViewById(R.id.registerPassword);
        registerEmail=view.findViewById(R.id.registerEmail);
        registerName=view.findViewById(R.id.registerName);
        return view;
    }
    private void register(){
        email=registerEmail.getText().toString();
        password=registerPassword.getText().toString();
        name=registerName.getText().toString();



        FirebaseAuth auth=FirebaseAuth.getInstance();

        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            onSuccess();
                            FirebaseUser user=task.getResult().getUser();
                            setUserParameters(user);
                            addToUsers(user);
                            LoginActivity.startActivity(getContext());
                        }else{
                            onFailure();
                        }
                    }
                });

    }
    private void onSuccess(){
        FirebaseAuth auth=FirebaseAuth.getInstance();
        FirebaseUser user=auth.getCurrentUser();
        user.sendEmailVerification();
    }

    public void onFailure(){
        Toast.makeText(getActivity(),"GO OUT FROM MY CHAT ARTYON",Toast.LENGTH_LONG);
    }

    private void addToUsers(FirebaseUser firebaseUser){

        DatabaseReference db= FirebaseDatabase.getInstance().getReference();
        User user=new User(email,firebaseUser.getUid(),name);
        db.child("users").child(user.getUid()).setValue(user);


    }

    private void setUserParameters(FirebaseUser user){

        UserProfileChangeRequest profileUpdates=new UserProfileChangeRequest.Builder()
                .setDisplayName(name).build();

        user.updateProfile(profileUpdates);
    }

}
