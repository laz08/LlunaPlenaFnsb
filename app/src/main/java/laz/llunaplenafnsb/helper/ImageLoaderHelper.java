package laz.llunaplenafnsb.helper;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ImageLoaderHelper {

    /**
     * Loads image from URL into imageview.
     *
     * @param ctx       Context.
     * @param imageView ImageView.
     * @param url       URL.
     */
    public static void loadImageInto(Context ctx, ImageView imageView, String url) {

        //TODO: PlaceholdeR?
        Picasso.with(ctx).load(url).into(imageView);
    }
}
