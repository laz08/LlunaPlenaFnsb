package laz.llunaplenafnsb.api.loader;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import laz.llunaplenafnsb.R;
import laz.llunaplenafnsb.api.ApiConstant;
import laz.llunaplenafnsb.api.parsers.FeedParser;
import laz.llunaplenafnsb.items.Feed;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Feed loader manager.
 */
public class FeedLoader {

    public static final String TAG = "FeedLoader";

    private static FeedLoader mManager;
    private OkHttpClient mHttpClient;

    /**
     * Constructor.
     */
    private FeedLoader() {

        mHttpClient = new OkHttpClient();
    }

    /**
     * Returns single instance.
     *
     * @return Feed loader manager instance.
     */
    public static FeedLoader getInstance() {

        if (mManager == null) {

            mManager = new FeedLoader();
        }

        return mManager;
    }

    /**
     * Loads feed.
     *
     * @return Feed. Null if could not load the feed.
     */
    public void loadFeed(Context ctx, Callback callback) {

        Resources res = ctx.getResources();
//        String requestURL = res.getString(R.string.baseURL) + res.getString(R.string.rssJson);
        String requestURL = res.getString(R.string.blog_url) + "?key=" + res.getString(R.string.apiKey_blogger);
        Log.v(TAG, "Request URL: " + requestURL);

        Request req = new Request.Builder()
                .url(requestURL)
                .build();

        Call call = mHttpClient.newCall(req);
        call.enqueue(callback);
    }


    /**
     * Parses feed string.
     *
     * @param feedJSON Json feed as string.
     * @return Parsed feed.
     */
    public Feed parseFeed(String feedJSON) {

        try {

            JSONObject json = new JSONObject(feedJSON);
            return FeedParser.parse(json);

        } catch (JSONException e) {

            Log.v(TAG, "Error while parsing.");
        }
        return null;
    }

}
