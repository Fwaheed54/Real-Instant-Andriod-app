package com.FYP.Adil.realinstant.Presenters;

import android.util.Log;

import com.FYP.Adil.realinstant.Contracts.NearbyUserContract;
import com.FYP.Adil.realinstant.Contracts.UserContract;
import com.FYP.Adil.realinstant.Models.User;
import com.FYP.Adil.realinstant.Retrofit.ApiUtils;
import com.FYP.Adil.realinstant.Retrofit.UserService;

import java.io.File;
import java.util.ArrayList;


import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserPresenter {
    private UserService userService;
    private UserContract userContract;
    private NearbyUserContract nearbyUserContract;

    public UserPresenter() {
    }

    public UserPresenter(NearbyUserContract nearbyUserContract) {
        this.nearbyUserContract = nearbyUserContract;
    }

    public UserPresenter(UserContract userContract) {
        this.userContract = userContract;
    }

    public void getUserCall(String Token){

        userService = ApiUtils.getUserService();

        userService.getUserDetails("Bearer "+Token).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.i("On Response Single User",response.message());
                if (response.isSuccessful()){
                    userContract.getSingleUser(response.body());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.i("On Failure Single User",t.getMessage());
            }
        });
    }


    public  void UploadUserProfileImage(File file ,int userId){
        //Set image into request body object
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part image = MultipartBody.Part.createFormData("image"
                , file.getName()+".png", requestBody);
        RequestBody user_id = RequestBody.create(MultipartBody.FORM, userId+"");

        userService = ApiUtils.getUserService();

        userService.uploadUserProfile(image,user_id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i("Response profile uplaod",response.message());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("Response Fail Pro Ima",t.getMessage());
            }
        });
    }

    public  void HideUserInNearby(int action ,int userId){
        //Set isHide into request body object

        RequestBody user_id = RequestBody.create(MultipartBody.FORM, userId+"");
        RequestBody userAction = RequestBody.create(MultipartBody.FORM, action+"");

        userService = ApiUtils.getUserService();

        userService.isLocation(user_id,userAction).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i("Response ok update isHide",response.message());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("Response Fail update isHide",t.getMessage());
            }
        });
    }

    public void getNearbyPeople(String lati,String longi,int userId){

        userService = ApiUtils.getUserService();

        userService.getNearbyPeople(lati,longi,userId).enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                Log.i("Response get Nearby",response.message());

                if(response.isSuccessful()){
                    nearbyUserContract.getNearbyUsers(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                Log.i("Response Failed Nearby",t.getMessage());

            }
        });
    }


}
