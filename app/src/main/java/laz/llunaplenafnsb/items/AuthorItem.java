package laz.llunaplenafnsb.items;

/**
 * AuthorItem.
 */
public class AuthorItem {

    private String mName;
    private String mEmail;
    private ImageItem mThumbnail;
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

    public ImageItem getThumbnail() {
        return mThumbnail;
    }

    public void setThumbnail(ImageItem thumbnail) {
        mThumbnail = thumbnail;
    }

    public String getUri() {
        return mUri;
    }

    public void setUri(String uri) {
        mUri = uri;
    }
}
