package com.FYP.Adil.realinstant.Presenters;

import android.util.Log;

import com.FYP.Adil.realinstant.Retrofit.ApiUtils;
import com.FYP.Adil.realinstant.Retrofit.PaidPostService;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostDetailPresenter {

    private PaidPostService paidPostService;

    public PostDetailPresenter() {
    }

   public void LoadDetails(String HoursBill, String RadiusBill, String TotalBill, String LastPostId, String UserTempId){

        RequestBody changeAreaPrice = RequestBody.create(MultipartBody.FORM, "56");

        RequestBody numberOfDaysPrice;
        if(HoursBill.equals("")){
            numberOfDaysPrice = RequestBody.create(MultipartBody.FORM,"0" );}
        else{
            numberOfDaysPrice = RequestBody.create(MultipartBody.FORM,HoursBill.substring(2) );}


        RequestBody radiusPrice;
        if(RadiusBill.equals("")){
            radiusPrice = RequestBody.create(MultipartBody.FORM,"0" );
        }else{
            radiusPrice = RequestBody.create(MultipartBody.FORM,RadiusBill.substring(2));
        }

        RequestBody totalPrice = RequestBody.create(MultipartBody.FORM,TotalBill+"" );

        RequestBody post_id = RequestBody.create(MultipartBody.FORM, LastPostId);
        RequestBody user_id = RequestBody.create(MultipartBody.FORM,UserTempId+"" );


        paidPostService  = ApiUtils.getPaidPostService();


        // set all parementers  into call
        Call<Void> call = paidPostService.UploadPostDetails(changeAreaPrice,numberOfDaysPrice,radiusPrice,totalPrice,post_id,user_id);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.i("Response Post Details", response.message());
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.i("Failure Post Details",t.getMessage());
            }
        });

    }
}
