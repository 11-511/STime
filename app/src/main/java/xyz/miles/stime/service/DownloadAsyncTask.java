package xyz.miles.stime.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import xyz.miles.stime.util.ImageAdapter;
import xyz.miles.stime.util.LoadMoreWrapper;

public class DownloadAsyncTask extends AsyncTask<List<String>, Integer, Void> {
    private RecyclerView recyclerView;
    private List<Bitmap> bmImages;
    private ImageAdapter adapter;

    private SwipeRefreshLayout swipeRefreshLayout;
    private LoadMoreWrapper wrapper;

    private Context context;

    private int numPerPage = 6;
    private int HIGHT_MAX = 0;
    private int page;

    public DownloadAsyncTask(Context context, SwipeRefreshLayout swipeRefreshLayout,
                             RecyclerView recyclerView,
                             LoadMoreWrapper wrapper, ImageAdapter adapter, List<Bitmap> bmImages) {
        this.context = context;
        this.swipeRefreshLayout = swipeRefreshLayout;
        this.recyclerView = recyclerView;
        this.wrapper = wrapper;
        this.adapter = adapter;
        this.bmImages = bmImages;
    }

    @Override
    protected Void doInBackground(List<String>... lists) {
        bmImages = new ArrayList<>();
        List<String> imagesUrl = lists[0];
        int low = 0;
        int high = imagesUrl.size() > 6 ? 6 : imagesUrl.size();
        for (int i = low; i < high; ++i) {
            try {
                String imageUrl = imagesUrl.get(i);
                Log.d("do in back ground get image url", imageUrl);
                URL url = new URL(imageUrl);
                URLConnection connection = url.openConnection();
                connection.connect();

                InputStream in;
                in = connection.getInputStream();
                Bitmap bmImage = BitmapFactory.decodeStream(in);
                bmImages.add(bmImage);
                Log.d("bmImages add", "success!" + " " + (i + 1));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        initAdapter();
    }

    private void initAdapter()
    {
        adapter = new ImageAdapter(this.context,bmImages);
        wrapper = new LoadMoreWrapper(adapter);

        recyclerView.setLayoutManager(new GridLayoutManager(this.context,2));
        recyclerView.setAdapter(wrapper);
    }

    // 进度条更新

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }
}
