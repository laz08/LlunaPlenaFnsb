package laz.llunaplenafnsb.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import laz.llunaplenafnsb.R;
import laz.llunaplenafnsb.adapter.HomeEntriesAdapter;
import laz.llunaplenafnsb.adapter.OnFeedItemClickListener;
import laz.llunaplenafnsb.api.loader.FeedLoaderManager;
import laz.llunaplenafnsb.items.EntryItem;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity implements OnFeedItemClickListener {

    public static final String TAG = "SearchActivity";

    @Bind(R.id.coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;

    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Bind(R.id.no_entries_view)
    RelativeLayout mNoEntriesView;

    private ProgressDialog mProgressDialog;

    private HomeEntriesAdapter mAdapter;
    private ArrayList<EntryItem> mEntries;
    private FeedLoaderManager mLoaderManager;
    private SearchView mSearchView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {

            actionBar.setTitle(getResources().getText(R.string.search));
            actionBar.setDefaultDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ButterKnife.bind(this);

        configureRecyclerView();
        mLoaderManager = FeedLoaderManager.getInstance(this);
    }

    /**
     * Makes query.
     */
    private void makeQuery(final String query) {

        mProgressDialog = ProgressDialog.show(this, getResources().getString(R.string.loading), getResources().getString(R.string.wait), true, false);

        Call<ResponseBody> call = mLoaderManager.loadQuery(this, query);
        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                mProgressDialog.dismiss();
                mSearchView.clearFocus();
                try {

                    List<EntryItem> posts = mLoaderManager.parsePosts(response.body().string());
                    if (posts != null) {

                        mNoEntriesView.setVisibility(View.GONE);
                        mRecyclerView.setVisibility(View.VISIBLE);
                        Log.v(TAG, "Posts size: " + posts.size());
                        mEntries.clear();
                        mEntries.addAll(posts);
                        mAdapter.setEntries(mEntries);
                        mAdapter.notifyDataSetChanged();

                    } else {

                        showError(getResources().getString(R.string.no_results), query);
                        mNoEntriesView.setVisibility(View.VISIBLE);
                        Log.v(TAG, "Posts null. Query did not return any result.");
                    }
                } catch (IOException e) {

                    mNoEntriesView.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.GONE);
                    showError(getResources().getString(R.string.error), query);
                    Log.v(TAG, "Exception");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                mNoEntriesView.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.GONE);
                mProgressDialog.dismiss();
                mSearchView.clearFocus();
                Log.v(TAG, "onFailure");
                showError(getResources().getString(R.string.error_connection), query);
            }
        });
    }


    /**
     * Configures recycler view.
     */
    private void configureRecyclerView() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mEntries = new ArrayList<>();
        mAdapter = new HomeEntriesAdapter(this, false);
        mAdapter.setEntries(mEntries);

        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.search_item);

        if (searchItem != null) {

            mSearchView = (SearchView) searchItem.getActionView();
            if (mSearchView != null) {

                mSearchView.setIconified(false);
                mSearchView.requestFocus();
                mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                    @Override
                    public boolean onQueryTextSubmit(String query) {

                        makeQuery(query);
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        return false;
                    }
                });
            }
        }
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    /**
     * Shows snackbar message.
     */
    private void showError(String message, final String query) {

        Resources res = getResources();
        Snackbar snackbar = Snackbar
                .make(mCoordinatorLayout, message, Snackbar.LENGTH_INDEFINITE)
                .setAction(res.getString(R.string.retry), new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        makeQuery(query);
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

        Log.v(TAG, "OnEntryClick!");

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
}
