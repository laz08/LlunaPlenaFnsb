package laz.llunaplenafnsb.items;

import java.util.List;

/**
 * Feed.
 */
public class Feed {


    private String mName;

    private String mDescription;
    private String mUrl;

    private String mUpdated;


    private List<EntryItem> mPosts;
    private String mPostsUrl;

    public String getPostsUrl() {
        return mPostsUrl;
    }
    public void setPostsUrl(String url){

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

}
