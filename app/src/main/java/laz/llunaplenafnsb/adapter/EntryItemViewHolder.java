package laz.llunaplenafnsb.adapter;

import android.support.v7.widget.RecyclerView;

import laz.llunaplenafnsb.items.EntryItem;
import laz.llunaplenafnsb.views.EntryItemView;

/**
 * Entry item holder.
 */
public class EntryItemViewHolder extends RecyclerView.ViewHolder {

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
    public void decorate(final EntryItem item, final OnEntryClickListener listener) {

//        Log.v(TAG, "Decorating");
        mEntryItemView.setEntry(item);
        mEntryItemView.setListener(listener);
    }

}
