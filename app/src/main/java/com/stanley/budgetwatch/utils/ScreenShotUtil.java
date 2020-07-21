package com.stanley.budgetwatch.utils;

import android.graphics.Bitmap;
import android.os.Environment;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ScreenShotUtil {
    public static Bitmap takeScreenShot(View view){
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache(true);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        return bitmap;
    }

    public static Bitmap takeScreenShotOfRootView(View view){
        return takeScreenShot(view.getRootView());
    }

    public static void storeScreenShot(Bitmap bitmap, String fileName){
        String path = Environment.getExternalStorageDirectory().toString() + "/" + fileName;
        OutputStream outputStream = null;
        File imageFile = new File(path);

        try {
            outputStream = new FileOutputStream(imageFile);
           bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
           outputStream.flush();
        } catch (IOException e){

        } finally {
            try {
                if(outputStream != null){
                    outputStream.close();
                }
            } catch (Exception e){

            }
        }
    }
}
