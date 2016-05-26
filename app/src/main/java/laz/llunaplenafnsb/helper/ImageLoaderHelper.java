package laz.llunaplenafnsb.helper;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import laz.llunaplenafnsb.R;

/**
 * Image loader helper.
 */
public class ImageLoaderHelper {

    public static final String TAG = "ImageLoaderHelper";

    /**
     * Loads image from URL into imageview.
     *
     * @param ctx       Context.
     * @param imageView ImageView.
     * @param url       URL.
     */
    public static void loadImageInto(Context ctx, final ImageView imageView, String url) {

        //TODO: PlaceholdeR?
        Picasso.with(ctx).load(url).error(R.drawable.ic_error_black_24dp).into(imageView);
    }
}
