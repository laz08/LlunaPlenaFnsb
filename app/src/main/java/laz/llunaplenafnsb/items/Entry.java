package laz.llunaplenafnsb.items;

import java.util.List;

/**
 * Entry.
 */
public class Entry {

    private String mTitle;
    private String mSummary;

    private List<Link> mLinks;
    private Author mAuthor;

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

    public Author getAuthor() {
        return mAuthor;
    }

    public void setAuthor(Author author) {
        mAuthor = author;
    }
}
