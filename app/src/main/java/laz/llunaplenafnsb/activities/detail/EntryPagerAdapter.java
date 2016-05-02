package laz.llunaplenafnsb.activities.detail;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.List;

import laz.llunaplenafnsb.items.EntryItem;
import laz.llunaplenafnsb.views.EntryDetailFragment;

public class EntryPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {

    private List<EntryItem> mEntries;

    /**
     * Constructor.
     *
     * @param fm Fragment manager.
     */
    public EntryPagerAdapter(FragmentManager fm) {

        super(fm);
    }

    public void setItems(List<EntryItem> items) {

        mEntries = items;
    }

    @Override
    public Fragment getItem(int position) {

        if (mEntries != null && mEntries.size() > position) {

            return EntryDetailFragment.newInstance(mEntries.get(position));
        }
        return null;
    }

    @Override
    public int getCount() {

        return mEntries != null ? mEntries.size() : 0;
    }
}
