package laz.llunaplenafnsb.items;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * EntryItem.
 */
public class EntryItem implements Parcelable {

    private String mUpdated;

    private String mTitle;
    private String mContent;

    private AuthorItem mAuthor;

    private ThumbnailItem mThumbnail;

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

    public void setThumbnail(ThumbnailItem thumbnail) {

        mThumbnail = thumbnail;
    }

    public ThumbnailItem getThumbnail() {

        return mThumbnail;
    }

    /**
     * Determines if entry has image.
     *
     * @return True if entry has image. False otherwise.
     */
    public boolean hasImage() {

        return mThumbnail != null && mThumbnail.getUrl() != null && mThumbnail.getUrl().length() > 0;
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
        dest.writeParcelable(mThumbnail, 0);
    }

    public EntryItem() {
    }

    protected EntryItem(Parcel in) {
        mUpdated = in.readString();
        mTitle = in.readString();
        mContent = in.readString();
//        mLinks = new ArrayList<Link>();
//        in.readList(mLinks, List.class.getClassLoader());
        mAuthor = in.readParcelable(AuthorItem.class.getClassLoader());
        mThumbnail = in.readParcelable(ThumbnailItem.class.getClassLoader());
    }

    public static final Parcelable.Creator<EntryItem> CREATOR = new Parcelable.Creator<EntryItem>() {
        public EntryItem createFromParcel(Parcel source) {
            return new EntryItem(source);
        }

        public EntryItem[] newArray(int size) {
            return new EntryItem[size];
        }
    };
}
