package laz.llunaplenafnsb.items;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ThumbnailItem.
 */
public class ThumbnailItem implements Parcelable {


    private String mUrl;

    /**
     * Constructor.
     */
    public ThumbnailItem() {

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

        dest.writeString(mUrl);
    }

    /**
     * Parcelable constructor.
     *
     * @param in Parcel input.
     */
    protected ThumbnailItem(Parcel in) {
        
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
