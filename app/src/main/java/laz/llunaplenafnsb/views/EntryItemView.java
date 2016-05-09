package laz.llunaplenafnsb.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import laz.llunaplenafnsb.R;
import laz.llunaplenafnsb.adapter.OnEntryClickListener;
import laz.llunaplenafnsb.helper.ImageLoaderHelper;
import laz.llunaplenafnsb.items.EntryItem;

/**
 * EntryItem item view.
 */
public class EntryItemView extends FrameLayout {

    public static final String TAG = "EntryItemView";

    @Bind(R.id.entry_title)
    TextView mTitle;

    @Bind(R.id.entry_summary)
    TextView mSummary;

    @Bind(R.id.entry_image)
    ImageView mImageView;

    @Bind(R.id.card_view)
    CardView mCardView;

    private EntryItem mEntry;

    /**
     * Constructor.
     *
     * @param context Context.
     */
    public EntryItemView(Context context) {

        super(context);
        initialize(context);
    }

    /**
     * Constructor.
     *
     * @param context Context.
     * @param attrs   Attribute set.
     */
    public EntryItemView(Context context, AttributeSet attrs) {

        super(context, attrs);
        initialize(context);
    }

    /**
     * Initializes this view.
     */
    private void initialize(Context context) {

        View layout = LayoutInflater.from(context).inflate(R.layout.view_entry_item, this);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layout.setLayoutParams(lp);
        ButterKnife.bind(this, layout);
    }

    /**
     * Decorates view with entry.
     *
     * @param entry Entry.
     */
    public void setEntry(EntryItem entry) {

        mEntry = entry;

        mTitle.setText(entry.getTitle());
        String content = Html.fromHtml(entry.getContent()).toString();
        String imgUrl = entry.getImgFromContent();
//        Log.v(TAG, "Content: " + content.replaceAll("\\<[^>]*>", ""));
        mSummary.setText(content);
        if (imgUrl != null && imgUrl.trim().length() > 0) {
            Log.v(TAG, "First img url: " + imgUrl);

//        if (entry.hasImage()) {

//            Log.v(TAG, "Entry has image. Title: " + entry.getTitle());
            mImageView.setVisibility(VISIBLE);
            ImageLoaderHelper.loadImageInto(mImageView.getContext(), mImageView, imgUrl);
        } else {

            mImageView.setVisibility(GONE);
        }

    }

    public void setListener(final OnEntryClickListener listener) {

        mCardView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.v(TAG, "OnClick");
                listener.onEntryClick(mEntry);
            }
        });
    }
}
