package com.example.ayselkas.myapplication.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.example.ayselkas.myapplication.Activity.ChatActivity;
import com.example.ayselkas.myapplication.Adapters.ChatRecyclerAdapter;
import com.example.ayselkas.myapplication.LocalStorage.Constants;
import com.example.ayselkas.myapplication.Models.Chat;
import com.example.ayselkas.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import static com.google.android.gms.internal.zzs.TAG;


/**
 * A placeholder fragment containing a simple view.
 */
public class ChatActivityFragment extends Fragment {
    private RecyclerView myRecyclerViewChat;
    private EditText myTxtMessage;
    private ProgressDialog myProgressDialog; // progress bar can be used instead
    private ChatRecyclerAdapter myChatRecyclerAdapter;
    private Button sendButton;
    private Chat chat;

    ///////////

    ///////////



    public static ChatActivityFragment newInstance(String receiver,
                                                    String receiverUid,
                                                    String firebaseToken) {
        Bundle args = new Bundle();
        args.putString(Constants.ARG_RECEIVER, receiver);
        args.putString(Constants.ARG_RECEIVER_UID, receiverUid);
        args.putString(Constants.ARG_FIREBASE_TOKEN, firebaseToken);
        ChatActivityFragment fragment = new ChatActivityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_chat, container, false);
        sendButton=(Button) fragmentView.findViewById(R.id.sendMessageButton);
        sendButton.setEnabled(false);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });
        bindViews(fragmentView);
        return fragmentView;
    }

    private void bindViews(View view) {
        myRecyclerViewChat = (RecyclerView) view.findViewById(R.id.chatRecyclerView);
        myTxtMessage = (EditText) view.findViewById(R.id.typeText);
        myTxtMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().trim().length() > 0) {
                    sendButton.setEnabled(true);}
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        myProgressDialog = new ProgressDialog(getActivity());
        myProgressDialog.setTitle("LOading");
        myProgressDialog.setMessage("Please Wait");
        myProgressDialog.setIndeterminate(true);
       getMessageFromFirebaseUser(FirebaseAuth.getInstance().getCurrentUser().getUid(),
               getActivity().getIntent().getExtras().getString(Constants.ARG_RECEIVER_UID));

//        mChatPresenter.getMessage(FirebaseAuth.getInstance().getCurrentUser().getUid(),
//                getArguments().getString(Constants.ARG_RECEIVER_UID));
    }


    private void sendMessage() {
        String message = myTxtMessage.getText().toString();
        String receiver = getActivity().getIntent().getExtras().getString(Constants.ARG_RECEIVER);
        String receiverUid = getActivity().getIntent().getExtras().getString(Constants.ARG_RECEIVER_UID);
        String sender = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String senderUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Chat chat = new Chat(sender,
                receiver,
                senderUid,
                receiverUid,
                message,
                System.currentTimeMillis());
        sendMessageToFirebaseUser(chat);
    }

    public void sendMessageToFirebaseUser( final Chat chat) {
        final String room_type_1 = chat.senderUid + "_" + chat.receiverUid;
        final String room_type_2 = chat.receiverUid + "_" + chat.senderUid;

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child(Constants.ARG_CHAT_ROOMS).getRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(room_type_1)) {
                    Log.e(TAG, "sendMessageToFirebaseUser: " + room_type_1 + " exists");
                    databaseReference.child(Constants.ARG_CHAT_ROOMS).child(room_type_1).child(String.valueOf(chat.timestamp)).setValue(chat);
                } else if (dataSnapshot.hasChild(room_type_2)) {
                    Log.e(TAG, "sendMessageToFirebaseUser: " + room_type_2 + " exists");
                    databaseReference.child(Constants.ARG_CHAT_ROOMS).child(room_type_2).child(String.valueOf(chat.timestamp)).setValue(chat);
                } else {
                    Log.e(TAG, "sendMessageToFirebaseUser: success");
                    databaseReference.child(Constants.ARG_CHAT_ROOMS).child(room_type_1).child(String.valueOf(chat.timestamp)).setValue(chat);
                    getMessageFromFirebaseUser(chat.senderUid, chat.receiverUid);
                }
                // send push notification to the receiver
//                sendPushNotificationToReceiver(chat.sender,
//                        chat.message,
//                        chat.senderUid,
//                        new SharedPrefUtil(context).getString(Constants.ARG_FIREBASE_TOKEN),
//                        receiverFirebaseToken);
                    onSendMessageSuccess();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    public void getMessageFromFirebaseUser(String senderUid, String receiverUid) {
        final String room_type_1 = senderUid + "_" + receiverUid;
        final String room_type_2 = receiverUid + "_" + senderUid;
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child(Constants.ARG_CHAT_ROOMS).getRef()
                .addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild(room_type_1)) {
                            FirebaseDatabase.getInstance()
                                    .getReference()
                                    .child(Constants.ARG_CHAT_ROOMS)
                                    .child(room_type_1).addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                    Chat chat = dataSnapshot.getValue(Chat.class);
                                    onGetMessageSuccess(chat);
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
                        }else if(dataSnapshot.hasChild(room_type_2)) {
                            FirebaseDatabase.getInstance().getReference().child(Constants.ARG_CHAT_ROOMS)
                                    .child(room_type_2).addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                    Chat chat =dataSnapshot.getValue((Chat.class));
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
                        }else{
                            Log.e(TAG,"getMessageFromFirebaseUser: no such room available");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }




    public void onGetMessageSuccess(Chat chat){
        if(myChatRecyclerAdapter==null||myChatRecyclerAdapter.getItemCount()==0){
            myChatRecyclerAdapter=new ChatRecyclerAdapter(new ArrayList<Chat>());
            myRecyclerViewChat.setAdapter(myChatRecyclerAdapter);
        }
        myChatRecyclerAdapter.add(chat);
        myRecyclerViewChat.smoothScrollToPosition(myChatRecyclerAdapter.getItemCount()-1);

    }
    public void onSendMessageSuccess(){
        myTxtMessage.setText("");
    }

}