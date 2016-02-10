package laz.llunaplenafnsb.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import laz.llunaplenafnsb.R;
import laz.llunaplenafnsb.items.EntryItem;

/**
 * EntryItem item view.
 */
public class EntryItemView extends CardView {

    @Bind(R.id.entry_title)
    TextView mTitle;

    @Bind(R.id.entry_summary)
    TextView mSummary;

    @Bind(R.id.entry_image)
    ImageView mImageView;


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

        View layout = inflate(context, R.layout.view_entry_item, null);
        ButterKnife.bind(this, layout);
    }

    /**
     * Decorates view with entry.
     *
     * @param entry Entry.
     */
    public void setEntry(EntryItem entry) {

        mTitle.setText(entry.getTitle());
        mSummary.setText(entry.getSummary());
    }
}
