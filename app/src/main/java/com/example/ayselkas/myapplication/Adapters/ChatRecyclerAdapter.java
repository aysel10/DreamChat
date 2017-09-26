package com.example.ayselkas.myapplication.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ayselkas.myapplication.Models.Chat;
import com.example.ayselkas.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

/**
 * Created by ayselkas on 9/25/17.
 */

public class ChatRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final int VIEW_TYPE_ME=1;
    private static final int VIEW_TYPE_OTHER=2;

    private List<Chat>myChat;

    public ChatRecyclerAdapter(List<Chat> myChat) {
        this.myChat = myChat;
    }

    public void add(Chat chat){
        myChat.add(chat);
        notifyItemInserted(myChat.size()-1);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder=null;
        switch (viewType){
            case VIEW_TYPE_ME:
                View viewChatMine=layoutInflater.inflate(R.layout.item_chat_mine,parent,false);
                viewHolder=new MyChatViewHolder(viewChatMine);
                break;
            case VIEW_TYPE_OTHER:
                View viewChatOther=layoutInflater.inflate(R.layout.item_chat_other,parent,false);
                viewHolder=new OtherChatViewHolder(viewChatOther);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(TextUtils.equals(myChat.get(position).senderUid, FirebaseAuth.getInstance().getCurrentUser().getUid())){
                configureMyChatViewHolder((MyChatViewHolder)holder,position);
        }else{
            configureOtherChatViewHolder((OtherChatViewHolder)holder,position);
        }
    }
    public void configureMyChatViewHolder(MyChatViewHolder myChatViewHolder,int position){
        Chat chat=myChat.get(position);

        myChatViewHolder.txtChatMessage.setText(chat.message);
    }
    public void configureOtherChatViewHolder(OtherChatViewHolder otherChatViewHolder,int position){
        Chat chat=myChat.get(position);
        otherChatViewHolder.txtChatMessage.setText(chat.message);
    }

    @Override
    public int getItemViewType(int position) {
        if(TextUtils.equals(myChat.get(position).senderUid,FirebaseAuth.getInstance().getCurrentUser().getUid())){
            return VIEW_TYPE_ME;
        }else{
            return VIEW_TYPE_OTHER;
        }
    }

    @Override
    public int getItemCount() {
        if(myChat!=null){
            return  myChat.size();
        }
        return 0;
    }

    private static class MyChatViewHolder extends RecyclerView.ViewHolder{
        private TextView txtChatMessage;

        public MyChatViewHolder(View itemView){
            super(itemView);
            txtChatMessage=(TextView)itemView.findViewById(R.id.typeText);
        }

    }
    private static class OtherChatViewHolder extends RecyclerView.ViewHolder{
        private TextView txtChatMessage;

        public OtherChatViewHolder(View itemView){
            super(itemView);
            txtChatMessage=(TextView) itemView.findViewById(R.id.typeText);
        }
    }
}
