package com.example.dtttestapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bitmapImage;

    public DownloadImageTask(ImageView bmImage) {
        this.bitmapImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String url = "https://intern.development.d-tt.dev/" + urls[0];
        Bitmap bitmap = null;
        try {
            InputStream in = new URL(url).openStream();
            bitmap = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return bitmap;
    }

    protected void onPostExecute(Bitmap result) {
        bitmapImage.setImageBitmap(result);
    }
}
