package laz.llunaplenafnsb.api;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import laz.llunaplenafnsb.R;
import laz.llunaplenafnsb.api.parsers.FeedParser;
import laz.llunaplenafnsb.items.Feed;

/**
 * Feed fetcher task.
 */
public class FeedFetcherTask extends AsyncTask<Void, Void, Feed> {

    public static final String TAG = "RESTManager";

    private static final String GET_METHOD = "GET";
    private static final int TIMEOUT = 500;

    private Context mContext;

    /**
     * Constructor.
     *
     * @param ctx Context.
     */
    public FeedFetcherTask(Context ctx) {

        mContext = ctx;
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
    protected Feed doInBackground(Void... voids) {

        String feedJSON = requestFeed();
        if (feedJSON != null) {

            try {

                JSONObject json = new JSONObject(feedJSON);
                Log.v(TAG, "Json has feed: " + json.has(ApiConstant.FEED));
                if (json.has(ApiConstant.FEED)) {

                    return FeedParser.parse(json.getJSONObject(ApiConstant.FEED));
                }
            } catch (JSONException e) {

                e.printStackTrace();
            }
        }
        return null;
    }
}
