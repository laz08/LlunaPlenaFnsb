package laz.llunaplenafnsb.api.loader;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import laz.llunaplenafnsb.R;
import laz.llunaplenafnsb.api.ApiConstant;
import laz.llunaplenafnsb.api.parsers.FeedParser;
import laz.llunaplenafnsb.items.Feed;

/**
 * Feed fetcher task.
 */

//https://medium.com/google-developers/making-loading-data-on-android-lifecycle-aware-897e12760832#.x4dgmnp90
public class FeedLoader extends AsyncTaskLoader<Feed> {

    public static final String TAG = "FeedLoader";

    public static FeedLoader mLoader;

    private Context mContext;
    private Feed mData;


    /**
     * Returns instance.
     *
     * @param ctx Context.
     * @return Feed loader.
     */
    public static FeedLoader getInstance(Context ctx) {

        if (mLoader == null) {

            mLoader = new FeedLoader(ctx);
        }

        return mLoader;
    }

    /**
     * Constructor.
     *
     * @param ctx Context.
     */
    private FeedLoader(Context ctx) {

        super(ctx);
        mContext = ctx;
    }

    @Override
    protected void onStartLoading() {

        if (mData != null) {

            deliverResult(mData);
        } else {

            forceLoad();
        }
    }

    @Override
    public Feed loadInBackground() {

        String feedJSON = requestFeed();
        if (feedJSON != null) {

            try {

                JSONObject json = new JSONObject(feedJSON);
                Log.v(TAG, "Json has feed: " + json.has(ApiConstant.FEED));
                if (json.has(ApiConstant.FEED)) {

                    mData = FeedParser.parse(json.getJSONObject(ApiConstant.FEED));
                }
            } catch (JSONException e) {

                e.printStackTrace();
            }
        }

        return mData;
    }

    /**
     * Requests main feed.
     */
    private String requestFeed() {

        Resources res = mContext.getResources();

        HttpURLConnection connection = null;

        try {

            String requestURL = res.getString(R.string.baseURL) + res.getString(R.string.rssJson);
            Log.v(TAG, "Request URL: " + requestURL);
            URL url = new URL(requestURL);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(GET_METHOD);
            connection.setUseCaches(false);
            connection.setConnectTimeout(TIMEOUT);
            connection.connect();

            if (hasConnectedCorrectly(connection)) {

                Log.v(TAG, "Connected correctly.");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();

                String responseLine;
                while ((responseLine = bufferedReader.readLine()) != null) {

                    stringBuilder.append(responseLine + "\n");
                }

                bufferedReader.close();
                return stringBuilder.toString();


            } else {

                Log.v(TAG, "unable to connect.");
            }

        } catch (IOException e) {

            e.printStackTrace();
        } finally {

            if (connection != null) {

                connection.disconnect();
            }
        }

        return null;
    }

    /**
     * Determines if connection has been successful.
     *
     * @param connection Connection.
     * @return True if connection has been successful. False otherwise.
     * @throws IOException
     */
    private boolean hasConnectedCorrectly(HttpURLConnection connection) throws IOException {

        return connection.getResponseCode() == 200 || connection.getResponseCode() == 201;
    }


    @Override
    public void deliverResult(Feed data) {

        mData = data;
        super.deliverResult(data);
    }


    private static final String GET_METHOD = "GET";
    private static final int TIMEOUT = 500;

}
