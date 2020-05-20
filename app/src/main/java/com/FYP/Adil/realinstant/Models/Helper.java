package com.FYP.Adil.realinstant.Models;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;

import com.FYP.Adil.realinstant.Retrofit.ApiUtils;
import com.FYP.Adil.realinstant.Retrofit.ReactService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Helper {

    static public int userId ;
    static public SharedPreferences preferences;
    static public User user;

    public static void setSharedPreferences(String key, String value, Context context) {
         preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getSharedPreferences(String key, Context context) {
         preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }


    public static void removeSharedPreferences(String key, Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(key);
        editor.commit();
    }

    static public void react(String post_Id,String user_Id, String action){

        ReactService reactService = ApiUtils.getReactService();

        RequestBody postId = RequestBody.create(MultipartBody.FORM, post_Id);
        RequestBody Action = RequestBody.create(MultipartBody.FORM, action);
        RequestBody userId = RequestBody.create(MultipartBody.FORM, user_Id);

        Call<Void> call = reactService.like_dislike(userId,postId,Action);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.i("On Response React",response.message());
        }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.i("On Failure React",t.getMessage());

            }
        });
    }


    // Image conversion method
    public static File GetFile(Bitmap bitmap,Context context){


        File f = new File(context.getCacheDir(), "filename");
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }


        //Convert bitmap to byte array
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100 /*ignored for PNG*/, bos);
        byte[] bitmapdata = bos.toByteArray();

        //write the bytes in file
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return f;
    }



    public static Boolean InternetConnectionChecker(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            return true;
        }
        else
            return false;
    }


    public static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        } else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            if (unit.equals("K")) {
                dist = dist * 1.609344;
            } else if (unit.equals("N")) {
                dist = dist * 0.8684;
            }
            return (dist);
        }

    }

}
