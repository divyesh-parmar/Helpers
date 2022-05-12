package com.example.jigneshjc.teleportblure.Teleport_Async;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;


public class BlureImageAsync extends AsyncTask<Void, Void, Void> {

	Context mContext;
    Bitmap bb;
    float seek;
    ImageView ivblure;

    public BlureImageAsync(Context mContext, Bitmap MaBitmap, float SEEK, ImageView ivBlure) {
        this.bb = MaBitmap;
        this.mContext = mContext;
        this.seek = SEEK;
        this.ivblure = ivBlure;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        bb = new BlurBuilder().blur(mContext, bb, seek);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        ivblure.setImageBitmap(bb);
    }
	class BlurBuilder {
        private static final float BITMAP_SCALE = 0.4f;
        //   private static final float BLUR_RADIUS = 10.5f;

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        public Bitmap blur(Context context, Bitmap image, float BLUR_RADIUS) {
            int width = Math.round(image.getWidth() * BITMAP_SCALE);
            int height = Math.round(image.getHeight() * BITMAP_SCALE);

            Bitmap inputBitmap = Bitmap.createScaledBitmap(image, width, height, false);
            Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);

            RenderScript rs = RenderScript.create(context);
            ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
            Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
            Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);
            theIntrinsic.setRadius(BLUR_RADIUS);
            theIntrinsic.setInput(tmpIn);
            theIntrinsic.forEach(tmpOut);
            tmpOut.copyTo(outputBitmap);

            return outputBitmap;
        }
    }

}