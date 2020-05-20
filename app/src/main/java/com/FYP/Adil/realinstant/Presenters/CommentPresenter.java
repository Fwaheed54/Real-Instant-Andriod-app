package com.FYP.Adil.realinstant.Presenters;

import android.util.Log;

import com.FYP.Adil.realinstant.Contracts.CommentContract;
import com.FYP.Adil.realinstant.Contracts.CommentFragmentContract;
import com.FYP.Adil.realinstant.Models.User;
import com.FYP.Adil.realinstant.Retrofit.ApiUtils;
import com.FYP.Adil.realinstant.Retrofit.CommentService;
import com.FYP.Adil.realinstant.Retrofit.UserService;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentPresenter {

    private CommentService commentService;
    private UserService userService;
    private CommentContract commentContract;
    private CommentFragmentContract commentFragmentContract;
    private ArrayList<User> users;


    public CommentPresenter(CommentFragmentContract commentFragmentContract) {
        this.commentFragmentContract = commentFragmentContract;
    }

    public CommentPresenter(CommentContract commentContract) {
        this.commentContract = commentContract;
    }

    public CommentPresenter() {
        users = new ArrayList<>();
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public CommentService getUserService() {
        return commentService;
    }

    public void setUserService(CommentService commentService) {
        this.commentContract = commentContract;
    }

    public CommentContract getCommentContract() {
        return commentContract;
    }

    public void setCommentContract(CommentContract commentContract) {
        this.commentContract = commentContract;
    }

    public void PostCommentsCall(String user_Id,String post_Id, String text){

        commentService = ApiUtils.getCommentService();


        RequestBody postId = RequestBody.create(MultipartBody.FORM, post_Id);
        RequestBody Text = RequestBody.create(MultipartBody.FORM, text);
        RequestBody userId = RequestBody.create(MultipartBody.FORM, user_Id);

        commentService.PostComment( userId, postId,Text).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.i("On Response ",response.message());
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.i("On Failure ",t.getMessage());
            }
        });
    }


    public void getUserCall(){

        userService = ApiUtils.getUserService();

        userService.getAllUsers().enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                Log.i("On Response user Call",response.message());

                if (response.isSuccessful()){
                    users = response.body();
                    commentFragmentContract.getUsers(users);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                Log.i("On Failure user call",t.getMessage());
            }
        });
    }
}


