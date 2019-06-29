package xyz.miles.stime.service;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import xyz.miles.stime.bean.AdapterComment;
import xyz.miles.stime.bean.AdapterCommentCopy;
import xyz.miles.stime.util.LoadMoreWrapper;

public class AddCommentAsyncTask extends AsyncTask<AdapterCommentCopy, Integer, Void> {
    private List<AdapterComment> comments;
    private LoadMoreWrapper wrapper;

    public AddCommentAsyncTask(LoadMoreWrapper wrapper, List<AdapterComment> comments) {
        this.wrapper = wrapper;
        this.comments = comments;
    }

    @Override
    protected Void doInBackground(AdapterCommentCopy... adapterCommentCopies) {
        AdapterCommentCopy commentCopy = adapterCommentCopies[0];
        addData(commentCopy);

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        wrapper.notifyDataSetChanged();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    private void addData(AdapterCommentCopy commentCopy) {
        AdapterComment newComment = new AdapterComment();
        newComment.name = commentCopy.name;
        newComment.comment = commentCopy.comment;
        newComment.time = commentCopy.time;
        try {
            Log.d("comment user head url", commentCopy.headUrl);
            URL url = new URL(commentCopy.headUrl);
            URLConnection connection = url.openConnection();
            connection.connect();

            InputStream in;
            in = connection.getInputStream();
            Bitmap bmImage = BitmapFactory.decodeStream(in);
            newComment.head = bmImage;
            comments.add(newComment);
            Log.d("comment users adapter data add", "success!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
