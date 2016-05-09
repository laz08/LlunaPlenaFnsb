package laz.llunaplenafnsb.items;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * AuthorItem.
 */
public class AuthorItem implements Parcelable {

    private String mName;
    private ThumbnailItem mThumbnail;
    private String mUrl; //Optional


    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public ThumbnailItem getThumbnail() {
        return mThumbnail;
    }

    public void setThumbnail(ThumbnailItem thumbnail) {
        mThumbnail = thumbnail;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(mName);
        dest.writeParcelable(mThumbnail, flags);
        dest.writeString(mUrl);
    }

    public AuthorItem() {
    }

    protected AuthorItem(Parcel in) {

        mName = in.readString();
        mThumbnail = in.readParcelable(ThumbnailItem.class.getClassLoader());
        mUrl = in.readString();
    }

    public static final Parcelable.Creator<AuthorItem> CREATOR = new Parcelable.Creator<AuthorItem>() {
        public AuthorItem createFromParcel(Parcel source) {
            return new AuthorItem(source);
        }

        public AuthorItem[] newArray(int size) {
            return new AuthorItem[size];
        }
    };
}
