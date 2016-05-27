package laz.llunaplenafnsb.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;

import laz.llunaplenafnsb.R;

public class CollapsedBaseFragment extends Fragment {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        AppBarLayout barLayout = (AppBarLayout) getActivity().findViewById(R.id.toolbar_layout);
        barLayout.setExpanded(false);
    }
}
