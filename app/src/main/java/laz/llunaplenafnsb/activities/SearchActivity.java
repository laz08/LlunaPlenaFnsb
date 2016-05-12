package laz.llunaplenafnsb.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import laz.llunaplenafnsb.R;
import laz.llunaplenafnsb.adapter.HomeEntriesAdapter;
import laz.llunaplenafnsb.adapter.OnEntryClickListener;
import laz.llunaplenafnsb.api.loader.FeedLoaderManager;
import laz.llunaplenafnsb.items.EntryItem;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity implements OnEntryClickListener {

    public static final String TAG = "SearchActivity";

    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private HomeEntriesAdapter mAdapter;
    private ArrayList<EntryItem> mEntries;
    private FeedLoaderManager mLoaderManager;

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
    private void makeQuery(String query) {

        Call<ResponseBody> call = mLoaderManager.loadQuery(this, query);
        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {

                    List<EntryItem> posts = mLoaderManager.parsePosts(response.body().string());
                    if (posts != null) {

                        Log.v(TAG, "Posts size: " + posts.size());
                        mEntries.clear();
                        mEntries.addAll(posts);
                        mAdapter.setEntries(mEntries);
                        mAdapter.notifyDataSetChanged();

                    } else {

                        Log.v(TAG, "Posts null. Query did not return any result.");
                    }
                } catch (IOException e) {

                    Log.v(TAG, "Exception");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Log.v(TAG, "onFailure");
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
        mAdapter = new HomeEntriesAdapter(this);
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

            SearchView searchView = (SearchView) searchItem.getActionView();
            if (searchView != null) {

                searchView.setIconified(false);
                searchView.requestFocus();
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

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
}
