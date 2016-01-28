package laz.llunaplenafnsb.api;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import laz.llunaplenafnsb.R;
import laz.llunaplenafnsb.items.FeedItem;

public class FeedFetcher extends AsyncTask<Void, Void, FeedItem> {

    public static final String TAG = "RESTManager";

    private static final String GET_METHOD = "GET";
    private static final int TIMEOUT = 500;

    private Context mContext;

    /**
     * Constructor.
     *
     * @param ctx Context.
     */
    public FeedFetcher(Context ctx) {

        mContext = ctx;
    }


    /**
     * Requests main feed.
     */
    private void getMainFeed() {

        Resources res = mContext.getResources();

        String requestURL = res.getString(R.string.baseURL) + res.getString(R.string.rssJson);

        HttpURLConnection connection = null;

        try {

            URL url = new URL(requestURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(GET_METHOD);
            connection.setUseCaches(false);
            connection.setConnectTimeout(TIMEOUT);
            connection.connect();

            if (hasConnectedCorrectly(connection)) {

                Log.v(TAG, "Connected correctly.");

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
    }

    private boolean hasConnectedCorrectly(HttpURLConnection connection) throws IOException {

        return connection.getResponseCode() == 200 || connection.getResponseCode() == 201;
    }

    @Override
    protected FeedItem doInBackground(Void... voids) {

        getMainFeed();
        return new FeedItem();
    }
}
