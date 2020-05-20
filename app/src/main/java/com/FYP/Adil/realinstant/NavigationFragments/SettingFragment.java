package com.FYP.Adil.realinstant.NavigationFragments;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.navigation.NavigationView;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.FYP.Adil.realinstant.Contracts.AuthContract;
import com.FYP.Adil.realinstant.Contracts.UserContract;
import com.FYP.Adil.realinstant.LoginActivity;
import com.FYP.Adil.realinstant.Models.AuthMassage;
import com.FYP.Adil.realinstant.Models.Helper;
import com.FYP.Adil.realinstant.Models.User;
import com.FYP.Adil.realinstant.Models.UserLogin;
import com.FYP.Adil.realinstant.Presenters.AuthPresenter;
import com.FYP.Adil.realinstant.Presenters.UserPresenter;
import com.FYP.Adil.realinstant.ProfileActivity;
import com.FYP.Adil.realinstant.R;
import com.squareup.picasso.Picasso;


public class SettingFragment extends Fragment implements UserContract, AuthContract,  NavigationView.OnNavigationItemSelectedListener {

    LinearLayout profileSettingClickable;
    ImageView userImage;
    TextView UserName,UserEmail;
    UserPresenter userPresenter;
    Button LogoutBtn;
    AuthPresenter authPresenter;
    View view;
    Switch LocationSwitch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        //make presenter and send user id for getting the user details.
        userPresenter = new UserPresenter(this);
        userPresenter.getUserCall(Helper.getSharedPreferences("Bearer",getContext()));
        //userPresenter.getUserCall(Helper.userId);

        authPresenter = new AuthPresenter(this);

        NavigationView navigationView = view.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        userImage = view.findViewById(R.id.UserImage);
        UserName = view.findViewById(R.id.UserNameId);
        UserEmail = view.findViewById(R.id.UserEmailid);
        LogoutBtn = view.findViewById(R.id.LogoutBtn);
        LocationSwitch = view.findViewById(R.id.locationSwitch);


        profileSettingClickable =  view.findViewById(R.id.profileSettingId);

        profileSettingClickable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ProfileAct = new Intent(getContext(), ProfileActivity.class);
                getActivity().startActivity(ProfileAct);
            }
        });

        LocationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    userPresenter.HideUserInNearby(1,Helper.userId);
                }else {
                    userPresenter.HideUserInNearby(0,Helper.userId);
                }
            }
        });

       // this.view = view;

        return view;
    }



    @Override
    public void getSingleUser(User user) {

//        Log.i("check",user.getAge());

        Picasso.get().load(user.getDpUrl())
                .error(R.drawable.avatar)
                .placeholder(R.drawable.placeholder)
                .fit()
                .centerCrop()
                .into(userImage);

        UserName.setText(user.getName()+" "+user.getLastName());
        UserEmail.setText(user.getEmail());
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

         if (id == R.id.LogoutBtn) {
            String Token = Helper.getSharedPreferences("Bearer",getContext());
            authPresenter.LogOutUser("Bearer "+Token);
            Helper.removeSharedPreferences("Bearer",getContext());
            getActivity().finish();
            Log.i("share",Helper.preferences+"");
            Log.i("hooo",Token);

        } else if (id == R.id.nav_send) {

        }


       // DrawerLayout drawer = (DrawerLayout) this.view.findViewById(R.id.drawer_layout);
       // drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void getMessage(AuthMassage Massege) {
        Toast.makeText(getActivity(),Massege.getMessage(),Toast.LENGTH_LONG).show();
          Intent intent = new Intent(getContext(),LoginActivity.class);
         startActivity(intent);

    }

    @Override
    public void Login(UserLogin userLogin) {

    }

}
