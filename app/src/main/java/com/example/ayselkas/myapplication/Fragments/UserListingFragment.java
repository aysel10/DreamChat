package com.example.ayselkas.myapplication.Fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ayselkas.myapplication.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class UserListingFragment extends Fragment {

    public UserListingFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_listing, container, false);
    }
}
