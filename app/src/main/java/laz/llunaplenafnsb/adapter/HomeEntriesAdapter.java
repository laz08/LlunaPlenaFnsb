package laz.llunaplenafnsb.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import laz.llunaplenafnsb.items.EntryItem;
import laz.llunaplenafnsb.views.EntryItemView;

/**
 * Home entries adapter.
 */
public class HomeEntriesAdapter extends RecyclerView.Adapter<EntryItemViewHolder> {

    List<EntryItem> mEntries;

    /**
     * Sets entries.
     *
     * @param entries Entries.
     */
    public void setEntries(List<EntryItem> entries) {

        mEntries = entries;
    }

    @Override
    public EntryItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new EntryItemViewHolder(new EntryItemView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(EntryItemViewHolder holder, int position) {

        if (mEntries != null && position < mEntries.size()) {

            EntryItem entryItem = mEntries.get(position);
            holder.decorate(entryItem);
        }
    }

    @Override
    public int getItemCount() {

        return (mEntries != null) ? mEntries.size() : 0;
    }
}
