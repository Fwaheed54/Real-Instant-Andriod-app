package com.FYP.Adil.realinstant.PostFragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;


import com.FYP.Adil.realinstant.HomeActivity;
import com.FYP.Adil.realinstant.Models.Helper;
import com.FYP.Adil.realinstant.Presenters.PostPresenter;
import com.FYP.Adil.realinstant.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;


public class ParameterFragment extends Fragment  implements OnMapReadyCallback,GoogleApiClient.OnConnectionFailedListener  {
    // TODO: Rename parameter arguments, choose names that match

    //All private declatraions for this Fragmengts
    private Button Next, Previous;
    private String KMVal = "2", Hours = "24";
    private TextView HoursRs, RadiusRs;
    private PostPresenter postPresenter;
    private Bundle bundle = new Bundle();
    int count = 0;

    private MapView mapView;
    public GoogleMap googleMap;
    Circle circle;
    private double lat, lng;
    final int REQUEST_CODE_MAP_LOCATION = 101;
    ImageButton openMapBtn;
    Button linearLayout;
    GoogleApiClient mGoogleApiClient;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_parameter, container, false);


        //Map view setting
        mapView = view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);


        // Make Objects
        SeekBar seekBarHD = view.findViewById(R.id.seekHours);
        SeekBar seekbarRadius = view.findViewById(R.id.seekRadius);
        HoursRs = view.findViewById(R.id.HoursRs);
        RadiusRs = view.findViewById(R.id.RadiusRs);
        Next = view.findViewById(R.id.Next);
        Previous = view.findViewById(R.id.Previous);


        lat = Float.parseFloat(getArguments().getString("lati"));
        lng = Float.parseFloat(getArguments().getString("longi"));

        Log.d("lati", String.valueOf(lat));
        Log.d("long", String.valueOf(lng));

        mGoogleApiClient = new GoogleApiClient
                .Builder(getContext())
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();

        mGoogleApiClient.connect();

        //make prsenter object
        postPresenter = new PostPresenter();

        Previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment CF = new CetagoryFragment();
                CF.setArguments(getArguments());
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_container, CF);
                fragmentTransaction.commit();

            }
        });


        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Next.getText().equals("share")) {
                    Log.i("BillTag", "  this is post frag");

                    // Get file in Bundle and pass into UploadFile Method

                    if (getArguments().getString("typeChecker").equals("file")) {
                        File file = (File) getArguments().getSerializable("file");
                        // This call for upload IMAGE/Video file
                        postPresenter.uploadFile(file, KMVal, getArguments().getString("Description"), getArguments().getString("lati")
                                , getArguments().getString("longi"), getArguments().getBoolean("IsGlobal")
                                , Hours, getArguments().getLong("CategoryId") + "", Helper.userId + "", false, getArguments().getString("TypeOfFile"));
                    } else {
                        Log.i("check catch", "hello catch");
                        postPresenter.uploadText(KMVal, getArguments().getString("Description"), getArguments().getString("lati")
                                , getArguments().getString("longi"), getArguments().getBoolean("IsGlobal")
                                , Hours, getArguments().getLong("CategoryId") + "", Helper.userId + "", false);
                    }

                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                    getActivity().startActivity(intent);
                    getActivity().finish();
                } else {

                    Log.i("BillTag", "  this is bill frag");
                    bundle = getArguments();

                    bundle.putString("RadiusRs", RadiusRs.getText() + "");
                    bundle.putString("HoursRs", HoursRs.getText() + "");
                    bundle.putString("KM", KMVal);
                    bundle.putString("Hours", Hours);
                    Fragment BF = new BillFragment();
                    BF.setArguments(bundle);
                    //Trensfer Camera fregement into Category Fregemenet
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame_container, BF);
                    fragmentTransaction.commit();
                }
            }
        });


        //set initial and max values for number of hours
        seekBarHD.setMax(99);
        seekBarHD.setMin(1);
        seekBarHD.setProgress(24);

        //set initial and max values for number of radius
        seekbarRadius.setMax(10);
        seekbarRadius.setMin(1);
        seekbarRadius.setProgress(2);


        // This seekbar is use to Set number of or number days for the post
        seekBarHD.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            int seekBarValue = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBarValue = progress;
                TextView Hours_Days = view.findViewById(R.id.SeekResult);
                Hours_Days.setText(seekBarValue + "H");
                Hours = seekBarValue + "";
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }

            @SuppressLint("SetTextI18n")
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (seekBar.getProgress() > 24) {
                    double temp = CalculateHoursPrice(seekBar.getProgress());
                    HoursRs.setText("€ " +temp );
                    Next.setText("Pay");
                } else {
                    HoursRs.setText("");
                    Next.setText("share");
                }

                if (BillChecker(RadiusRs, HoursRs))
                    Next.setText("share");
                else
                    Next.setText("pay");

            }
        });


        // This seekbar is use to Radius for the post
        seekbarRadius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int BarValue = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                BarValue = progress;
                TextView Km = view.findViewById(R.id.seekRadiusResult);
                Km.setText(BarValue + "KM");
                KMVal = BarValue + "";

                //map view controller
                if (progress <= 5 && progress > 2)
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 12));
                else if (progress > 5)
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 10));
                else
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 13));

                circle.remove();
                circle = googleMap.addCircle(new CircleOptions()
                        .center(new LatLng(lat, lng))
                        .radius(progress * 1000)
                        .strokeColor(Color.parseColor("#ff0099cc"))
                        .fillColor(Color.parseColor("#2271cce7")));
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @SuppressLint("SetTextI18n")
            public void onStopTrackingTouch(SeekBar seekBar) {

                if (seekBar.getProgress() > 2) {
                    double temp = CalculateRadiusPrice(seekBar.getProgress());
                    RadiusRs.setText("€ "+temp );
                } else {
                    RadiusRs.setText("");
                }

                if (BillChecker(RadiusRs, HoursRs))
                    Next.setText("share");
                else
                    Next.setText("pay");
            }
        });

        return view;

    }

    public double CalculateRadiusPrice(double lengthOfRadius) {
        lengthOfRadius = lengthOfRadius - 2;
        return lengthOfRadius * 10;
    }

    public double CalculateHoursPrice(double numberOfHours) {
        numberOfHours = numberOfHours - 24;
        return numberOfHours * 5;
    }


    public boolean BillChecker(TextView tv1, TextView tv2) {

        if (tv1.getText().equals("") && tv2.getText().equals("")) {
            return true;
        }
        return false;
    }


    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;

        // Add a marker in Current Location and move the camera
        LatLng CurrLoc = new LatLng(lat, lng);
        googleMap.addMarker(new MarkerOptions().position(CurrLoc).title("Post Location"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(CurrLoc));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(CurrLoc, 13));

        circle = map.addCircle(new CircleOptions()
                .center(new LatLng(lat, lng))
                .radius(2000)
                .strokeColor(Color.parseColor("#ff0099cc"))
                .fillColor(Color.parseColor("#2271cce7")));


        IntentToGetPlace();

    }


    public void IntentToGetPlace(){

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                PlacePicker.IntentBuilder intentBuilder =
                        new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(intentBuilder.build(getActivity()), REQUEST_CODE_MAP_LOCATION);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

    }





    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_MAP_LOCATION) {
            if (data != null) {
                Place place = PlacePicker.getPlace(data, getContext());
                if (place != null) {
                    lat = place.getLatLng().latitude;
                    lng = place.getLatLng().longitude;
                  /*  MarkerOptions marker = new MarkerOptions().position(
                            place.getLatLng()).title("Hello Maps");
                    marker.icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    googleMap.addMarker(marker);*/
                }
            }
        }

    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
       // Snackbar.make(fabPickPlace, connectionResult.getErrorMessage() + "", Snackbar.LENGTH_LONG).show();
    }
}
