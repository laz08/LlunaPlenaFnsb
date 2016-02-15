package laz.llunaplenafnsb.items;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * AuthorItem.
 */
public class AuthorItem implements Parcelable {

    private String mName;
    private String mEmail;
    private ThumbnailItem mThumbnail;
    private String mUri; //Optional


    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public ThumbnailItem getThumbnail() {
        return mThumbnail;
    }

    public void setThumbnail(ThumbnailItem thumbnail) {
        mThumbnail = thumbnail;
    }

    public String getUri() {
        return mUri;
    }

    public void setUri(String uri) {
        mUri = uri;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(mName);
        dest.writeString(mEmail);
        dest.writeParcelable(mThumbnail, flags);
        dest.writeString(mUri);
    }

    public AuthorItem() {
    }

    protected AuthorItem(Parcel in) {

        mName = in.readString();
        mEmail = in.readString();
        mThumbnail = in.readParcelable(ThumbnailItem.class.getClassLoader());
        mUri = in.readString();
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
