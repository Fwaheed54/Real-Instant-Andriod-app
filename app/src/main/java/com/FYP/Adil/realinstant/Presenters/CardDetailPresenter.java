package com.FYP.Adil.realinstant.Presenters;

import android.util.Log;

import com.FYP.Adil.realinstant.Contracts.CardDetailContract;
import com.FYP.Adil.realinstant.Retrofit.ApiUtils;
import com.FYP.Adil.realinstant.Retrofit.CardDetailService;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CardDetailPresenter {

    private CardDetailService cardDetailService;
    private CardDetailContract cardDetailContract;

    public CardDetailPresenter(CardDetailContract cardDetailContract) {
        this.cardDetailContract = cardDetailContract;
    }

    public CardDetailPresenter() {
    }


    public void UserCardStatus(int userId){

        cardDetailService = ApiUtils.getCardDetailService();

        cardDetailService.getCardDetailStatus(userId).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("Response Card status",response.message()+"   "+response.body());
                if(response.isSuccessful()){
                    cardDetailContract.getUserCardStatus(response.body());
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.i("Failure Card status",t.getMessage());
            }
        });
    }

    public void CardDetailCall(String CardNo,String Cvv,String ExpDate,String UserId){

        cardDetailService = ApiUtils.getCardDetailService();


        RequestBody cardNo = RequestBody.create(MultipartBody.FORM, CardNo);
        RequestBody cvv = RequestBody.create(MultipartBody.FORM, Cvv);
        RequestBody expDate = RequestBody.create(MultipartBody.FORM, ExpDate);
        RequestBody userId = RequestBody.create(MultipartBody.FORM, UserId);

        cardDetailService.UploadCardDetails(cardNo,cvv,expDate,userId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.i("Response Card Details",response.message());
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.i("Failure Card Details",t.getMessage());
            }
        });
    }


}
