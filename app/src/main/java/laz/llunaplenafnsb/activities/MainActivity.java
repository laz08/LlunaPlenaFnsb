package laz.llunaplenafnsb.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import java.util.concurrent.ExecutionException;

import butterknife.Bind;
import laz.llunaplenafnsb.R;
import laz.llunaplenafnsb.api.FeedFetcherTask;
import laz.llunaplenafnsb.items.Feed;

/**
 * Main activity.
 */
public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Bind(R.id.headerImage)
    ImageView mHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
        fetchData();
    }

    private void initialize() {

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
