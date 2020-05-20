package com.FYP.Adil.realinstant.RecyclerViews;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.FYP.Adil.realinstant.Models.Helper;
import com.FYP.Adil.realinstant.Models.Subscription;
import com.FYP.Adil.realinstant.Presenters.SubscriptionPresenter;
import com.FYP.Adil.realinstant.R;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.util.ArrayList;

public class SubscriptionRecyclerAdapter extends RecyclerView.Adapter<SubscriptionRecyclerAdapter.GithubViewHolder>  {

    private Context context;
    private AppCompatActivity activity;
    private ArrayList<Subscription> subscriptions;
    private SubscriptionPresenter subscriptionPresenter;



    public SubscriptionRecyclerAdapter(Context context, AppCompatActivity activity) {
        this.context = context;
        this.activity = activity;
        subscriptionPresenter = new SubscriptionPresenter();
    }

    public void setSubscriptions(ArrayList<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }


    @NonNull
    @Override
    public SubscriptionRecyclerAdapter.GithubViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        View view = inflater.inflate(R.layout.item_subscription_view,viewGroup,false);

        return new SubscriptionRecyclerAdapter.GithubViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final GithubViewHolder githubViewHolder, final int i) {
        final Subscription subscription = this.subscriptions.get(i);


        githubViewHolder.subscriptionName.setText(subscription.getSubscriptionName());

        githubViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LovelyStandardDialog(context, LovelyStandardDialog.ButtonLayout.VERTICAL)
                        .setTopColorRes(R.color.colorPrimary)
                        .setButtonsColorRes(R.color.colorPrimary)
                        .setIcon(R.drawable.radar)
                        .setTitle("Are Sure Unsubscribe This Area")
                        .setMessage("Are you sure you want to unsubscribe this area? All of your posts from this area will not be available")
                        .setPositiveButton(android.R.string.ok, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                   subscriptionPresenter.Unsubscribe(Helper.userId+"",subscription.getId()+"");
                                   subscriptions.remove(subscription);
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .show();

                        notifyDataSetChanged();
            }
        });
    }


    @Override
    public int getItemCount() {
        return subscriptions.size();
    }

    public class GithubViewHolder extends RecyclerView.ViewHolder {

        TextView subscriptionName;

        public GithubViewHolder(@NonNull View itemView) {
            super(itemView);

            subscriptionName = itemView.findViewById(R.id.SubscriptionViewId);

        }
    }
}

