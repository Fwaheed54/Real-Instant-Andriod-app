package com.FYP.Adil.realinstant.Retrofit;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ReactService {

    @Multipart
    @POST("React")
    Call<Void> like_dislike(

            @Part("user_id") RequestBody user_id,
            @Part("post_id") RequestBody post_id,
            @Part("action") RequestBody action
    );

    @GET("React/{User_id}/{Post_id}")
    Call<String> getAction(@Path("User_id") int User_id,@Path("Post_id") int Post_id);


}
