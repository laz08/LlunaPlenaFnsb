package laz.llunaplenafnsb.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import laz.llunaplenafnsb.R;
import laz.llunaplenafnsb.activities.EntryDetailActivity;
import laz.llunaplenafnsb.adapter.HomeEntriesAdapter;
import laz.llunaplenafnsb.adapter.OnFeedItemClickListener;
import laz.llunaplenafnsb.api.loader.FeedLoaderManager;
import laz.llunaplenafnsb.items.EntryItem;
import laz.llunaplenafnsb.items.Feed;
import laz.llunaplenafnsb.views.CustomSwipeRefreshLayout;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Home feed fragment.
 */
public class HomeFeedFragment extends Fragment implements OnFeedItemClickListener {

    public static final String TAG = "HomeFeedFragment";

    @Bind(R.id.swipe_refresh_layout)
    CustomSwipeRefreshLayout mSwipeRefreshLayout;

    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.toolbar_image)
    ImageView mToolbarImage;

    @Bind(R.id.coord_lay)
    CoordinatorLayout mCoordLayout;

    private static HomeFeedFragment mHomeFeedFragment;

    private HomeEntriesAdapter mAdapter;
    private ArrayList<EntryItem> mEntries;
    private Feed mFeed;

    /**
     * Returns instance of Home Feed fragment.
     *
     * @return Home feed instance.
     */
    public static HomeFeedFragment getInstance() {

        if (mHomeFeedFragment == null) {

            mHomeFeedFragment = new HomeFeedFragment();
        }
        return mHomeFeedFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home_feed, container, false);
        ButterKnife.bind(this, view);

        initialize();
        return view;
    }

    /**
     * Initializes the fragment.
     */
    private void initialize() {

        setUpToolbar();
        configureRecyclerView();

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {

                retrieveFeed();
            }
        });

        if (mFeed == null) {

            Log.v(TAG, "Saved instance IS null.");
            mSwipeRefreshLayout.setRefreshing(true);
            mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent);

            retrieveFeed();
        } else {

            loadData();
        }
    }


    /**
     * Registers feed loader.
     */
    private void retrieveFeed() {

        FeedLoaderManager manager = FeedLoaderManager.getInstance(getContext());
        final Context ctx = getContext();
        retrofit2.Call<ResponseBody> responseBodyCall = manager.loadFeed(ctx);
        responseBodyCall.enqueue(new retrofit2.Callback<ResponseBody>() {

            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

                onFeedResponse(response);
            }

            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {

                Log.v(TAG, "On failure");
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

        final FeedLoaderManager feedLoader = FeedLoaderManager.getInstance(getContext());
        mFeed = feedLoader.parseFeed(response.body().string());

        Call<ResponseBody> itemsCall = feedLoader.loadEntries(getContext());

        itemsCall.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    String body = response.body().string();
                    mFeed.setNextPageToken(feedLoader.parseNextPageToken(body));
                    mFeed.setPosts(feedLoader.parsePosts(body));
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
     * Sets up toolbar.
     */
    private void setUpToolbar() {

        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.setSupportActionBar(mToolbar);
        ActionBar actionBar = activity.getSupportActionBar();
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

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new HomeEntriesAdapter(this, true);
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

    @Override
    public void loadNewEntriesClick() {

        //TODO: Load new entries
        Log.v(TAG, "loadNewEntriesClick!");

    }


    /**
     * Opens entry detail activity.
     */
    private void openEntryDetailActivity(EntryItem item) {

        Intent detailPager = new Intent(getContext(), EntryDetailActivity.class);

        detailPager.putParcelableArrayListExtra(EntryDetailActivity.EXTRA_ENTRIES, mEntries);
        detailPager.putExtra(EntryDetailActivity.EXTRA_POSITION, mEntries.indexOf(item));
        detailPager.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(detailPager);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
        outState.putParcelable(FEED_SAVED_INSTANCE, mFeed);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {

            Log.v(TAG, "Saved instance not null.");
            mFeed = savedInstanceState.getParcelable(FEED_SAVED_INSTANCE);
        }
    }

    private static final String FEED_SAVED_INSTANCE = "feedSavedInstance";
}
