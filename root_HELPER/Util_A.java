package com.example.jigneshjc.magictouch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Div 
 */

public class Util_A {

    Context mContext;

    public Util_A(Context mContext) {
        this.mContext = mContext;
    }

    public void TOAST(String msg) {
        Toast.makeText(mContext, msg, 3).show();
    }

    public void TOAST(Float msg) {
        Toast.makeText(mContext, "" + msg, 3).show();
    }


    public void TOAST(boolean msg) {
        Toast.makeText(mContext, "" + msg, 3).show();
    }

    public void TOAST(int msg) {
        Toast.makeText(mContext, "" + msg, 3).show();
    }

    public void TOAST(Bitmap msg) {
        Toast t = new Toast(mContext);
        ImageView iv = new ImageView(mContext);
        iv.setImageBitmap(msg);
        t.setView(iv);
        t.show();
    }

    public void TOAST(Uri msg) {
        Toast t = new Toast(mContext);
        ImageView iv = new ImageView(mContext);
        iv.setImageURI(msg);
        t.setView(iv);
        t.show();
    }

    public void TOAST(Drawable msg) {
        Toast t = new Toast(mContext);
        ImageView iv = new ImageView(mContext);
        iv.setImageDrawable(msg);
        t.setView(iv);
        t.show();
    }

    public Bitmap getBitmap_from_Drawable(int RESOURC) {
        return BitmapFactory.decodeResource(mContext.getResources(),
                RESOURC);
    }

    public Bitmap getBitmap_from_Drawable(Drawable d) {
        return ((BitmapDrawable) d).getBitmap();
    }

    public Drawable getDrawable_from_Bitmap(Bitmap bitmap) {
        return new BitmapDrawable(mContext.getResources(), bitmap);
    }


    public Bitmap getDrawingCache(View v) {
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache(true);
        // creates immutable clone
        Bitmap b = Bitmap.createBitmap(v.getDrawingCache());
        v.setDrawingCacheEnabled(false); // clear drawing cache
        return b;
    }

    public ArrayList<String> getAssetFolderFiles(String path) {
        String[] list;
        ArrayList<String> files = new ArrayList<>();
        try {
            list = mContext.getAssets().list(path);
            if (list.length > 0) {
                // This is a folder
                for (String file : list) {
                    files.add(file);
                }
            }
        } catch (Exception e) {
        }
        return files;
    }

    public ArrayList<Bitmap> getAssetFolderImage(Context con, String folderName) {
        ArrayList<Bitmap> LIST = new ArrayList<>();
        try {
            String[] images = con.getAssets().list(folderName);
            ArrayList<String> listImages = new ArrayList<String>(
                    Arrays.asList(images));
            for (int i = 0; i < listImages.size(); i++) {
                InputStream inputstream = con.getAssets().open(
                        folderName + "/" + listImages.get(i));

                BitmapFactory.Options opts = new BitmapFactory.Options();
                opts.inDensity = 100;

                Drawable drawable = Drawable.createFromResourceStream(null,
                        null, inputstream, null, opts);

                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();

                LIST.add(bitmap);
            }
        } catch (Exception e) {
        }
        return LIST;

    }

    public int getDisplayHieght() {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    public int getDisplayWidth() {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    public void set_TYPEFACE(String name, TextView tv) {
        Typeface tf = Typeface.createFromAsset(mContext.getAssets(), "fonts/" + name);
        tv.setTypeface(tf);
    }

    public void shareImage(Uri u, PACKAGE pck) {
        try {
            if (pck.equals(PACKAGE.WHATSAAP)) {
                Intent i = new Intent(
                        Intent.ACTION_SEND);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra(Intent.EXTRA_STREAM, u);
                i.setPackage("com.whatsapp");
                i.setType("image/*");
                mContext.startActivity(i);
            } else if (pck.equals(PACKAGE.FACEBOOK)) {

            } else if (pck.equals(PACKAGE.INSTAGRAM)) {

            } else {
                Intent i = new Intent(
                        Intent.ACTION_SEND);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra(Intent.EXTRA_STREAM, u);
                i.setType("image/*");
            }
        } catch (Exception e) {
            TOAST(e.toString());
        }

    }

    public void shareImage(Bitmap bitmap, PACKAGE pck) {
        try {
            String bitmapPath = MediaStore.Images.Media.insertImage(mContext.getContentResolver(),
                    bitmap, "title", null);
            Uri bitmapUri = Uri.parse(bitmapPath);
            if (pck.equals(PACKAGE.WHATSAAP)) {
                Intent i = new Intent(
                        Intent.ACTION_SEND);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra(Intent.EXTRA_STREAM, bitmapUri);
                i.setPackage("com.whatsapp");
                i.setType("image/*");
                mContext.startActivity(i);
            } else if (pck.equals(PACKAGE.FACEBOOK)) {

            } else if (pck.equals(PACKAGE.INSTAGRAM)) {

            } else {
                Intent i = new Intent(
                        Intent.ACTION_SEND);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra(Intent.EXTRA_STREAM, bitmapUri);
                i.setType("image/*");
            }
        } catch (Exception e) {
            TOAST(e.toString());
        }

    }

    public void start_Camera(int Request_Code) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        ((Activity) mContext).startActivityForResult(intent, Request_Code);
    }

    public void start_Gallary(int Request_Code) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        ((Activity) mContext).startActivityForResult(intent, Request_Code);
    }

    public Bitmap getBitmap_from_uri(Uri u) {
        Bitmap myBitmap = null;
        try {
            myBitmap = MediaStore.Images.Media.getBitmap(
                    mContext.getContentResolver(), u);
        } catch (Exception e) {
            //  show_Text(c, e.toString());
        }
        return myBitmap;
    }

    public Bitmap getShadow(Bitmap mainImage) {
        int width = mainImage.getWidth();
        int height = mainImage.getHeight();
        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);
        Bitmap reflectionImage = Bitmap.createBitmap(mainImage, 0,
                0, width, height, matrix, false);

        Bitmap reflectedBitmap = Bitmap.createBitmap(width,
                (height + height), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(reflectedBitmap);
        canvas.drawBitmap(mainImage, 0, 0, null);
        Paint defaultPaint = new Paint();
        canvas.drawRect(0, height, width, height, defaultPaint);
        canvas.drawBitmap(reflectionImage, 0, height - 6, null);
        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0,
                mainImage.getHeight(), 0, reflectedBitmap.getHeight()
                , 0x70ffffff, 0x00ffffff, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawRect(0, height, width, reflectedBitmap.getHeight()
                , paint);
        return reflectedBitmap;
    }

    public enum PACKAGE {
        WHATSAAP,
        FACEBOOK,
        INSTAGRAM;
    }

}
