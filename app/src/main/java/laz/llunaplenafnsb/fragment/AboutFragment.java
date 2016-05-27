package laz.llunaplenafnsb.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import laz.llunaplenafnsb.R;

/**
 * About fragment.
 */
public class AboutFragment extends CollapsedBaseFragment {

    private static AboutFragment sAboutFragment;

    public static final String TAG = "AboutFragment";

    /**
     * Returns single instance of about fragment.
     *
     * @return About fragment instance.
     */
    public static AboutFragment getInstance() {

        if (sAboutFragment == null) {

            sAboutFragment = new AboutFragment();
        }
        return sAboutFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_about, container, false);
        ButterKnife.bind(this, view);

        initialize();
        return view;
    }

    /**
     * Initializes the about fragment.
     */
    private void initialize() {

        //TODO
    }

}
