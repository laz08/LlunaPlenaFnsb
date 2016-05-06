package laz.llunaplenafnsb.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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
import laz.llunaplenafnsb.api.loader.FeedLoader;
import laz.llunaplenafnsb.items.EntryItem;
import laz.llunaplenafnsb.items.Feed;
import laz.llunaplenafnsb.views.CustomSwipeRefreshLayout;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

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

        registerLoader();

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {

                registerLoader();
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
    private void registerLoader() {

        FeedLoader manager = FeedLoader.getInstance();
        final Context ctx = getApplicationContext();
        manager.loadFeed(this, new Callback() {

            Handler handler = new Handler(ctx.getMainLooper());

            @Override
            public void onFailure(Call call, final IOException e) {

                handler.post(new Runnable() {

                    @Override
                    public void run() {

                        manageFailure(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                handler.post(new Runnable() {

                    @Override
                    public void run() {

                        manageResponse(response);
                    }
                });

            }
        });
    }

    /**
     * Manages response.
     *
     * @param response Response.
     */
    private void manageResponse(Response response) {

        if (response.isSuccessful()) {

            loadData(FeedLoader.getInstance().parseFeed(response.body().toString()));

        } else {

            Log.v(TAG, "Response not successful!");
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    /**
     * Manages failure on petitions.
     *
     * @param e Exception.
     */
    private void manageFailure(IOException e) {

        Log.v(TAG, "OnFailure");
        e.printStackTrace();
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
     *
     * @param feed Feed.
     */
    private void loadData(Feed feed) {

        mSwipeRefreshLayout.setRefreshing(false);

        if (feed != null) {

            Log.v(TAG, "Updated at: " + feed.getUpdated());
            Log.v(TAG, "Title: " + feed.getTitle());
            Log.v(TAG, "SubTitle: " + feed.getSubtitle());

            mEntries = new ArrayList<>(feed.getEntries());
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
                        registerLoader();
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
