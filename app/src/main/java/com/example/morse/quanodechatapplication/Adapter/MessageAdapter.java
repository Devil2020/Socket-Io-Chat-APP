package com.example.morse.quanodechatapplication.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.morse.quanodechatapplication.Model.ImageHelper;
import com.example.morse.quanodechatapplication.Model.Message;
import com.example.morse.quanodechatapplication.R;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageHolder> {
    ArrayList<Message> messages;
    Context context;
    public MessageAdapter(Context context,ArrayList<Message> messages){
        this.messages=messages;
        this.context=context;
    }
    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.messageview,viewGroup,false);
        return new MessageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageHolder messageHolder, int i) {
        messageHolder.Message.setText(messages.get(i).getMessageBody());
        Bitmap bitmap=ImageHelper.StringToBitMap(messages.get(i).getImage());
        messageHolder.UserImage.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class MessageHolder extends RecyclerView.ViewHolder{
        ImageView UserImage;
        TextView Message;
        public MessageHolder(@NonNull View itemView) {
            super(itemView);
            UserImage=itemView.findViewById(R.id.PersonImage);
            Message = itemView.findViewById(R.id.MessageBody);
        }
    }
}
