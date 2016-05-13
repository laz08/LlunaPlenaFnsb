package laz.llunaplenafnsb.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebView;

import butterknife.Bind;
import butterknife.ButterKnife;
import laz.llunaplenafnsb.R;

public class WebPageActivity extends AppCompatActivity {

    public static final String TAG = "WebPageActivity";

    @Bind(R.id.embedded_webview)
    WebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webpage);

        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            String url = extras.getString(EXTRA_URL);
            if (url != null) {

                mWebView.loadUrl(url);
            }

            String title = extras.getString(EXTRA_TITLE);

            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {

                if (title != null) {

                    actionBar.setTitle(title);
                } else {

                    Log.v(TAG, "Title is null");
                    actionBar.setTitle("");
                }
                actionBar.setDefaultDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayHomeAsUpEnabled(true);
            }

        } else {

            Log.e(TAG, "An URL must be sent via the intent extras!");
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

    public static final String EXTRA_URL = "extraURL";
    public static final String EXTRA_TITLE = "extraTitle";

}
