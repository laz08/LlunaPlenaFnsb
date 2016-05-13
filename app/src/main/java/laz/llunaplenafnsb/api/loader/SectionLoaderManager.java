package laz.llunaplenafnsb.api.loader;

import android.content.Context;
import android.content.res.Resources;

import laz.llunaplenafnsb.R;
import retrofit2.Retrofit;

/**
 * Section loader manager.
 */
public class SectionLoaderManager {

    private static SectionLoaderManager mManager;

    private final FeedLoaderService mFeedService;

    /**
     * Constructor.
     */
    private SectionLoaderManager(Context ctx) {

        Resources res = ctx.getResources();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(res.getString(R.string.blog_url))
                .build();

        mFeedService = retrofit.create(FeedLoaderService.class);
    }

    /**
     * Returns single instance.
     *
     * @return Feed loader manager instance.
     */
    public static SectionLoaderManager getInstance(Context ctx) {

        if (mManager == null) {

            mManager = new SectionLoaderManager(ctx);
        }

        return mManager;
    }

    //Todo: load sections
}
