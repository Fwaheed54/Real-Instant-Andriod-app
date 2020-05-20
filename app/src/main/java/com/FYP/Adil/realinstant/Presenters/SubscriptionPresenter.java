package com.FYP.Adil.realinstant.Presenters;

import android.util.Log;

import com.FYP.Adil.realinstant.Contracts.SubscriptionContract;
import com.FYP.Adil.realinstant.Models.Subscription;
import com.FYP.Adil.realinstant.Retrofit.ApiUtils;
import com.FYP.Adil.realinstant.Retrofit.SubscriptionService;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubscriptionPresenter {

    private SubscriptionService subscriptionService;
    private SubscriptionContract subscriptionContract;

    public SubscriptionPresenter(SubscriptionContract subscriptionContract) {
        this.subscriptionContract = subscriptionContract;
    }

    public SubscriptionPresenter() {

    }

    public void getSubscriptionCall(int userId){

        subscriptionService = ApiUtils.getSubscriptionService();

        subscriptionService.getUserAllSubscription(userId).enqueue(new Callback<ArrayList<Subscription>>() {
            @Override
            public void onResponse(Call<ArrayList<Subscription>> call, Response<ArrayList<Subscription>> response) {
                Log.i("Success getSubscription",response.message());
                if (response.isSuccessful()){
                    subscriptionContract.SubscriptionCheckerInterface(response.body());
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Subscription>> call, Throwable t) {
                Log.i(" Failure getSubscription",t.getMessage());

            }
        });
    }


    public void AddSubscription(String subName,String subRadius,String lati,String longi,String isActive,String user_Id){

        subscriptionService = ApiUtils.getSubscriptionService();

        RequestBody SubName = RequestBody.create(MultipartBody.FORM, subName);
        RequestBody SubRadius = RequestBody.create(MultipartBody.FORM, subRadius);
        RequestBody UserId = RequestBody.create(MultipartBody.FORM, user_Id);
        RequestBody Lati = RequestBody.create(MultipartBody.FORM, lati);
        RequestBody Longi = RequestBody.create(MultipartBody.FORM, longi);
        RequestBody IsActive = RequestBody.create(MultipartBody.FORM, isActive);

        subscriptionService.AddSubscriptionCall(SubRadius,SubName,UserId,Lati,Longi,IsActive).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.i("Response Success SetSub",response.message());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.i("Response Failure",t.getMessage());
            }
        });
    }

    public void Unsubscribe(String user_Id,String subId){

        subscriptionService = ApiUtils.getSubscriptionService();

        RequestBody UserId = RequestBody.create(MultipartBody.FORM, user_Id);
        RequestBody Sub_id = RequestBody.create(MultipartBody.FORM, subId);


        subscriptionService.Unsubscribe(UserId,Sub_id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.i("Response Success Unsubscribe",response.message());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.i("Response Unsubscribe Failure",t.getMessage());
            }
        });
    }
}
