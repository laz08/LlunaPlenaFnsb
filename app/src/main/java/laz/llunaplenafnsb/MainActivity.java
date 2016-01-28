package laz.llunaplenafnsb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import laz.llunaplenafnsb.api.FeedFetcher;

/**
 * Main activity.
 */
public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FeedFetcher manager = new FeedFetcher(getApplicationContext());
        manager.execute();
    }
}
