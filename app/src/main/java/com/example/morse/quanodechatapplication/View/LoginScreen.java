package com.example.morse.quanodechatapplication.View;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.morse.quanodechatapplication.ChatActivity;
import com.example.morse.quanodechatapplication.R;
import com.example.morse.quanodechatapplication.databinding.FragmentLoginScreenBinding;
import com.squareup.picasso.Picasso;


public class LoginScreen extends Fragment {
    private FragmentLoginScreenBinding loginScreenBinding;
    private Context context;
    private Uri PicturePath;
    SharedPreferences preferences;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences=context.getSharedPreferences("Cache",Context.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        loginScreenBinding=DataBindingUtil.inflate(inflater,R.layout.fragment_login_screen,container,false);
        View Root = loginScreenBinding.getRoot();
        // Inflate the layout for this fragment
        loginScreenBinding.SetImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Morse", "onClick: ");
                pickFromGallery();
            }
        });
    loginScreenBinding.Send.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent=new Intent(context , ChatActivity.class);
            SharedPreferences.Editor editor=preferences.edit();
            editor.putString("UserName",loginScreenBinding.UserName.getText().toString());
            editor.putString("UserImage",PicturePath.toString());
            editor.apply();
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        }
    });

        return Root;
    }
    private void pickFromGallery() {

        startActivityForResult(new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI), 20);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            PicturePath=data.getData();
            Picasso.with(context).load(PicturePath).into(loginScreenBinding.SetImage);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
        }

    @Override
    public void onStop() {
        super.onStop();

    }
}
