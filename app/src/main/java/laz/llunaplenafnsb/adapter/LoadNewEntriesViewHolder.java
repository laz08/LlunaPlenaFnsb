package laz.llunaplenafnsb.adapter;

import laz.llunaplenafnsb.views.LoadEntriesItemView;

/**
 * New entries button view holder.
 */
public class LoadNewEntriesViewHolder extends BaseFeedItemViewHolder {

    private LoadEntriesItemView mItemView;

    /**
     * Constructor.
     *
     * @param itemView Item view.
     */
    public LoadNewEntriesViewHolder(LoadEntriesItemView itemView) {

        super(itemView);
        mItemView = itemView;
    }


    /**
     * Decorates entry item.
     */
    public void decorate(final OnFeedItemClickListener listener, boolean show) {

        mItemView.setVisible(show);
        mItemView.setListener(listener);
    }

}
