package laz.llunaplenafnsb.items;

import java.util.List;

/**
 * EntryItem.
 */
public class EntryItem {

    private String mUpdated;

    private String mTitle;
    private String mSummary;

    private List<Link> mLinks;
    private AuthorItem mAuthor;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }

    public List<Link> getLinks() {
        return mLinks;
    }

    public void setLinks(List<Link> links) {
        mLinks = links;
    }

    public AuthorItem getAuthor() {
        return mAuthor;
    }

    public void setAuthor(AuthorItem author) {
        mAuthor = author;
    }

    public String getUpdated() {
        return mUpdated;
    }

    public void setUpdated(String updated) {
        mUpdated = updated;
    }

}
