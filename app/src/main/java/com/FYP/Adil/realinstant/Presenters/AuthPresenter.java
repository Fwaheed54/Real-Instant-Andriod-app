package com.FYP.Adil.realinstant.Presenters;

import android.util.Log;

import com.FYP.Adil.realinstant.Contracts.AuthContract;
import com.FYP.Adil.realinstant.Models.AuthMassage;
import com.FYP.Adil.realinstant.Models.SignupUser;
import com.FYP.Adil.realinstant.Models.User;
import com.FYP.Adil.realinstant.Models.UserLogin;
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

public class AuthPresenter {

    private UserService userService;
    private AuthContract authContract;

    public AuthPresenter(AuthContract authContract) {
        this.authContract = authContract;
    }


    public  void Signup(SignupUser signupUser){
        //Set image into request body object

        userService = ApiUtils.getUserService();

        userService.SignUp(signupUser).enqueue(new Callback<AuthMassage>() {
            @Override
            public void onResponse(Call<AuthMassage> call, Response<AuthMassage> response) {
                Log.i("On Response signUp",response.message());
                if(response.isSuccessful()){
                    authContract.getMessage(response.body());
                }
            }

            @Override
            public void onFailure(Call<AuthMassage> call, Throwable t) {
                Log.i("On Failure signUp ",t.getMessage());
            }
        });
    }


    public  void Login(String Email,String Password){
        //Set image into request body object

        RequestBody email = RequestBody.create(MultipartBody.FORM, Email);
        RequestBody password = RequestBody.create(MultipartBody.FORM, Password);

        userService = ApiUtils.getUserService();

        userService.login(email,password).enqueue(new Callback<UserLogin>() {
            @Override
            public void onResponse(Call<UserLogin> call, Response<UserLogin> response) {
                Log.i("On Response login",response.message());


                if(response.isSuccessful()){
                    Log.i("checkk",response.body().getExpiresAt()+"");
                    if(response.body().getExpiresAt().equals("0"))
                        authContract.getMessage(new AuthMassage("You Are Blocked"));
                    else
                        authContract.Login(response.body());
                }else {
                    //authContract.getMessage(new AuthMassage(response.body().getExpiresAt()));
                   authContract.getMessage(new AuthMassage("Wrong Email/Password"));
                }
            }

            @Override
            public void onFailure(Call<UserLogin> call, Throwable t) {
                Log.i("On Failure Login ",t.getMessage());
            }
        });
    }

    public void LogOutUser(final String Token){

        userService = ApiUtils.getUserService();

        userService.Logout(Token).enqueue(new Callback<AuthMassage>() {
            @Override
            public void onResponse(Call<AuthMassage> call, Response<AuthMassage> response) {
                Log.i("On Response Logout",response.message());

                if(response.isSuccessful()){
                    if(response.body() != null)
                      authContract.getMessage(response.body());
                }
            }

            @Override
            public void onFailure(Call<AuthMassage> call, Throwable t) {
                Log.i("On Failure Logout ",t.getMessage());
            }
        });
    }
}
