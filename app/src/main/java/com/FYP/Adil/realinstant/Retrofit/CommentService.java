package com.FYP.Adil.realinstant.Retrofit;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface CommentService {

    @Multipart
    @POST("Comment")
    Call<Void> PostComment(

            @Part("user_id") RequestBody user_id,
            @Part("post_id") RequestBody post_id,
            @Part("commentText") RequestBody commentText
    );
}
