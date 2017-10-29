package com.example.ayselkas.myapplication.Fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ayselkas.myapplication.Activity.ChatActivity;
import com.example.ayselkas.myapplication.Activity.UserListingActivity;
import com.example.ayselkas.myapplication.Adapters.UserListingAdapter;
import com.example.ayselkas.myapplication.Models.User;
import com.example.ayselkas.myapplication.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class UserListingFragment extends Fragment {
    UserListingAdapter userListingAdapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    List<User> userList;
    ListView usersListing;
    TextView userTextView;
    public UserListingFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_user_listing, container, false);
        //   UserListingAdapter us
        //хватит себя так вести Да конечно

        userList = new ArrayList<>();
        userListingAdapter = new UserListingAdapter(getContext(), R.layout.user_listing_item, userList);
        usersListing =  view.findViewById(R.id.userListingView);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("users");
        usersListing.setAdapter(userListingAdapter);

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                User user=dataSnapshot.getValue(User.class);
                userListingAdapter.add(user);
///                Log.d("On item Clicked",userListingAdapter.getUserList().toString());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        usersListing.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Log.e("On item Clicked", userList.get(position).email);
                Log.e("On item Clicked", userList.get(position).uid);
                ChatActivity.startActivity(getActivity(),
                       userList.get(position).email,
                        userList.get(position).uid,
                        userList.get(position).name,
                        userList.get(position).firebaseToken);

            }
        });



        return view;
    }
    public User getUseer(int position ){
        return userList.get(position+1);
    }
}
