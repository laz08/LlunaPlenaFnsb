package laz.llunaplenafnsb.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import laz.llunaplenafnsb.R;
import laz.llunaplenafnsb.helper.ImageLoaderHelper;
import laz.llunaplenafnsb.items.EntryItem;
import laz.llunaplenafnsb.items.ThumbnailItem;

/**
 * Entry detail activity.
 */
public class EntryDetailActivity extends AppCompatActivity {

    public static final String TAG = "EntryDetailActivity";

    @Bind(R.id.title_tv)
    TextView mTitle;

    @Bind(R.id.toolbar_image)
    ImageView mToolbarImage;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.description_tv)
    TextView mDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_detail);
        ButterKnife.bind(this);

        setUpToolbar();

        populateActivity();
    }

    /**
     * Sets up screen toolbar.
     */
    private void setUpToolbar() {

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {

            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(""); //No title!
        }

    }

    /**
     * Populates this activity.
     */
    private void populateActivity() {

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            EntryItem item = extras.getParcelable(MainActivity.EXTRA_ENTRY_ITEM);
            decorateView(item);
        } else {

            Log.w(TAG, "An item should have been sent.");
            finish();
        }
    }

    /**
     * Decorates this activity.
     *
     * @param item Entry item.
     */
    private void decorateView(EntryItem item) {

        mTitle.setText(item.getTitle());
        mDescription.setText(item.getSummary());

        ThumbnailItem thumb = item.getThumbnail();
        if (thumb != null) {

            ImageLoaderHelper.loadImageInto(this, mToolbarImage, thumb.getUrl());
        }
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
}
