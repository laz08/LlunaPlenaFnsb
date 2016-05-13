package laz.llunaplenafnsb.api.loader;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SectionsLoaderService {

    @GET("blogger/v3/blogs/3262650353206841577/pages")
    Call<ResponseBody> getSections(@Query("key") String apiKey);
}
