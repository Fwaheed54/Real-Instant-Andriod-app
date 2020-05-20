package com.FYP.Adil.realinstant.Contracts;

import com.FYP.Adil.realinstant.Models.Post;
import com.FYP.Adil.realinstant.Models.Subscription;

import java.util.ArrayList;

public interface PostContract {

     void getAreaWisePost(ArrayList<Post> Posts);
     void SubscriptionCheckerInterface(ArrayList<Subscription> subscriptions);

   //  void StartLocation();
    //void getSubscription(ArrayList<Subscription> subscriptions);
    //void setSubscriptionArea(String subName,String subRadius,String lati,String longi,String user_Id);
}
