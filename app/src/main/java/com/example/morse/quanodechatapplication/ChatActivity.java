package com.example.morse.quanodechatapplication;

import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.morse.quanodechatapplication.Adapter.MessageAdapter;
import com.example.morse.quanodechatapplication.Model.ImageHelper;
import com.example.morse.quanodechatapplication.Model.Message;
import com.example.morse.quanodechatapplication.databinding.ActivityChatBinding;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityChatBinding chatBinding;
    private String  userName;
    private String  userImage;
    private Bitmap bitmapUserImage;
    private com.github.nkzawa.socketio.client.Socket socket;
    private ArrayList<Message> messages;
    private MessageAdapter messageAdapter;
    private SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        messages=new ArrayList<>();
        preferences=getSharedPreferences("Cache",MODE_PRIVATE);
        messageAdapter=new MessageAdapter(this,messages);
        chatBinding=DataBindingUtil.setContentView(this,    R.layout.activity_chat);
        chatBinding.RecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        chatBinding.RecyclerView.setAdapter(messageAdapter);
        this.userName=preferences.getString("UserName","null");
        this.userImage=preferences.getString("UserImage","null");
        try {
            bitmapUserImage = ReadBitmapFromUri(userImage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            socket=IO.socket("http://192.168.1.2:3000");
            socket.connect();
            socket.emit("joinPerson",userName);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        chatBinding.SendMessage.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        socket.on("SayHello", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String data = (String) args[0];
                        // get the extra data from the fired event and display a toast
                        Toast.makeText(ChatActivity.this, data, Toast.LENGTH_LONG).show();

                    }
                });
            }
        });
        socket.on("MessageRecieve", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject data = (JSONObject) args[0];
                        try {
                            //extract data from fired event
                            String nickname = data.getString("senderNickname");
                            String message = data.getString("message");
                            String image = data.getString("senderImage");
                            Bitmap i= ImageHelper.StringToBitMap(image);
                            // make instance of message
                            NotifyAdapter(nickname,image,message);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });
            }
        });
    }
    public Bitmap ReadBitmapFromUri(String Path) throws FileNotFoundException {
        Uri uri=Uri.parse(Path);
        final InputStream imageStream = getContentResolver().openInputStream(uri);
        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
        return selectedImage;
    }
    public String BitMapToString(Bitmap BitmapToString){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        BitmapToString.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp=Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    @Override
    public void onClick(View v) {
        if (chatBinding.EntreMessage.getText().toString() != null) {
            String BitmapImage = BitMapToString(bitmapUserImage);
            socket.emit("MessageSend", userName ,  chatBinding.EntreMessage.getText().toString(),BitmapImage );
            NotifyAdapter(userName,BitmapImage,chatBinding.EntreMessage.getText().toString());
            chatBinding.EntreMessage.setText("");
            chatBinding.EntreMessage.setHint("Message for QuaNode Room");
        }
    }
    public void NotifyAdapter (String Name , String Image , String MessageBody){
        Message message =new Message();
        message.setUserName(Name);
        message.setMessageBody(MessageBody);
        message.setImage(Image);
        messages.add(message);
        messageAdapter.notifyDataSetChanged();
    }
}
