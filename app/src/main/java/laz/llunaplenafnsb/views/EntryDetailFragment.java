package laz.llunaplenafnsb.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import laz.llunaplenafnsb.R;
import laz.llunaplenafnsb.helper.ImageLoaderHelper;
import laz.llunaplenafnsb.items.EntryItem;
import laz.llunaplenafnsb.items.ThumbnailItem;

/**
 * Entry detail view
 */
public class EntryDetailFragment extends Fragment {

    private EntryItem mEntry;

    @Bind(R.id.toolbar_image)
    ImageView mToolbarImage;

    @Bind(R.id.title_tv)
    TextView mTitle;

    @Bind(R.id.description_tv)
    TextView mDescription;

    public static EntryDetailFragment newInstance(EntryItem item) {

        EntryDetailFragment fmt = new EntryDetailFragment();

        Bundle args = new Bundle();
        args.putParcelable(EXTRA_ENTRY_ITEM, item);

        fmt.setArguments(args);
        return fmt;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mEntry = getArguments().getParcelable(EXTRA_ENTRY_ITEM);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.content_entry_detail, container, false);
        ButterKnife.bind(this, view);

        mTitle.setText(mEntry.getTitle());
        mDescription.setText(mEntry.getSummary());

        ThumbnailItem thumb = mEntry.getThumbnail();
        if (thumb != null) {

            ImageLoaderHelper.loadImageInto(getContext(), mToolbarImage, thumb.getUrl());
        }
        return view;
    }

    public static final String EXTRA_ENTRY_ITEM = "extraEntryItem";

}
