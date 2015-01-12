package com.example.iem.skatespotfinder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.File;

/**
 * Created by iem on 09/01/15.
 */
public class Method {

    private void getFileBrowser(){
        Intent lIntent = new Intent();
        lIntent.setType("image/*");
        lIntent.setAction(Intent.ACTION_GET_CONTENT);
        //startActivityForResult(Intent.createChooser(lIntent, "Select picture"), 1);
    }

    private Bitmap getPictureFromUri(Uri aUri){
        File lFile = new File(aUri.getPath());
        Bitmap lBitmap = null;
        if(lFile.exists()) {
            lBitmap = BitmapFactory.decodeFile(lFile.getAbsolutePath());
        }
        return lBitmap;
    }
}
