package com.FYP.Adil.realinstant.NavigationFragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.FYP.Adil.realinstant.Contracts.NearbyUserContract;
import com.FYP.Adil.realinstant.Models.Helper;
import com.FYP.Adil.realinstant.Models.User;
import com.FYP.Adil.realinstant.OtherObjects.LocalCoordinate;
import com.FYP.Adil.realinstant.OtherObjects.LocationHelper;
import com.FYP.Adil.realinstant.Presenters.UserPresenter;
import com.FYP.Adil.realinstant.R;
import com.FYP.Adil.realinstant.RecyclerViews.NearbyUserRecyclerAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

public class NearbyPeopleFragment extends Fragment implements NearbyUserContract {

    private RecyclerView NearbyPeopleRecycler;
    private NearbyUserRecyclerAdapter nearbyUserRecyclerAdapter;
    private LocationHelper locationHelper;
    private UserPresenter userPresenter;
    private String lat="0.0",lng="0.0";



    public NearbyPeopleFragment() {
        // Required empty public constructor
        userPresenter = new UserPresenter(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nearby_people, container, false);
        Init(view);
        return view;
    }

    public void Init(View view){
        //set recyclerView layout
        NearbyPeopleRecycler = view.findViewById(R.id.NearbyPeopleRecyclerId);
        NearbyPeopleRecycler.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
       // NearbyPeopleRecycler.setLayoutManager(new GridLayoutManager(getContext(),2));
        //set recycler adapter
        nearbyUserRecyclerAdapter = new NearbyUserRecyclerAdapter(getContext(), (AppCompatActivity) getActivity());
        NearbyPeopleRecycler.setAdapter(nearbyUserRecyclerAdapter);
        nearbyUserRecyclerAdapter.setUsers(new ArrayList<User>());

        locationHelper = new LocationHelper(getContext(), (AppCompatActivity) getActivity());
    }


    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onSimpleEvent(LocalCoordinate localCoordinate) {
        lat = localCoordinate.getLatitude();
        lng =  localCoordinate.getLongitude();
        userPresenter.getNearbyPeople(lat,lng,Helper.userId);
    }

    @Override
    public void onStart() {
        EventBus.getDefault().register(this);
        super.onStart();
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public void onResume() {
        Log.e("DEBUG", "onResume of NearbyFragment");
        super.onResume();
        locationHelper.locationSetup();
    }

    @Override
    public void onPause() {
        Log.e("DEBUG", "OnPause of NearbyFragment");
        super.onPause();
        locationHelper.StopLocation();
    }

    @Override
    public void getNearbyUsers(ArrayList<User> users) {
        nearbyUserRecyclerAdapter.setUsers(users);
        nearbyUserRecyclerAdapter.notifyDataSetChanged();
    }
}
