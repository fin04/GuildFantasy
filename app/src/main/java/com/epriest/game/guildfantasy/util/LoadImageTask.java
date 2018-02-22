package com.epriest.game.guildfantasy.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
/**
 * Created by darka on 2018-02-23.
 */

public class LoadImageTask extends AsyncTask<String, Integer, Bitmap> {
    Context mContext;
    Bitmap.Config format;
    int scaleValue;
    int SIZE = 10;

    public LoadImageTask(Context mContext, Bitmap.Config format, int scaleValue) {
        this.mContext = mContext;
        this.format = format;
        this.scaleValue = scaleValue;
    }

    protected Bitmap doInBackground(String...paths) {
        Bitmap bitmap = null;
        try{
            InputStream is = mContext.getAssets().open(paths[0]);
            BitmapFactory.Options opt = new BitmapFactory.Options();
            opt.inSampleSize = scaleValue;
            if(format == null)
                opt.inPreferredConfig = Bitmap.Config.RGB_565;
            else
                opt.inPreferredConfig = format;
            bitmap = BitmapFactory.decodeStream(is, null, opt);
            if(is != null)
                try{
                    is.close();
                }catch(IOException e){
                    Log.d("","IOException"+e);
                }
        }catch(IOException e){
            Log.d("", "IOException"+e);
        }
        return bitmap;
    }

    protected void onProgressUpdate(Integer...progress) {
        String text = "Downloading\n[";
        for (int i = 0; i < progress[0]; i++) {
            text += '=';
        }
        for (int i = SIZE; i > progress[0]; i--) {
            text += "  ";
        }
        text += "]";
//        m_vwLoad.setText(text);
//You can make a much prettier load bar using a SurfaceView and drawing progress or creating drawable resources and using an ImageView
    }

    protected void onPostExecute(Bitmap result) {
//        m_vwLoad.setVisibility(View.GONE);
//        m_vwImage.setVisibility(View.VISIBLE);
//        m_vwImage.setImageBitmap(result);
//Hide the load bar, show the image, and set the image display to the resulting Bitmap
    }
}
