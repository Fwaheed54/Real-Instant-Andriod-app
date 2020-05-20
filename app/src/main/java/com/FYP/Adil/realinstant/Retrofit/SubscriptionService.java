package com.FYP.Adil.realinstant.Retrofit;


import com.FYP.Adil.realinstant.Models.Subscription;

import java.util.ArrayList;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface SubscriptionService {

    @Multipart
    @POST("Subscription")
    Call<Void> AddSubscriptionCall(
            @Part("sub_radius") RequestBody SubscriptionRadius,
            @Part("sub_name") RequestBody SubscriptionName,
            @Part("user_id") RequestBody user_id,
            @Part("Lati") RequestBody Lati,
            @Part("Longi") RequestBody Longi,
            @Part("isActive") RequestBody isActive
    );


    @Multipart
    @POST("unsubscribe")
    Call<Void> Unsubscribe(
            @Part("user_id") RequestBody user_id,
            @Part("Sub_id") RequestBody subId
    );


    @GET("Subscription")
    Call<ArrayList<Subscription>> getUserAllSubscription(@Query("user_id") int user_Id);
}
