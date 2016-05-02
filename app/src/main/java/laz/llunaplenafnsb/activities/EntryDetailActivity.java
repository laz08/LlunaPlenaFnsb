package laz.llunaplenafnsb.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import laz.llunaplenafnsb.R;
import laz.llunaplenafnsb.activities.detail.EntryPagerAdapter;
import laz.llunaplenafnsb.items.EntryItem;

/**
 * Entry detail activity.
 */
public class EntryDetailActivity extends AppCompatActivity {

    public static final String TAG = "EntryDetailViewPager";

    @Bind(R.id.back_icon)
    ImageView mBack;

    @Bind(R.id.viewPager)
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_details_viewpager);
        ButterKnife.bind(this);

        mBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });
        
        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            ArrayList<EntryItem> entries = extras.getParcelableArrayList(EXTRA_ENTRIES);
            int pos = extras.getInt(EXTRA_POSITION);

            EntryPagerAdapter adapter = new EntryPagerAdapter(getSupportFragmentManager());
            adapter.setItems(entries);
            mViewPager.setAdapter(adapter);
            mViewPager.setCurrentItem(pos);

        } else {

            Log.w(TAG, "Entries should have been passed.");
            finish();
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

    public static final String EXTRA_ENTRIES = "extraEntries";
    public static final String EXTRA_POSITION = "extraPosition";
}
