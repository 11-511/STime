package xyz.miles.stime.service;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class SetImageAsyncTask extends AsyncTask<String, Integer, Void> {
    private Bitmap bmImage;
    private ImageView imageView;

    public SetImageAsyncTask(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    protected Void doInBackground(String... strings) {
        String imageUrl = strings[0];
        try {
            Log.d("SetImageAsyncTask getting image url", imageUrl);
            URL url = new URL(imageUrl);
            URLConnection connection = url.openConnection();
            connection.connect();

            InputStream in;
            in = connection.getInputStream();
            bmImage = BitmapFactory.decodeStream(in);
            Log.d("bmImage decode", "success!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        imageView.setImageBitmap(bmImage);
    }
}
