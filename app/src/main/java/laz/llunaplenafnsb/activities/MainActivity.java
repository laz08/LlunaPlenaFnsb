package laz.llunaplenafnsb.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import laz.llunaplenafnsb.R;
import laz.llunaplenafnsb.adapter.HomeEntriesAdapter;
import laz.llunaplenafnsb.adapter.OnEntryClickListener;
import laz.llunaplenafnsb.api.loader.FeedLoaderManager;
import laz.llunaplenafnsb.items.EntryItem;
import laz.llunaplenafnsb.items.Feed;
import laz.llunaplenafnsb.views.CustomSwipeRefreshLayout;
import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * Main activity.
 */
public class MainActivity extends AppCompatActivity implements OnEntryClickListener {

    public static final String TAG = "MainActivity";

    @Bind(R.id.swipe_refresh_layout)
    CustomSwipeRefreshLayout mSwipeRefreshLayout;

    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.toolbar_image)
    ImageView mToolbarImage;

    @Bind(R.id.drawer)
    DrawerLayout mDrawerLayout;

    @Bind(R.id.navigation_view)
    NavigationView mNavigationView;

    @Bind(R.id.coord_lay)
    CoordinatorLayout mCoordLayout;

    private HomeEntriesAdapter mAdapter;
    private ArrayList<EntryItem> mEntries;
    private Feed mFeed;


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

        mSwipeRefreshLayout.setRefreshing(true);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent);

        retrieveFeed();

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {

                retrieveFeed();
            }
        });

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                return onItemDrawerSelected(item);
            }
        });
    }

    /**
     * Registers feed loader.
     */
    private void retrieveFeed() {

        FeedLoaderManager manager = FeedLoaderManager.getInstance(this);
        final Context ctx = getApplicationContext();
        retrofit2.Call<ResponseBody> responseBodyCall = manager.newLoadFeed(ctx);
        responseBodyCall.enqueue(new retrofit2.Callback<ResponseBody>() {

            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

                onFeedResponse(response);
            }

            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {

                Log.v(TAG, "On failure");
                //TODO: Show error
                manageFailure();

            }
        });
    }

    /**
     * On feed response.
     *
     * @param response Response.
     */
    private void onFeedResponse(retrofit2.Response<ResponseBody> response) {

        Log.v(TAG, "On response");

        try {

            retrievePosts(response);

        } catch (IOException e) {

            manageFailure(e);
        }
    }

    /**
     * Retrieves posts from the blog.
     *
     * @param response Response body.
     * @throws IOException IO Exception.
     */
    private void retrievePosts(Response<ResponseBody> response) throws IOException {

        FeedLoaderManager feedLoader = FeedLoaderManager.getInstance(this);
        mFeed = feedLoader.parseFeed(response.body().string());
        retrofit2.Call<ResponseBody> itemsCall = feedLoader.newLoadEntries(getApplicationContext());
        itemsCall.enqueue(new retrofit2.Callback<ResponseBody>() {

            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call, Response<ResponseBody> response) {

                try {

                    mFeed.setPosts(FeedLoaderManager.getInstance(getApplicationContext()).parseEntries(response.body().string()));
                    loadData();
                } catch (IOException e) {

                    Log.v(TAG, "Error while getting response.");
                    manageFailure(e);
                }
            }

            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {

                Log.v(TAG, "On failure");
                manageFailure();
            }
        });
    }


    /**
     * Manages failure on petitions.
     *
     * @param e Exception.
     */
    private void manageFailure(IOException e) {

        e.printStackTrace();
        manageFailure();
    }


    /**
     * Manages failure on petitions.
     */
    private void manageFailure() {

        Log.v(TAG, "OnFailure");
        showError();
        mSwipeRefreshLayout.setRefreshing(false);
    }


    /**
     * On item drawer selected.
     *
     * @param item Menu item selected.
     * @return Returns true if it has managed the click on the item. False otherwise.
     */

    private boolean onItemDrawerSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {

            case R.id.drawer_home:
                return true;

            case R.id.drawer_forum:

                openForum();
                mDrawerLayout.closeDrawers();
                return true;

            case R.id.drawer_config:

                startSettingsActivity();
                mDrawerLayout.closeDrawers();
                return true;

            case R.id.drawer_about:
                //TODO: Start about screen

            case R.id.drawer_dev:
                //TODO: Start about developer screen


            default:
                mDrawerLayout.closeDrawers();
        }
        return false;
    }

    /**
     * Opens forum.
     */
    private void openForum() {

        String forumUrl = getResources().getString(R.string.forum_url);

        Intent browserIntent = new Intent(Intent.ACTION_VIEW);
        browserIntent.setData(Uri.parse(forumUrl));
        startActivity(browserIntent);
    }

    /**
     * Starts settings activity.
     */
    private void startSettingsActivity() {

        Intent settingsAct = new Intent(this, SettingsActivity.class);
        startActivity(settingsAct);
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
     */
    private void loadData() {

        mSwipeRefreshLayout.setRefreshing(false);

        if (mFeed != null) {

            Log.v(TAG, "Updated at: " + mFeed.getUpdated());
            Log.v(TAG, "Title: " + mFeed.getName());
            Log.v(TAG, "Description: " + mFeed.getDescription());
            Log.v(TAG, "Posts size: " + mFeed.getPosts().size());

            mEntries = new ArrayList<>(mFeed.getPosts());
            mAdapter.setEntries(mEntries);
            mAdapter.notifyDataSetChanged();
        } else {

            Log.w(TAG, "Error while retrieving the feed. Feed is null!");
            showError();
        }
    }

    /**
     * Shows error when feed could not be loaded.
     */
    private void showError() {

        Resources res = getResources();
        Snackbar snackbar = Snackbar
                .make(mCoordLayout, res.getString(R.string.error_feed_null), Snackbar.LENGTH_INDEFINITE)
                .setAction(res.getString(R.string.retry), new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        mSwipeRefreshLayout.setRefreshing(true);
                        retrieveFeed();
                    }
                });
        snackbar.show();
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

        Intent detailPager = new Intent(this, EntryDetailActivity.class);

        detailPager.putParcelableArrayListExtra(EntryDetailActivity.EXTRA_ENTRIES, mEntries);
        detailPager.putExtra(EntryDetailActivity.EXTRA_POSITION, mEntries.indexOf(item));
        detailPager.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(detailPager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {

        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {

            mDrawerLayout.closeDrawers();
        } else {

            super.onBackPressed();
        }
    }


    @Override
    protected void onResume() {

        super.onResume();
        mNavigationView.setCheckedItem(R.id.drawer_home);
    }
}
