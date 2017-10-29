package com.example.ayselkas.myapplication.Fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ayselkas.myapplication.R;

/**
 * Created by artyomkuznetsov on 10/4/17.
 */

public class NickNameChangerFragment extends DialogFragment {

    public NickNameChangerFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.change_nickname_dialog, container, false);

        return v;
    }
}
