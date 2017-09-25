package com.example.ayselkas.myapplication.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ayselkas.myapplication.Activity.UserListingActivity;
import com.example.ayselkas.myapplication.Models.User;
import com.example.ayselkas.myapplication.R;

import java.util.List;

/**
 * Created by ayselkas on 9/23/17.
 */

public class UserListingAdapter extends ArrayAdapter<User>{
   // private List<User>userList;
    public UserListingAdapter(@NonNull Context context, @LayoutRes int resource, List<User>userList) {
        super(context, resource, userList);

       // this.userList=userList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null){
            convertView=((Activity)getContext()).getLayoutInflater().inflate(R.layout.user_listing_item,parent,false);
        }

        TextView userTextView=(TextView)convertView.findViewById(R.id.userTextView);

        User user=getItem(position);

        userTextView.setText(user.getName());



        return convertView;
    }

//    public List<User> getUserList() {
//        return userList;
//   }
}
