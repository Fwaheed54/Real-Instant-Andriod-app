package com.FYP.Adil.realinstant.Retrofit;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface PaidPostService {


    @Multipart
    @POST("PaidPost")
    Call<Void> UploadPostDetails(

            @Part("changeAreaPrice") RequestBody changeAreaPrice,
            @Part("numberOfDaysPrice") RequestBody numberOfDaysPrice,
            @Part("radiusPrice") RequestBody radiusPrice,
            @Part("totalPrice") RequestBody totalPrice,
            @Part("post_id") RequestBody post_id,
            @Part("post_user_id") RequestBody post_user_id
    );
}
