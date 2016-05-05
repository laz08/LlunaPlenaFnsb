package laz.llunaplenafnsb.views;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;

/**
 * Custom refresh layout.
 */
public class CustomSwipeRefreshLayout extends SwipeRefreshLayout {

    private boolean mIsMeasured = false;
    private boolean mPreMeasureRefreshing = false;

    /**
     * Constructor.
     *
     * @param context Context.
     */
    public CustomSwipeRefreshLayout(Context context) {

        super(context);
    }

    /**
     * Constructor
     *
     * @param context Context.
     * @param attrs   Attribute set.
     */
    public CustomSwipeRefreshLayout(Context context, AttributeSet attrs) {

        super(context, attrs);
    }


    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (!mIsMeasured) {

            mIsMeasured = true;
            setRefreshing(mPreMeasureRefreshing);
        }
    }

    @Override
    public void setRefreshing(boolean refreshing) {

        if (mIsMeasured) {

            super.setRefreshing(refreshing);
        } else {

            mPreMeasureRefreshing = refreshing;
        }
    }
}
