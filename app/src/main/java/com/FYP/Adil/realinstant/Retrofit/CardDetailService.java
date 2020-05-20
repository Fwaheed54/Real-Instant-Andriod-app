package com.FYP.Adil.realinstant.Retrofit;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface CardDetailService {


    @GET("CardDetail/{User_id}")
    Call<String> getCardDetailStatus(@Path("User_id") int User_id);


    @Multipart
    @POST("CardDetail")
    Call<Void> UploadCardDetails(
            @Part("cardNumber") RequestBody cardNumber,
            @Part("cvv") RequestBody cvv,
            @Part("expDate") RequestBody expDate,
            @Part("user_id") RequestBody user_id
    );
}
