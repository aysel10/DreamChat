package com.example.ayselkas.myapplication.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.ayselkas.myapplication.Fragments.ChatActivityFragment;
import com.example.ayselkas.myapplication.LocalStorage.Constants;
import com.example.ayselkas.myapplication.R;

public class ChatActivity extends AppCompatActivity {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        init();

    }


    public static void startActivity(Context context,String receiver,String receiverUid, String name, String firebaseToken){
        Intent intent=new Intent(context,ChatActivity.class);
        intent.putExtra(Constants.ARG_RECEIVER,receiver);
        intent.putExtra(Constants.ARG_RECEIVER_UID,receiverUid);
        intent.putExtra(Constants.ARG_USER_NICKNAME, name);
        intent.putExtra(Constants.ARG_FIREBASE_TOKEN,firebaseToken);
        context.startActivity(intent);
        Log.e("bla blawka",intent.getExtras().getString(Constants.ARG_RECEIVER_UID));

    }



    private void init(){
        toolbar.setTitle(getIntent().getExtras().getString(Constants.ARG_USER_NICKNAME));
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_content_chat,
                ChatActivityFragment.newInstance(getIntent().getExtras().getString(Constants.ARG_RECEIVER),
                        getIntent().getExtras().getString(Constants.ARG_RECEIVER_UID),
                        getIntent().getExtras().getString(Constants.ARG_FIREBASE_TOKEN)),
                ChatActivityFragment.class.getSimpleName());
        fragmentTransaction.commit();

        Log.e("bla artyom koza",getIntent().getExtras().getString(Constants.ARG_RECEIVER_UID));
    }


}
