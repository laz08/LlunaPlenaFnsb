package laz.llunaplenafnsb.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import laz.llunaplenafnsb.R;
import laz.llunaplenafnsb.items.EntryItem;

/**
 * Entry detail activity.
 */
public class EntryDetailActivity extends AppCompatActivity {

    public static final String TAG = "EntryDetailActivity";

    @Bind(R.id.title_tv)
    TextView mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_detail);
        ButterKnife.bind(this);

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
    }
}
