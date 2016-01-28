package laz.llunaplenafnsb.api;

/**
 * Feed callback.
 */
public interface FeedCallback {

    /**
     * On feed received.
     */
    void onFeedReceived();

    /**
     * On error.
     */
    void onError();
}
