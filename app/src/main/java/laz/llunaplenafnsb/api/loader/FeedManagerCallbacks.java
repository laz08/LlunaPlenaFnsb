package laz.llunaplenafnsb.api.loader;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;

import laz.llunaplenafnsb.api.parsers.FeedLoaderCallback;
import laz.llunaplenafnsb.items.Feed;

/**
 * Feed manager callbacks.
 */
public class FeedManagerCallbacks implements LoaderManager.LoaderCallbacks<Feed> {

    public static final String TAG = "FeedManagerCallbacks";

    private Context mContext;
    private FeedLoaderCallback mCallback;

    /**
     * Constructor.
     *
     * @param ctx Context.
     */
    public FeedManagerCallbacks(Context ctx, FeedLoaderCallback callback) {

        mContext = ctx;
        mCallback = callback;
    }

    @Override
    public Loader<Feed> onCreateLoader(int id, Bundle args) {

        return FeedLoader.getInstance(mContext);
    }

    @Override
    public void onLoadFinished(Loader<Feed> loader, Feed data) {

        Log.v(TAG, "On Load finished");
        mCallback.onFeedLoaded(data);
    }

    @Override
    public void onLoaderReset(Loader<Feed> loader) {

    }
}
