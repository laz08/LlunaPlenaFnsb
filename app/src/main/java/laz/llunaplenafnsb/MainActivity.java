package laz.llunaplenafnsb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import laz.llunaplenafnsb.api.ApiRestManager;
import laz.llunaplenafnsb.api.FeedCallback;

/**
 * Main activity.
 */
public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ApiRestManager manager = new ApiRestManager(getApplicationContext());
        manager.getMainFeed(new FeedCallback() {

            @Override
            public void onFeedReceived() {

                Log.v(TAG, "Feed received");
            }

            @Override
            public void onError() {

                Log.v(TAG, "Error while getting feed.");
            }
        });
    }
}
