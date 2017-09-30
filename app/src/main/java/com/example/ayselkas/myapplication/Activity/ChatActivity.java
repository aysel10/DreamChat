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
import android.view.View;

import com.example.ayselkas.myapplication.Fragments.ChatActivityFragment;
import com.example.ayselkas.myapplication.LocalStorage.Constants;
import com.example.ayselkas.myapplication.R;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();

    }

    public static void startActivity(Context context,String receiver,String receiverUid){
        Intent intent=new Intent(context,ChatActivity.class);
        intent.putExtra(Constants.ARG_RECEIVER,receiver);
        intent.putExtra(Constants.ARG_RECEIVER_UID,receiverUid);
        context.startActivity(intent);
        Log.e("bla blawka",intent.getExtras().getString(Constants.ARG_RECEIVER_UID));



    }
    private void init(){
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_chat,
                ChatActivityFragment.newInstance(getIntent().getExtras().getString(Constants.ARG_RECEIVER),
                        getIntent().getExtras().getString(Constants.ARG_RECEIVER_UID)),
                        ChatActivityFragment.class.getSimpleName());
        fragmentTransaction.commit();

        Log.e("bla artyom koza",getIntent().getExtras().getString(Constants.ARG_RECEIVER_UID));
    }


}
