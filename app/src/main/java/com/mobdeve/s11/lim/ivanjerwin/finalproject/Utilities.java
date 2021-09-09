package com.mobdeve.s11.lim.ivanjerwin.finalproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Utilities {
    // convert from bitmap to byte array
    public static byte[] getBytes(Bitmap bitmap) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        stream.close();
        return stream.toByteArray();
    }

    // convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public static int convertBooltoInt(boolean bool){
        if(bool) return 1;
        else return 0;
    }

    public static boolean convertInttoBool(int i){
        return i == 1;
    }
}
