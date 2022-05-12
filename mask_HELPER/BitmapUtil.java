package com.example.jigneshjc.portraphoto.portra_FILTER;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.RequiresApi;

public class BitmapUtil {
	
	Bitmap mask;
    Bitmap result, original;
    
    public Bitmap makeMaskImageNew(Bitmap main_mask, Bitmap main_bitmap) {
        try {
            this.result.recycle();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.mask = main_mask;
        this.result = Bitmap.createBitmap(this.mask.getWidth(),
                this.mask.getHeight(), Bitmap.Config.ARGB_8888);
        this.original = main_bitmap;
        Canvas mCanvas = new Canvas(this.result);
        Paint paint = new Paint(1);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        mCanvas.drawBitmap(this.original, 0.0f, 0.0f, null);
        mCanvas.drawBitmap(this.mask, 0.0f, 0.0f, paint);
        paint.setXfermode(null);
        try {
            this.mask.recycle();
            this.mask = null;
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            this.original.recycle();
            this.original = null;
        } catch (Exception e22) {
            e22.printStackTrace();
        }
        return result;
    }
}