package com.FYP.Adil.realinstant.Retrofit;

public class ApiUtils {
    public static final String BASE_URL = "http://192.168.1.17/TwoInOne/public/api/";

    public static CategoryService getCategoryService() {
        return RetrofitClient.getClient(BASE_URL).create(CategoryService.class);
    }

    public static PostService getImgService() {
        return RetrofitClient.getClient(BASE_URL).create(PostService.class);
    }

    public static UserService getUserService(){

        return RetrofitClient.getClient(BASE_URL).create(UserService.class);
    }

    public static PostService getPostService(){

        return RetrofitClient.getClient(BASE_URL).create(PostService.class);
    }

    public static PaidPostService getPaidPostService(){

        return RetrofitClient.getClient(BASE_URL).create(PaidPostService.class);
    }

    public static ReactService getReactService(){

        return RetrofitClient.getClient(BASE_URL).create(ReactService.class);
    }

    public static  CommentService getCommentService(){
        return RetrofitClient.getClient(BASE_URL).create(CommentService.class);
    }

    public static  SubscriptionService getSubscriptionService(){
        return RetrofitClient.getClient(BASE_URL).create(SubscriptionService.class);
    }

    public static  ReportService getReportService(){
        return RetrofitClient.getClient(BASE_URL).create(ReportService.class);
    }

    public static  CardDetailService getCardDetailService(){
        return RetrofitClient.getClient(BASE_URL).create(CardDetailService.class);
    }


}
