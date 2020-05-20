package com.FYP.Adil.realinstant.Retrofit;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ReportService {

    @Multipart
    @POST("Report")
    Call<Void> ReportDetailUpload(
            @Part("post_id") RequestBody post_id,
            @Part("user_id") RequestBody user_id,
            @Part("reportDescription") RequestBody reportDescription
    );
}
