package laz.llunaplenafnsb.items;

import java.util.List;

/**
 * Feed.
 */
public class Feed {

    private String mUpdated;

    private String mTitle;
    private String mSubtitle;

    private List<Link> mLinks;
    private List<EntryItem> mEntries;

    public String getUpdated() {
        return mUpdated;
    }

    public void setUpdated(String updated) {
        mUpdated = updated;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getSubtitle() {
        return mSubtitle;
    }

    public void setSubtitle(String subtitle) {
        mSubtitle = subtitle;
    }

    public List<EntryItem> getEntries() {
        return mEntries;
    }

    public void setEntries(List<EntryItem> entries) {
        mEntries = entries;
    }

    public List<Link> getLinks() {
        return mLinks;
    }

    public void setLinks(List<Link> links) {
        mLinks = links;
    }
}
