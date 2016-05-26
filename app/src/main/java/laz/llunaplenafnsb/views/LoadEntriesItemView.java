package laz.llunaplenafnsb.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import laz.llunaplenafnsb.R;
import laz.llunaplenafnsb.adapter.OnFeedItemClickListener;

/**
 * Load entries item view.
 */
public class LoadEntriesItemView extends FrameLayout {


    public static final String TAG = "LoadEntriesItemView";

    @Bind(R.id.button_entries)
    Button mButton;

    /**
     * Constructor.
     *
     * @param context Context.
     */
    public LoadEntriesItemView(Context context) {

        super(context);
        initialize(context);
    }

    /**
     * Constructor.
     *
     * @param context Context.
     * @param attrs   Attribute set.
     */
    public LoadEntriesItemView(Context context, AttributeSet attrs) {

        super(context, attrs);
        initialize(context);
    }

    /**
     * Initializes this view.
     */
    private void initialize(Context context) {

        View layout = LayoutInflater.from(context).inflate(R.layout.view_load_entries, this);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layout.setLayoutParams(lp);
        ButterKnife.bind(this, layout);
    }

    /**
     * Sets listener.
     *
     * @param listener On feed item click listener.
     */
    public void setListener(final OnFeedItemClickListener listener) {

        mButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {

                Log.v(TAG, "OnClick");
                listener.loadNewEntriesClick();
            }
        });

    }

    /**
     * Sets this view visible.
     */
    public void setVisible(boolean show) {

        Log.v(TAG, "Setting button as visible/gone");
        if (show) {

            mButton.setVisibility(VISIBLE);
        } else {

            mButton.setVisibility(GONE);
        }
    }
}
