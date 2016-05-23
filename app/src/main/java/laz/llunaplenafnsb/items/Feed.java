package laz.llunaplenafnsb.items;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Feed.
 */
public class Feed implements Parcelable {

    private String mName;

    private String mDescription;
    private String mUrl;

    private String mUpdated;

    private List<EntryItem> mPosts;
    private String mPostsUrl;
    private String mNextPageToken;

    /**
     * Constructor.
     */
    public Feed() {

    }

    /**
     * Constructor from parcelable.
     *
     * @param in Parcel in.
     */
    protected Feed(Parcel in) {

        mName = in.readString();
        mDescription = in.readString();
        mUrl = in.readString();
        mUpdated = in.readString();
        mPosts = in.createTypedArrayList(EntryItem.CREATOR);
        mPostsUrl = in.readString();
        mNextPageToken = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(mName);
        parcel.writeString(mDescription);
        parcel.writeString(mUrl);
        parcel.writeString(mUpdated);
        parcel.writeTypedList(mPosts);
        parcel.writeString(mPostsUrl);
        parcel.writeString(mNextPageToken);
    }

    public static final Creator<Feed> CREATOR = new Creator<Feed>() {

        @Override
        public Feed createFromParcel(Parcel in) {

            return new Feed(in);
        }

        @Override
        public Feed[] newArray(int size) {

            return new Feed[size];
        }
    };

    public String getPostsUrl() {
        return mPostsUrl;
    }

    public void setPostsUrl(String url) {

        mPostsUrl = url;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getUpdated() {
        return mUpdated;
    }

    public void setUpdated(String updated) {
        mUpdated = updated;
    }

    public List<EntryItem> getPosts() {
        return mPosts;
    }

    public void setPosts(List<EntryItem> posts) {
        mPosts = posts;
    }


    @Override
    public int describeContents() {

        return 0;
    }

    public String getNextPageToken() {
        return mNextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        mNextPageToken = nextPageToken;
    }

    /**
     * Adds new posts.
     *
     * @param entryItems Entry items.
     */
    public void addPosts(List<EntryItem> entryItems) {

        if (mPosts == null) {

            mPosts = new ArrayList<>();
        }

        mPosts.addAll(entryItems);
    }

}
