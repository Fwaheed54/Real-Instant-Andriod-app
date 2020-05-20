package com.FYP.Adil.realinstant;

import android.content.pm.PackageManager;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.FYP.Adil.realinstant.Contracts.UserContract;
import com.FYP.Adil.realinstant.Models.Helper;
import com.FYP.Adil.realinstant.Models.User;
import com.FYP.Adil.realinstant.NavigationFragments.HomeFragment;
import com.FYP.Adil.realinstant.NavigationFragments.NearbyPeopleFragment;
import com.FYP.Adil.realinstant.NavigationFragments.SettingFragment;
import com.FYP.Adil.realinstant.Presenters.AuthPresenter;
import com.FYP.Adil.realinstant.Presenters.UserPresenter;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.view.MenuItem;
import android.widget.Toast;


public class HomeActivity extends AppCompatActivity implements UserContract {


    private BottomNavigationView navigation;
    private  NavigationView navigationView;
    private  AuthPresenter authPresenter;
    private UserPresenter userPresenter;
    private static final int REQUEST_CODE = 1010;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Its make all layout objects
        init();
        getSupportActionBar().hide();

       /* if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);

            return;
        }
*/

        // Here, thisActivity is the current activity
  /*      if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE);

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE },
                        REQUEST_CODE);

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA },
                        REQUEST_CODE);
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }
*/
            //this presenter set user in helper class
        userPresenter = new UserPresenter(this);
        userPresenter.getUserCall(Helper.getSharedPreferences("Bearer",this));



        if(Helper.InternetConnectionChecker(this)){
            //load home fragment for the first time
            loadFragment(new HomeFragment());
        }else
            Toast.makeText(this,"Please Check Your Internet",Toast.LENGTH_LONG).show();
    }

    public void init(){
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_Home:
                    fragment = new HomeFragment();
                    loadFragment(fragment);
                    return true;

                case R.id.navigation_Nearby:
                    fragment = new NearbyPeopleFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_profile:
                    fragment = new SettingFragment();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };


    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.Fragment_Poll, fragment);
        transaction.commit();
    }

    @Override
    public void getSingleUser(User user) {
        Helper.user = user;
        Helper.userId = user.getId();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }

                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }
}
