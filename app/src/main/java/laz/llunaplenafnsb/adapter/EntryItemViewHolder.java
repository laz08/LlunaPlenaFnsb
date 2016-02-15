package laz.llunaplenafnsb.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import laz.llunaplenafnsb.activities.EntryDetailActivity;
import laz.llunaplenafnsb.items.EntryItem;
import laz.llunaplenafnsb.views.EntryItemView;

/**
 * Entry item holder.
 */
public class EntryItemViewHolder extends RecyclerView.ViewHolder {

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
    public void decorate(final EntryItem item) {

        mEntryItemView.setEntry(item);
        mEntryItemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                openEntryDetailActivity(item);
            }
        });
    }

    /**
     * Opens entry detail activity.
     */
    private void openEntryDetailActivity(EntryItem item) {

//        Log.v(TAG, "Opening entry detail activity.");
        Context context = mEntryItemView.getContext();

        Intent detailIntent = new Intent(context, EntryDetailActivity.class);
        detailIntent.putExtra(EXTRA_ENTRY_ITEM, item);
        detailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(detailIntent);
    }

    private static final String EXTRA_ENTRY_ITEM = "extraEntryItem";
}
