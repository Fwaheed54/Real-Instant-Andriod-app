package com.FYP.Adil.realinstant.Retrofit;

import com.FYP.Adil.realinstant.Models.AuthMassage;
import com.FYP.Adil.realinstant.Models.SignupUser;
import com.FYP.Adil.realinstant.Models.User;
import com.FYP.Adil.realinstant.Models.UserLogin;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserService {


    @GET("User")
    Call<ArrayList<User>> getAllUsers();

   /* @GET("User/{User_id}")
    Call<User> getUserDetails(@Path("User_id") int User_id);*/

    @GET("auth/user")
    Call<User> getUserDetails(@Header("Authorization") String Token);

    @GET("updateUserCoordinate")
    Call<ArrayList<User>> getNearbyPeople(@Query("lati") String Lati,
                                          @Query("longi") String Longi ,@Query("userId") int user_Id);

    @Multipart
    @POST("updateUserProfile")
    Call<ResponseBody> uploadUserProfile(
            @Part MultipartBody.Part URL,
            @Part("user_Id") RequestBody user_id
    );

    //this function use to update user coordinates action which is active or not active
    @Multipart
    @POST("isLocation")
    Call<ResponseBody> isLocation(

            @Part("userId") RequestBody user_id,
            @Part("action") RequestBody action
    );

    @POST("auth/signup")
    Call<AuthMassage> SignUp(@Body SignupUser signupUser);

    @Multipart
    @POST("auth/login")
    Call<UserLogin> login(@Part("email") RequestBody email,
                          @Part("password") RequestBody password);

    @GET("auth/logout")
    Call<AuthMassage> Logout(@Header("Authorization") String Token);
}
