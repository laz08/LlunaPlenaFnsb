package laz.llunaplenafnsb.api.loader;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import laz.llunaplenafnsb.R;
import laz.llunaplenafnsb.api.ApiConstant;
import laz.llunaplenafnsb.api.parsers.EntryParser;
import laz.llunaplenafnsb.api.parsers.FeedParser;
import laz.llunaplenafnsb.items.EntryItem;
import laz.llunaplenafnsb.items.Feed;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

/**
 * Feed loader manager.
 */
public class FeedLoaderManager {

    public static final String TAG = "FeedLoaderManager";

    private static FeedLoaderManager mManager;
    private FeedLoaderService mFeedService;

    /**
     * Constructor.
     */
    private FeedLoaderManager(Context ctx) {

        Resources res = ctx.getResources();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(res.getString(R.string.blog_url))
                .build();

        mFeedService = retrofit.create(FeedLoaderService.class);
    }

    /**
     * Returns single instance.
     *
     * @return Feed loader manager instance.
     */
    public static FeedLoaderManager getInstance(Context ctx) {

        if (mManager == null) {

            mManager = new FeedLoaderManager(ctx);
        }

        return mManager;
    }


    public retrofit2.Call<ResponseBody> newLoadFeed(Context ctx) {

        return mFeedService.getFeed(ctx.getResources().getString(R.string.apiKey_blogger));
    }

    public retrofit2.Call<ResponseBody> newLoadEntries(Context ctx) {

        return mFeedService.getPosts(ctx.getResources().getString(R.string.apiKey_blogger));
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

    /**
     * Parses entries.
     *
     * @param entriesJSON Entries json.
     * @return
     */
    public List<EntryItem> parseEntries(String entriesJSON) {

        try {

            JSONObject json = new JSONObject(entriesJSON);
            if (json.has(ApiConstant.ITEMS)) {

                return EntryParser.parse(json.getJSONArray(ApiConstant.ITEMS));
            }

        } catch (JSONException e) {

            Log.v(TAG, "Error while parsing.");
        }
        return null;
    }
}
