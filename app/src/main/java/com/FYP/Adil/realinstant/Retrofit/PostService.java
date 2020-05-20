package com.FYP.Adil.realinstant.Retrofit;

import com.FYP.Adil.realinstant.Models.Post;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PostService {

    @Multipart
    @POST("Post")
    Call<ResponseBody> uploadFile(
            @Part MultipartBody.Part File,

            @Part("radius") RequestBody radius,
            @Part("description") RequestBody description,
            @Part("lati") RequestBody lati,
            @Part("longi") RequestBody longi,
            @Part("isGlobal") RequestBody isGlobal,
            @Part("duration") RequestBody duration,
            @Part("category_id") RequestBody category_id,
            @Part("user_id") RequestBody user_id,
            @Part("postType") RequestBody postType

    );


    @Multipart
    @POST("uploadText")
    Call<ResponseBody> uploadText(
            @Part("radius") RequestBody radius,
            @Part("description") RequestBody description,
            @Part("lati") RequestBody lati,
            @Part("longi") RequestBody longi,
            @Part("isGlobal") RequestBody isGlobal,
            @Part("duration") RequestBody duration,
            @Part("category_id") RequestBody category_id,
            @Part("user_id") RequestBody user_id

    );

    @GET("Post/")
    Call<ArrayList<Post>> GetPosts();

    @GET("Post/{User_id}")
    Call<String> getUserPostLastId(@Path("User_id") int User_id);

    @GET("allUserPosts/{User_id}")
    Call<ArrayList<Post>> getUserPosts(@Path("User_id") int User_id);

    @GET("getFilteredPosts")
    Call<ArrayList<Post>> getPostAreawise(@Query("Lati") String Lati,
                                 @Query("Longi") String Longi ,@Query("user_Id") int user_Id);

    @PUT("Post/{id}")
    Call<Void> DeletePost(
            @Path("id") int id);
}
