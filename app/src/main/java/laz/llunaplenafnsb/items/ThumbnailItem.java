package laz.llunaplenafnsb.items;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ThumbnailItem.
 */
public class ThumbnailItem implements Parcelable {

    private int mWidth;
    private int mHeight;
    private String mUrl;

    /**
     * Constructor.
     */
    public ThumbnailItem() {

    }

    public int getWidth() {
        return mWidth;
    }

    public void setWidth(int width) {
        mWidth = width;
    }

    public int getHeight() {
        return mHeight;
    }

    public void setHeight(int height) {
        mHeight = height;
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

        dest.writeInt(mWidth);
        dest.writeInt(mHeight);
        dest.writeString(mUrl);
    }

    /**
     * Parcelable constructor.
     *
     * @param in Parcel input.
     */
    protected ThumbnailItem(Parcel in) {

        mWidth = in.readInt();
        mHeight = in.readInt();
        mUrl = in.readString();
    }

    /**
     * Parcelable creator.
     */
    public static final Parcelable.Creator<ThumbnailItem> CREATOR = new Parcelable.Creator<ThumbnailItem>() {

        public ThumbnailItem createFromParcel(Parcel source) {

            return new ThumbnailItem(source);
        }

        public ThumbnailItem[] newArray(int size) {

            return new ThumbnailItem[size];
        }
    };
}
