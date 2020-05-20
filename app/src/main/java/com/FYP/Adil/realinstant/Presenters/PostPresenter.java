package com.FYP.Adil.realinstant.Presenters;

import android.util.Log;

import com.FYP.Adil.realinstant.Contracts.LastPostIdContract;
import com.FYP.Adil.realinstant.Contracts.PostContract;
import com.FYP.Adil.realinstant.Contracts.UserPostsContract;
import com.FYP.Adil.realinstant.Models.Post;
import com.FYP.Adil.realinstant.Models.Subscription;
import com.FYP.Adil.realinstant.Retrofit.ApiUtils;
import com.FYP.Adil.realinstant.Retrofit.PostService;
import com.FYP.Adil.realinstant.Retrofit.SubscriptionService;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostPresenter {

    private PostService postService;
    private PostContract postContract;
    private UserPostsContract userPostsContract;
    private LastPostIdContract lastPostIdContract;

    //TODO
    private SubscriptionService subscriptionService;



    public PostPresenter() {
    }

    public PostPresenter(LastPostIdContract lastPostIdContract) {
        this.lastPostIdContract = lastPostIdContract;
    }



    public PostPresenter(UserPostsContract userPostsContract) {
        this.userPostsContract = userPostsContract;
    }

    public PostPresenter(PostContract postContract) {
        this.postContract = postContract;
    }

    public PostService getPostService() {
        return postService;
    }

    public void setPostService(PostService postService) {
        this.postService = postService;
    }

    public PostContract getPostContract() {
        return postContract;
    }

    public void setPostContract(PostContract postContract) {
        this.postContract = postContract;
    }

    public void getPostAreaWiseCall(String Lati, String Longi,int userId){

        postService = ApiUtils.getPostService();

        postService.getPostAreawise(Lati,Longi,userId).enqueue(new Callback<ArrayList<Post>>() {
            @Override
            public void onResponse(Call<ArrayList<Post>> call, Response<ArrayList<Post>> response) {
                Log.i("Response Success Area",response.message());
                if (response.isSuccessful()){
                    postContract.getAreaWisePost(response.body());
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Post>> call, Throwable t) {
                Log.i("Response failure Area",t.getMessage());
            }
        });
    }

    public void getUserPosts(int userId){
        postService = ApiUtils.getPostService();
        postService.getUserPosts(userId).enqueue(new Callback<ArrayList<Post>>() {
            @Override
            public void onResponse(Call<ArrayList<Post>> call, Response<ArrayList<Post>> response) {
                Log.i("Response User Posts",response.message());
                if(response.isSuccessful())
                    userPostsContract.getUserPost(response.body());
            }
            @Override
            public void onFailure(Call<ArrayList<Post>> call, Throwable t) {
                Log.i("Failure user Posts",t.getMessage());
            }
        });
    }

    //TODO
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

    public void getSubscriptionCall(int userId){


        subscriptionService = ApiUtils.getSubscriptionService();

        subscriptionService.getUserAllSubscription(userId).enqueue(new Callback<ArrayList<Subscription>>() {
            @Override
            public void onResponse(Call<ArrayList<Subscription>> call, Response<ArrayList<Subscription>> response) {
                Log.i("Response Success getSub",response.message());
                if (response.isSuccessful()){

                    postContract.SubscriptionCheckerInterface(response.body());
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Subscription>> call, Throwable t) {
                Log.i("Response Failure getSub",t.getMessage());

            }
        });
    }



    public void uploadFile(File file, String Radius, String Description, String Lati,
                           String Longi, Boolean IsGlobal, String NumOfHours, String Category_id,
                           final String User_id, final Boolean isPaid,String PostType) {

        //--------------------Set values into request body objects-----------------------------------------//


        //Set image into request body object
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);

        MultipartBody.Part File;
        if(PostType == "video") {
            File = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        }else{
             File = MultipartBody.Part.createFormData("file", file.getName()+".png", requestBody);
        }

        // Seek bar values into request body objects
        final RequestBody radius = RequestBody.create(MultipartBody.FORM, Radius);
        RequestBody numOfHours = RequestBody.create(MultipartBody.FORM, NumOfHours);
        // set description into request body object
        RequestBody description = RequestBody.create(MultipartBody.FORM, Description);

        //...... Coordinates
        RequestBody lati = RequestBody.create(MultipartBody.FORM, Lati);
        RequestBody longi = RequestBody.create(MultipartBody.FORM, Longi);

        // convert boolean object into binary
        int IsGolbalInt = IsGlobal ? 1 : 0;


        RequestBody category_id = RequestBody.create(MultipartBody.FORM, Category_id);
        RequestBody isGlobal = RequestBody.create(MultipartBody.FORM, String.valueOf(IsGolbalInt));
        final RequestBody user_id = RequestBody.create(MultipartBody.FORM, User_id);
        final RequestBody postType = RequestBody.create(MultipartBody.FORM, PostType);


        // Make image service
        PostService getResponse = ApiUtils.getImgService();


        // set all parementers  into call
        Call<ResponseBody> call = getResponse.uploadFile(File,radius,description,lati,longi,isGlobal,numOfHours,category_id,user_id,postType);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Log.i("Response upload file", response.message());
                if(response.isSuccessful()){
                    if(isPaid)
                        getLastPostId(Integer.parseInt(User_id));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

        /*call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i("Response upload file", response.message());
                if(response.isSuccessful()){
                    if(isPaid)
                        getLastPostId(Integer.parseInt(User_id));
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("teg fail",t.getMessage());
            }
        });*/
    }


    private void getLastPostId(int UserTempId){

        // Make retro service
        postService =  ApiUtils.getPostService();

        // make call to get all cotegories
        postService.getUserPostLastId(UserTempId).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("On response ",response.message());
                if(response.isSuccessful()){
                    //This event bus sent user last post id to Bill Fragment
                    //EventBus.getDefault().post(new String(response.body()));
                    lastPostIdContract.LastPostId(response.body());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.i("On Fail ",t.getMessage());
            }

        });
    }



    public void uploadText( String Radius, String Description, String Lati, String Longi, Boolean IsGlobal, String NumOfHours, String Category_id, final String User_id, final Boolean isPaid) {

        //--------------------Set values into request body objects-----------------------------------------//


        // Seek bar values into request body objects
        final RequestBody radius = RequestBody.create(MultipartBody.FORM, Radius);
        RequestBody numOfHours = RequestBody.create(MultipartBody.FORM, NumOfHours);
        // set description into request body object
        RequestBody description = RequestBody.create(MultipartBody.FORM, Description);

        //...... Coordinates
        RequestBody lati = RequestBody.create(MultipartBody.FORM, Lati);
        RequestBody longi = RequestBody.create(MultipartBody.FORM, Longi);

        // convert boolean object into binary
        int IsGolbalInt = IsGlobal ? 1 : 0;


        RequestBody category_id = RequestBody.create(MultipartBody.FORM, Category_id);
        RequestBody isGlobal = RequestBody.create(MultipartBody.FORM, String.valueOf(IsGolbalInt));
        final RequestBody user_id = RequestBody.create(MultipartBody.FORM, User_id);


        // Make image service
        PostService getResponse = ApiUtils.getImgService();


        // set all parementers  into call
        Call<ResponseBody> call = getResponse.uploadText(radius,description,lati,longi,isGlobal,numOfHours,category_id,user_id);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i("Response", response.message());
                if(response.isSuccessful()){
                    if(isPaid)
                        getLastPostId(Integer.parseInt(User_id));
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("teg fail",t.getMessage());
            }
        });
    }


    public void DeletePost(int PostId){

        postService = ApiUtils.getPostService();

        postService.DeletePost(PostId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.i("Response Delete Pass", response.message());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.i("Response Delete fail",t.getMessage());
            }
        });
    }


}
