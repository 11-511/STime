package xyz.miles.stime.service;

import android.os.AsyncTask;

import java.util.List;

import xyz.miles.stime.util.Comment;
import xyz.miles.stime.util.LoadMoreWrapper;

public class AddCommentAsyncTask extends AsyncTask<String, Integer, Void> {
    private List<Comment> comments;
    private LoadMoreWrapper wrapper;

    public AddCommentAsyncTask(LoadMoreWrapper wrapper, List<Comment> comments) {
        this.wrapper = wrapper;
        this.comments = comments;
    }

    @Override
    protected Void doInBackground(String... strings) {
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }
}
