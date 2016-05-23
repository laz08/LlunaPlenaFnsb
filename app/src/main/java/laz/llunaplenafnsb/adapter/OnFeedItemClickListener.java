package laz.llunaplenafnsb.adapter;

import laz.llunaplenafnsb.items.EntryItem;

public interface OnFeedItemClickListener {

    void onEntryClick(EntryItem entry);

    void loadNewEntriesClick();
}
