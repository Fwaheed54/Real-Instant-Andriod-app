package com.FYP.Adil.realinstant.OtherObjects;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.tasks.OnSuccessListener;

import org.greenrobot.eventbus.EventBus;

public class LocationHelper  implements OnMapReadyCallback {

    private static final int REQUEST_CODE = 1000;

    Context context;
    AppCompatActivity activity;
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationRequest locationRequest;
    LocationCallback locationCallback;


    public LocationHelper(Context context, AppCompatActivity activity) {
            this.context = context;
            this.activity = activity;
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
    }


    public void locationSetup(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
        } else {
            buildLocationRequest();
            buildLocationCallBack();
            StartLocation();
        }
    }

    public void StartLocation() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());

    }

    public void StopLocation() {

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            //TODO write code their
            return;
        }
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }


    public void getLatestLocation(){

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);

            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(activity
                            ,new OnSuccessListener<Location>() {
                                @Override
                                public void onSuccess(Location location) {
                                    Log.i("latestTag",location.getLongitude()+" "+location.getLatitude());

                /*
                tempLocation.setLatitude(location.getLatitude()+"");
                tempLocation.setLongitude(location.getLongitude()+"");*/
                                    //  Log.i("latestTag",location.getLongitude()+" "+location.getLatitude());
                                    // EventBus.getDefault().post(new LocalCoordinate(location.getLatitude()+"",location.getLongitude()+""));
                                }
                            });

        }


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    private void buildLocationCallBack() {
        locationCallback = new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                for(Location location:locationResult.getLocations()) {

                    EventBus.getDefault().postSticky(new LocalCoordinate(location.getLatitude()+"",location.getLongitude()+""));

                }
            }
        };
    }

    private void buildLocationRequest() {

        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setSmallestDisplacement(10);
    }
}
