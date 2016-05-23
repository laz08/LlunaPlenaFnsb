package laz.llunaplenafnsb.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import laz.llunaplenafnsb.items.EntryItem;
import laz.llunaplenafnsb.views.EntryItemView;
import laz.llunaplenafnsb.views.LoadEntriesItemView;

/**
 * Home entries adapter.
 */
public class HomeEntriesAdapter extends RecyclerView.Adapter<BaseFeedItemViewHolder> {

    public static final String TAG = "HomeEntriesAdapter";

    private OnFeedItemClickListener mListener;
    private List<EntryItem> mEntries;

    private boolean mAllowLoadingMoreEntries;

    /**
     * Constructor.
     *
     * @param listener OnFeedItemClickListener.
     */
    public HomeEntriesAdapter(OnFeedItemClickListener listener, boolean allowLoadingMoreEntries) {

        mListener = listener;
        mEntries = new ArrayList<>();
        mAllowLoadingMoreEntries = allowLoadingMoreEntries;
    }

    /**
     * Sets entries.
     *
     * @param entries Entries.
     */
    public void setEntries(List<EntryItem> entries) {

        mEntries.clear();
        mEntries.addAll(entries);
    }

    @Override
    public BaseFeedItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {

            case BUTTON_TYPE:
                return new LoadNewEntriesViewHolder(new LoadEntriesItemView(parent.getContext()));

            default:
            case ENTRY_TYPE:
                return new EntryItemViewHolder(new EntryItemView(parent.getContext()));

        }
    }

    @Override
    public void onBindViewHolder(BaseFeedItemViewHolder holder, int position) {

        if (holder instanceof EntryItemViewHolder && mEntries != null) {

            if (position < mEntries.size()) {

                EntryItem entryItem = mEntries.get(position);
                ((EntryItemViewHolder) holder).decorate(entryItem, mListener);
            }
        } else if (holder instanceof LoadNewEntriesViewHolder) {

            LoadNewEntriesViewHolder newEntriesViewHolder = (LoadNewEntriesViewHolder) holder;
            newEntriesViewHolder.decorate(mListener,
                    mAllowLoadingMoreEntries && mEntries != null && mEntries.size() > 0);
        }
    }

    @Override
    public int getItemViewType(int position) {

        if (position < (getItemCount() - 1)) {

            return ENTRY_TYPE;
        }
        return BUTTON_TYPE;
    }

    @Override
    public int getItemCount() {

        if (mEntries != null) {

            if (mAllowLoadingMoreEntries) {

                return mEntries.size() + 1;
            }
            return mEntries.size();
        }
        return 0;
    }

    private final int BUTTON_TYPE = 0;
    private final int ENTRY_TYPE = 42;

}
