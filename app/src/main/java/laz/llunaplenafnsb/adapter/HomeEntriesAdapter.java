package laz.llunaplenafnsb.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import laz.llunaplenafnsb.items.EntryItem;

/**
 * Home entries adapter.
 */
public class HomeEntriesAdapter extends RecyclerView.Adapter {

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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {

        return (mEntries != null) ? mEntries.size() : 0;
    }
}
