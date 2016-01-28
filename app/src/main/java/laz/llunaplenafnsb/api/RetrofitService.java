package laz.llunaplenafnsb.api;

import laz.llunaplenafnsb.items.Feed;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Retrofit service.
 */
public interface RetrofitService {

    @GET("rss.xml")
    Call<Feed> getMainFeed(
            @Query("alt") String format
    );

}
