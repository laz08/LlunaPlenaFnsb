package laz.llunaplenafnsb.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import laz.llunaplenafnsb.R;
import laz.llunaplenafnsb.helper.ImageLoaderHelper;
import laz.llunaplenafnsb.items.AuthorItem;

/**
 * Author view.
 */
public class AuthorView extends RelativeLayout {


    public static final String TAG = "AuthorView";

    @Bind(R.id.author_img)
    ImageView mAuthorImage;

    @Bind(R.id.author_name)
    TextView mAuthorName;
    private View mLayout;

    /**
     * Constructor.
     *
     * @param context Context.
     */
    public AuthorView(Context context) {

        super(context);
        initialize();
    }

    /**
     * Constructor.
     *
     * @param context Context.
     * @param attrs   Attribute set.
     */
    public AuthorView(Context context, AttributeSet attrs) {

        super(context, attrs);
        initialize();
    }


    /**
     * Populates this view.
     *
     * @param author Author item.
     */
    public void populateView(final AuthorItem author) {

        String imgURL = author.getThumbnail().getUrl();
        if (imgURL.startsWith("//")) {
            imgURL = "http://" + imgURL.substring(2, imgURL.length());
        }
        Log.v(TAG, "Img url: " + imgURL);
        ImageLoaderHelper.loadImageInto(getContext(), mAuthorImage, imgURL);
        mAuthorName.setText(author.getName());
        mLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.v(TAG, "OnClick");
                //TODO Open url
//                author.getUrl()
            }
        });
    }

    /**
     * Initializes this view.
     */
    private void initialize() {

        mLayout = inflate(getContext(), R.layout.view_author, this);
        ButterKnife.bind(this, mLayout);
    }

}
