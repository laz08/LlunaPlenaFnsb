package laz.llunaplenafnsb.activities;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import butterknife.Bind;
import butterknife.ButterKnife;
import laz.llunaplenafnsb.R;
import laz.llunaplenafnsb.adapter.HomeEntriesAdapter;
import laz.llunaplenafnsb.api.FeedManagerCallbacks;
import laz.llunaplenafnsb.api.parsers.FeedLoaderCallback;
import laz.llunaplenafnsb.items.Feed;

/**
 * Main activity.
 */
public class MainActivity extends AppCompatActivity implements FeedLoaderCallback {

    public static final String TAG = "MainActivity";

    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private HomeEntriesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
    }

    /**
     * Initializes the activity.
     */
    private void initialize() {

        ButterKnife.bind(this);

        configureRecyclerView();
        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.initLoader(LOADER_ID, null, new FeedManagerCallbacks(getApplicationContext(), this));
    }


    @Override
    public void onFeedLoaded(Feed feed) {

        loadData(feed);
    }

    /**
     * Configures recycler view.
     */
    private void configureRecyclerView() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new HomeEntriesAdapter();
        mRecyclerView.setAdapter(mAdapter);
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

        mAdapter.setEntries(feed.getEntries());
        mAdapter.notifyDataSetChanged();
    }


    private static final int LOADER_ID = 42;
}
