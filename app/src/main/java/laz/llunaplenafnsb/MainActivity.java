package laz.llunaplenafnsb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.concurrent.ExecutionException;

import laz.llunaplenafnsb.api.FeedFetcherTask;
import laz.llunaplenafnsb.items.Feed;

/**
 * Main activity.
 */
public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fetchData();
    }

    /**
     * Fetchs rss feed data.
     */
    private void fetchData() {

        Feed feed = null;
        try {

            feed = new FeedFetcherTask(getApplicationContext()).execute().get();
        } catch (InterruptedException e) {

            e.printStackTrace();
        } catch (ExecutionException e) {

            e.printStackTrace();
        }

        if (feed != null) {

            loadData(feed);
        }
    }

    /**
     * Loads feed data into activity.
     *
     * @param feed Feed.
     */
    private void loadData(Feed feed) {

        Log.v(TAG, "Updated at: " + feed.getUpdated());
        Log.v(TAG, "Title: " + feed.getTitle());
        Log.v(TAG, "SubTitle: " + feed.getSubtitle());
        //TODO: Populate view.
    }
}
