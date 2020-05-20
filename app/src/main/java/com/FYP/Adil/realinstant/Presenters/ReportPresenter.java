package com.FYP.Adil.realinstant.Presenters;

import android.util.Log;

import com.FYP.Adil.realinstant.Contracts.CommentContract;
import com.FYP.Adil.realinstant.Retrofit.ApiUtils;
import com.FYP.Adil.realinstant.Retrofit.ReportService;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportPresenter {

    private ReportService reportService;
    private CommentContract commentContract;

    public ReportPresenter() {
    }

    public ReportPresenter(CommentContract commentContract) {
        this.commentContract = commentContract;
    }

    public ReportService getReportService() {
        return reportService;
    }

    public void setReportService(ReportService reportService) {
        this.reportService = reportService;
    }

    public CommentContract getCommentContract() {
        return commentContract;
    }

    public void setCommentContract(CommentContract commentContract) {
        this.commentContract = commentContract;
    }

    public void ReportPosrCall(String user_Id, String post_Id, String text){

        reportService = ApiUtils.getReportService();


        RequestBody postId = RequestBody.create(MultipartBody.FORM, post_Id);
        RequestBody Text = RequestBody.create(MultipartBody.FORM, text);
        RequestBody userId = RequestBody.create(MultipartBody.FORM, user_Id);

        reportService.ReportDetailUpload(postId,userId,Text).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.i("On Response Report ",response.message());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.i("On Failure Report",t.getMessage());

            }
        });
    }
}
