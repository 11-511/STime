package xyz.miles.stime.service;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.BitSet;
import java.util.List;

import xyz.miles.stime.activity.MainActivity;
import xyz.miles.stime.util.LoadMoreWrapper;

public class AddImagesAsyncTask extends AsyncTask<String, Integer, Void> {

    private LoadMoreWrapper wrapper;
    private List<Bitmap> bmImages;

    public AddImagesAsyncTask(LoadMoreWrapper wrapper, List<Bitmap> bmImages) {
        this.wrapper = wrapper;
        this.bmImages = bmImages;
    }

    @Override
    protected Void doInBackground(String... strings) {
        String imageUrl = strings[0];
//        if (!isCancelled()) {
//            addData(imageUrl);
//        }
        addData(imageUrl);

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

//        if (!isCancelled()) {
//            wrapper.notifyDataSetChanged();
//        }
        wrapper.notifyDataSetChanged();
    }

    private void addData(String imageUrl) {
        try {
            Log.d("do in back ground get image url", imageUrl);
            URL url = new URL(imageUrl);
            URLConnection connection = url.openConnection();
            connection.connect();

            InputStream in;
            in = connection.getInputStream();
            Bitmap bmImage = BitmapFactory.decodeStream(in);
            bmImages.add(bmImage);
            Log.d("bmImages add", "success!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
