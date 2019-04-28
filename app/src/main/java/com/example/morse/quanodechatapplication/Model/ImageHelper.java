package com.example.morse.quanodechatapplication.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public  class ImageHelper {
   static public Bitmap StringToBitMap(String StringToBitmap){
        try{
            byte [] encodeByte=Base64.decode(StringToBitmap,Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }

}
