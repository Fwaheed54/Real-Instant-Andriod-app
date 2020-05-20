package com.FYP.Adil.realinstant.Presenters;

import androidx.annotation.NonNull;
import android.util.Log;

import com.FYP.Adil.realinstant.Contracts.CategoryContract;
import com.FYP.Adil.realinstant.Models.Category;
import com.FYP.Adil.realinstant.Retrofit.ApiUtils;
import com.FYP.Adil.realinstant.Retrofit.CategoryService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryPresenter {

    private CategoryService categoryService;
    private CategoryContract categoryContract;

    public CategoryPresenter(CategoryContract categoryContract) {
        this.categoryContract = categoryContract;
    }

    public CategoryPresenter() {
    }

    public void loadCategory() {

        // Make retro service
        categoryService =  ApiUtils.getCategoryService();

        // make call to get all cotegories
        categoryService.getCategory().enqueue(new Callback<ArrayList<Category>>() {

            @Override
            public void onResponse(final Call<ArrayList<Category>> call, @NonNull Response<ArrayList<Category>> response) {
                // if reposnse  is successful
                if(response.isSuccessful() && !response.body().isEmpty()) {
                    Log.d("Response ", "Category loaded from API "+response.message());
                    categoryContract.getCategory(response.body());
                }else {
                    int statusCode  = response.code();
                    Log.d("Failure category", "category loaded from API status code: "+statusCode);
                    // handle request errors depending on status code
                }

            }
            @Override
            public void onFailure(Call<ArrayList<Category>> call, Throwable t) {

                Log.i("tag Category: ",t.getMessage()+"");
            }
        });
    }
}

