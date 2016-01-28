package laz.llunaplenafnsb.api;

import android.content.Context;
import android.content.res.Resources;

import laz.llunaplenafnsb.R;
import laz.llunaplenafnsb.items.Feed;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ApiRestManager {

    public static final String TAG = "RESTManager";
    private RetrofitService mService;
    private Context mContext;

    /**
     * Constructor.
     *
     * @param ctx Context.
     */
    public ApiRestManager(Context ctx) {

        mContext = ctx;
        Resources res = mContext.getResources();

        String url = res.getString(R.string.baseURL);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mService = retrofit.create(RetrofitService.class);
    }


    /**
     * Requests main feed.
     */
    public void getMainFeed(final FeedCallback callback) {

        Call<Feed> jsonCall = mService.getMainFeed("json");
        jsonCall.enqueue(new Callback<Feed>() {

            @Override
            public void onResponse(Response<Feed> response) {

                callback.onFeedReceived();
            }

            @Override
            public void onFailure(Throwable t) {

                callback.onError();
            }
        });
    }

}
