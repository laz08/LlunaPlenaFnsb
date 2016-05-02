package laz.llunaplenafnsb.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import laz.llunaplenafnsb.R;
import laz.llunaplenafnsb.adapter.HomeEntriesAdapter;
import laz.llunaplenafnsb.adapter.OnEntryClickListener;
import laz.llunaplenafnsb.api.loader.FeedManagerCallbacks;
import laz.llunaplenafnsb.api.parsers.FeedLoaderCallback;
import laz.llunaplenafnsb.items.EntryItem;
import laz.llunaplenafnsb.items.Feed;

/**
 * Main activity.
 */
public class MainActivity extends AppCompatActivity implements FeedLoaderCallback, OnEntryClickListener {

    public static final String TAG = "MainActivity";

    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.toolbar_image)
    ImageView mToolbarImage;

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
        setUpToolbar();
        configureRecyclerView();

        final FeedManagerCallbacks callback = new FeedManagerCallbacks(getApplicationContext(), this);

        final LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.initLoader(LOADER_ID, null, callback);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {

                //TODO: Is there another way to do this?
                loaderManager.destroyLoader(LOADER_ID);
                loaderManager.initLoader(LOADER_ID, null, callback);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }


    /**
     * Sets up toolbar.
     */
    private void setUpToolbar() {

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {

            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.main_title);
        }

        mToolbarImage.setImageResource(R.drawable.header_toolbar_image);
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

        mAdapter = new HomeEntriesAdapter(this);
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

    @Override
    public void onEntryClick(EntryItem entry) {

        Log.v(TAG, "OnEntryClick!");
        openEntryDetailActivity(entry);
    }


    /**
     * Opens entry detail activity.
     */
    private void openEntryDetailActivity(EntryItem item) {

        Intent detailIntent = new Intent(this, EntryDetailActivity.class);
        detailIntent.putExtra(EXTRA_ENTRY_ITEM, item);
        detailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(detailIntent);
    }

    public static final String EXTRA_ENTRY_ITEM = "extraEntryItem";
    private static final int LOADER_ID = 42;

}
