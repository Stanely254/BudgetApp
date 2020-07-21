package com.stanley.budgetwatch.utils;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

public class FileUtil {
    private int count = 0;
    private static FileUtil mInstance;
    private FileUtil() {
    }
    public static FileUtil getInstance() {
        if (mInstance == null) {
            synchronized (FileUtil.class) {
                if (mInstance == null) {
                    mInstance = new FileUtil();
                }
            }
        }
        return mInstance;
    }

    public String getReceiptScreenshotFolder(){
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "/Budget-Watch/file/receipt/");
        if(!file.exists()) file.mkdirs();

        return String.valueOf(file);
    }

    /**
     * Stores the given {@link Bitmap} to a path on the device.
     *
     * @param bitmap   The {@link Bitmap} that needs to be stored
     * @param filePath The path in which the bitmap is going to be stored.
     */
    public void storeBitmap(Bitmap bitmap, String filePath) {
        File imageFile = new File(filePath);
        Objects.requireNonNull(imageFile.getParentFile()).mkdirs();
        try {
            OutputStream fout = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fout);
            fout.flush();
            fout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getScreenshotFolder() {
        File root = new File(Environment.getExternalStorageDirectory().getPath(), "/Budget-Watch/receipt/");
        if(!root.exists()) root.mkdirs();

        return String.valueOf(root);
    }
}
