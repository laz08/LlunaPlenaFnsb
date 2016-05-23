package laz.llunaplenafnsb.api.loader;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FeedLoaderService {

    @GET("blogger/v3/blogs/3262650353206841577")
    Call<ResponseBody> getFeed(@Query("key") String apiKey);

    @GET("blogger/v3/blogs/3262650353206841577/posts")
    Call<ResponseBody> getPosts(@Query("key") String apiKey);

    @GET("blogger/v3/blogs/3262650353206841577/posts")
    Call<ResponseBody> getNextPosts(@Query("key") String apiKey, @Query("nextPageToken") String token);

    @GET("blogger/v3/blogs/3262650353206841577/posts/search")
    Call<ResponseBody> search(@Query("key") String apiKey, @Query("q") String query);
}
