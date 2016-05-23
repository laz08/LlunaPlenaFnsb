package laz.llunaplenafnsb.adapter;

import laz.llunaplenafnsb.items.EntryItem;
import laz.llunaplenafnsb.views.EntryItemView;

/**
 * Entry item holder.
 */
public class EntryItemViewHolder extends BaseFeedItemViewHolder {

    public static final String TAG = "EntryItemViewHolder";

    private EntryItemView mEntryItemView;


    /**
     * Constructor.
     *
     * @param itemView ItemView.
     */
    public EntryItemViewHolder(EntryItemView itemView) {

        super(itemView);
        mEntryItemView = itemView;
    }

    /**
     * Decorates entry item.
     *
     * @param item Entry item.
     */
    public void decorate(final EntryItem item, final OnFeedItemClickListener listener) {

//        Log.v(TAG, "Decorating");
        mEntryItemView.setEntry(item);
        mEntryItemView.setListener(listener);
    }

}
