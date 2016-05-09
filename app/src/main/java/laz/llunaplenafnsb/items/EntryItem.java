package laz.llunaplenafnsb.items;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.Html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * EntryItem.
 */
public class EntryItem implements Parcelable {

    private String mUpdated;

    private String mTitle;
    private String mContent;

    private AuthorItem mAuthor;


    /**
     * Constructor.
     */
    public EntryItem() {

    }

    /**
     * Parses img from tags.
     *
     * @return Img url from content.
     */
    public String getImgFromContent() {

        if (mContent != null) {

            Document doc = Jsoup.parse(mContent);
            return doc.getElementsByTag("img").attr("src");
        }
        return null;
    }

    /**
     * Returns content as text (no tags).o
     *
     * @return Context parsed.
     */
    public String getContentAsText() {

        if (mContent != null) {

            Document doc = Jsoup.parse(mContent);
            return doc.text();
        }
        return null;
    }

    /**
     * Returns content as text (no tags).o
     *
     * @return Context parsed.
     */
    public String getContentAsFormattedText() {

        if (mContent != null) {

            return Html.fromHtml(mContent).toString();
        }
        return null;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mUpdated);
        dest.writeString(mTitle);
        dest.writeString(mContent);
        dest.writeParcelable(mAuthor, 0);
    }

    /**
     * Constructor from parcel.
     *
     * @param in Parcel in.
     */
    protected EntryItem(Parcel in) {

        mUpdated = in.readString();
        mTitle = in.readString();
        mContent = in.readString();
        mAuthor = in.readParcelable(AuthorItem.class.getClassLoader());
    }

    public static final Parcelable.Creator<EntryItem> CREATOR = new Parcelable.Creator<EntryItem>() {
        public EntryItem createFromParcel(Parcel source) {
            return new EntryItem(source);
        }

        public EntryItem[] newArray(int size) {
            return new EntryItem[size];
        }
    };


    //Getters and setters.
    //TODO: Remove unused ones...

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
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
