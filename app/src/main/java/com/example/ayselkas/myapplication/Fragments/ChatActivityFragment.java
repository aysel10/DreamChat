package com.example.ayselkas.myapplication.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


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
    private Button sendMessageButton;
    private Chat chat;

    ///////////

    ///////////



   public static ChatActivityFragment newInstance(String receiver, String receiverUid) {

       Bundle args = new Bundle();
       args.putString(Constants.ARG_RECEIVER, receiver);
       args.putString(Constants.ARG_RECEIVER_UID, receiverUid);
       Log.e("New Instance",receiverUid);
       ChatActivityFragment fragment = new ChatActivityFragment();
       fragment.setArguments(args);
       Log.e("Bundle",args.getString(Constants.ARG_RECEIVER_UID));
       return fragment;
   }



//    @Override
//    public void onStart() {
//        super.onStart();
//        EventBus.getDefault().register(this);
//    }
//
//    @Override
//    public void onStop() {
//
//        super.onStop();
//        EventBus.getDefault().unregister(this);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        myRecyclerViewChat = (RecyclerView) view.findViewById(R.id.chatRecyclerView);
        myTxtMessage = (EditText) view.findViewById(R.id.typeText);
        sendMessageButton=(Button)view.findViewById(R.id.sendMessageButton);
        return view;
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
        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    sendMessage();
            }
        });

    }
    public void sendMessage(){
        Bundle bundle=new Bundle();
        String message=myTxtMessage.getText().toString();
//        Log.e("Aysel",intent.getExtras().getString(Constants.ARG_RECEIVER));
        String receiver=bundle.getString(Constants.ARG_RECEIVER);
        String receiverUid=bundle.getString(Constants.ARG_RECEIVER_UID);

        String sender= FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String senderUid=FirebaseAuth.getInstance().getCurrentUser().getUid();

        Chat chat=new Chat(sender,
                receiver,
                senderUid,
                receiverUid,
                message,
                System.currentTimeMillis());
        sendMessageToFirebaseUser(chat);
    }

    public void sendMessageToFirebaseUser( final Chat chat){
        final String room_type_1=chat.senderUid+"_"+chat.receiverUid;
        final String room_type_2=chat.receiverUid+"_"+chat.senderUid;

        final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();

        databaseReference.child(Constants.ARG_CHAT_ROOMS).getRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(room_type_1)){
                    Log.e(TAG, "semd message to firebaseUser: "+room_type_1+" exists");
                    databaseReference.child(Constants.ARG_CHAT_ROOMS).child(room_type_1).child(String.valueOf(chat.timestamp)).setValue(chat);
                }else if(dataSnapshot.hasChild(room_type_2)){
                    Log.e(TAG,"Send messageToFirebaseUser: "+room_type_2+" exists");
                    databaseReference.child(Constants.ARG_CHAT_ROOMS).child(room_type_2).child(String.valueOf(chat.timestamp)).setValue(chat);
                }else{
                    Log.e(TAG,"Send messageToFirebaseUser: success");
                    databaseReference.child(Constants.ARG_CHAT_ROOMS).child(room_type_1).child(String.valueOf(chat.timestamp)).setValue(chat);
                    getMessageFromFirebaseUser(chat.senderUid,chat.receiverUid);
                }
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

}
