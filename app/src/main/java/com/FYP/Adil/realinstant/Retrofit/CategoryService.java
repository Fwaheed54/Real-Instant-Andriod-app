package com.FYP.Adil.realinstant.Retrofit;

import com.FYP.Adil.realinstant.Models.Category;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CategoryService {

    @GET("Category/")
    Call<ArrayList<Category>> getCategory();

    @GET("/")
    Call<Category> getCategory(@Query("tagged") String tags);
}
