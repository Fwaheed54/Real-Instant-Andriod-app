package com.FYP.Adil.realinstant.UserProfileNavigation;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.FYP.Adil.realinstant.Contracts.SubscriptionContract;
import com.FYP.Adil.realinstant.Models.Helper;
import com.FYP.Adil.realinstant.Models.Subscription;
import com.FYP.Adil.realinstant.Presenters.SubscriptionPresenter;
import com.FYP.Adil.realinstant.R;
import com.FYP.Adil.realinstant.RecyclerViews.SubscriptionRecyclerAdapter;

import java.util.ArrayList;

public class Subscriptions extends Fragment implements SubscriptionContract {

    private SubscriptionPresenter subscriptionPresenter;
    private SubscriptionRecyclerAdapter subscriptionRecyclerAdapters;
    private RecyclerView SubscriptionRecycler;


    public Subscriptions() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_subscriptions, container, false);
        subscriptionPresenter = new SubscriptionPresenter(this);

        init(view);


        subscriptionPresenter.getSubscriptionCall(Helper.userId);

        return  view;
    }

    public void init(View view){
        //Set Recycler view for user own subscription
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        SubscriptionRecycler = view.findViewById(R.id.UserOwnSubscription);
        SubscriptionRecycler.setLayoutManager(layoutManager);

        //set recycler adapter for user own subscription
        subscriptionRecyclerAdapters = new SubscriptionRecyclerAdapter(getContext(), (AppCompatActivity) getActivity());
        SubscriptionRecycler.setAdapter(subscriptionRecyclerAdapters);
        subscriptionRecyclerAdapters.setSubscriptions(new ArrayList<Subscription>());
    }

    @Override
    public void SubscriptionCheckerInterface(ArrayList<Subscription> subscriptions) {
        subscriptionRecyclerAdapters.setSubscriptions(subscriptions);
        subscriptionRecyclerAdapters.notifyDataSetChanged();
    }

}
