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

import xyz.miles.stime.bean.AdapterFollowUser;
import xyz.miles.stime.bean.AdapterFollowUserCopy;
import xyz.miles.stime.util.LoadMoreWrapper;

public class AddFollowUsersAsyncTask extends AsyncTask<AdapterFollowUserCopy, Integer, Void> {
    private LoadMoreWrapper wrapper;
    private List<AdapterFollowUser> adapterFollowUsers;

    public AddFollowUsersAsyncTask(LoadMoreWrapper wrapper, List<AdapterFollowUser> adapterFollowUsers) {
        this.wrapper = wrapper;
        this.adapterFollowUsers = adapterFollowUsers;
    }

    @Override
    protected Void doInBackground(AdapterFollowUserCopy... adapterFollowUserCopies) {
        AdapterFollowUserCopy userCopy = adapterFollowUserCopies[0];
        addData(userCopy);

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

    private void addData(AdapterFollowUserCopy userCopy) {
        AdapterFollowUser newUserData = new AdapterFollowUser();
        newUserData.username = userCopy.username;
        newUserData.followNum = userCopy.followNum;
        try {
            Log.d("follow user head url", userCopy.headUrl);
            URL url = new URL(userCopy.headUrl);
            URLConnection connection = url.openConnection();
            connection.connect();

            InputStream in;
            in = connection.getInputStream();
            Bitmap bmImage = BitmapFactory.decodeStream(in);
            newUserData.bmHead = bmImage;
            adapterFollowUsers.add(newUserData);
            Log.d("follow users adapter data add", "success!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
