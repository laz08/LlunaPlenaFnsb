package laz.llunaplenafnsb.api.parsers;

import laz.llunaplenafnsb.items.Feed;

/**
 * Feed loader callback.
 */
public interface FeedLoaderCallback {

    void onFeedLoaded(Feed feed);
}
