package com.example.ayselkas.myapplication.Fragments;

import android.app.ProgressDialog;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.ayselkas.myapplication.Adapters.ChatRecyclerAdapter;
import com.example.ayselkas.myapplication.LocalStorage.Constants;
import com.example.ayselkas.myapplication.R;

import org.greenrobot.eventbus.EventBus;


/**
 * A placeholder fragment containing a simple view.
 */
public class ChatActivityFragment extends Fragment {
    private RecyclerView myRecyclerViewChat;
    private EditText myTxtMessage;
    private ProgressDialog myProgressDialog; // progress bar can be used instead
    private ChatRecyclerAdapter myChatRecyclerAdapter;



   public static ChatActivityFragment newInstance(String receiver, String receiverUid) {
       Bundle args = new Bundle();
       args.putString(Constants.ARG_RECEIVER, receiver);
       args.putString(Constants.ARG_RECEIVER_UID, receiverUid);
       ChatActivityFragment fragment = new ChatActivityFragment();
       fragment.setArguments(args);
       return fragment;
   }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    private void bindViews(View view) {
        myRecyclerViewChat = (RecyclerView) view.findViewById(R.id.chatRecyclerView);
        myTxtMessage = (EditText) view.findViewById(R.id.typeText);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        myProgressDialog = new ProgressDialog(getActivity());
        myProgressDialog.setTitle("Loading!");
        myProgressDialog.setMessage("Жди");
        myProgressDialog.setIndeterminate(true);
//        myTxtMessage.setOnEditorActionListener(this);
    }





}
