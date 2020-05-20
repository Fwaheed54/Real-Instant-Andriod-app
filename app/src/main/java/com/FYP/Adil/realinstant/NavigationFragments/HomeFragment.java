package com.FYP.Adil.realinstant.NavigationFragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.FYP.Adil.realinstant.Contracts.CategoryContract;
import com.FYP.Adil.realinstant.Contracts.PostContract;
import com.FYP.Adil.realinstant.Models.Category;
import com.FYP.Adil.realinstant.Models.Helper;
import com.FYP.Adil.realinstant.Models.Post;
import com.FYP.Adil.realinstant.Models.Subscription;
import com.FYP.Adil.realinstant.OtherObjects.BuilderManager;
import com.FYP.Adil.realinstant.OtherObjects.LocalCoordinate;
import com.FYP.Adil.realinstant.OtherObjects.LocationHelper;
import com.FYP.Adil.realinstant.PostUploadActivity;
import com.FYP.Adil.realinstant.RecyclerViews.PostRecyclerAdapter;
import com.FYP.Adil.realinstant.Presenters.CategoryPresenter;
import com.FYP.Adil.realinstant.Presenters.PostPresenter;
import com.FYP.Adil.realinstant.R;
import com.FYP.Adil.realinstant.Retrofit.PostService;
import com.nightonke.boommenu.BoomButtons.BoomButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.OnBoomListener;
import com.nightonke.boommenu.Util;
import com.yarolegovich.lovelydialog.LovelyChoiceDialog;
import com.yarolegovich.lovelydialog.LovelyTextInputDialog;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements PostContract,CategoryContract {

    private PostService postService;
    private RecyclerView listOfPost;
    private LocationHelper locationHelper;
    private SwipeRefreshLayout swipeContainer;
    private BottomNavigationView navigation;
    private PostRecyclerAdapter postRecyclerAdapter;
    private PostPresenter postPresenter;
    private CategoryPresenter categoryPresenter;
    Button subscribeBtn,FilterBtn;
    private String lat="0.0",lng="0.0";
    View view;
    String SubscriptionName;
    private ArrayList<Subscription> subscriptions;
    private ArrayList<String> CategoriesName;
    private  ArrayList<Integer> FilterByPostions;


    public SwipeRefreshLayout getSwipeContainer() {
        return swipeContainer;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }



    public HomeFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view = inflater.inflate(R.layout.fragment_home, container, false);

        // Its make all layout objects
        init();

        //this function is show all button with animation for uplading post
        initializeBmb3(view);

        //Make presenter object and call get retrofit
        postPresenter = new PostPresenter(this);
        //Make category Presenter object
        categoryPresenter = new CategoryPresenter(this);
        //set All subscription in Array which is located at PostPresenter class
        postPresenter.getSubscriptionCall(Helper.userId);
        //Genrate call to get all post area wise and subscribed
        postPresenter.getPostAreaWiseCall(lat,lng,Helper.userId);
        //Call category
        categoryPresenter.loadCategory();


        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //this line of code is use for get subscription
                postPresenter.getSubscriptionCall(Helper.userId);

                //this line of code is use to get area wise post
                postPresenter.getPostAreaWiseCall(lat,lng,Helper.userId);
            }
        });

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        FilterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenFilterDialogBox();
            }
        });

        return  view;
    }

    public void init(){
        subscribeBtn = view.findViewById(R.id.subscribeBtn);
        FilterBtn = view.findViewById(R.id.FilterBtn);
        listOfPost = view.findViewById(R.id.UsersCardsView);
        navigation = view.findViewById(R.id.navigation);
        swipeContainer = view.findViewById(R.id.swipeContainer);
        this.subscriptions = new ArrayList<>();
        FilterByPostions = new ArrayList<>();

        postRecyclerAdapter = new PostRecyclerAdapter(getContext(), (AppCompatActivity) getActivity());
        listOfPost.setAdapter(postRecyclerAdapter);
        locationHelper = new LocationHelper(getContext(), (AppCompatActivity) getActivity());
        postRecyclerAdapter.setData(new ArrayList<Post>());
        listOfPost.setLayoutManager(new LinearLayoutManager(getContext()));
    }


    public void generateCall(){
        postPresenter.getSubscriptionCall(Helper.userId);

        postPresenter.getPostAreaWiseCall(lat,lng,Helper.userId);
    }


    //This for Subscription alter Dialog box
    public void SubscriptionopenDialog(){

        new LovelyTextInputDialog(getContext(), R.style.TintTheme)
                .setTopColorRes(R.color.blue)
                .setTitle("Wirtte your Subscription Name")
                .setMessage("Subscribe")
                .setIcon(R.drawable.radar)
                .setNegativeButton("Cancel",null)
                .setConfirmButton(android.R.string.ok, new LovelyTextInputDialog.OnTextInputConfirmListener() {
                    @Override
                    public void onTextInputConfirmed(String text) {
                        if(!lat.isEmpty() || !lng.isEmpty()){
                            postPresenter.AddSubscription(text+"","2",lat+"",lng+"","1",Helper.userId+"");
                            Toast.makeText(getContext(), "Subscription Added", Toast.LENGTH_LONG).show();
                            postPresenter.getSubscriptionCall(Helper.userId);

                            //set state change actions
                            subscribeBtn.setText("Subscribed Area");
                            subscribeBtn.setBackgroundResource(R.drawable.un_subscribe_button);
                            subscribeBtn.setTag(1);
                        }
                        else
                            Toast.makeText(getContext(), "Check Your Internet Connection", Toast.LENGTH_LONG).show();
                    }
                })
                .show();
    }

    public void OpenFilterDialogBox(){
        new LovelyChoiceDialog(getContext(), R.style.TintTheme)
                .setTopColorRes(R.color.blue)
                .setTitle("HashTag")
                .setIcon(R.drawable.filter)
                .setItemsMultiChoice(CategoriesName, new LovelyChoiceDialog.OnItemsSelectedListener<String>() {
                    @Override
                    public void onItemsSelected(List<Integer> positions, List<String> items) {
                      //  Log.i("checkBox",positions.get(0).toString()+items.get(0));
                            FilterByPostions = (ArrayList<Integer>) positions;
                            postPresenter.getPostAreaWiseCall(lat,lng,Helper.userId);
                    }
                })
                .setConfirmButtonText("Confirm")
                .show();
    }

    //This function check subscription which is in the 2KM radius or not
    public boolean SubscriptionChecker(){
        if(!subscriptions.isEmpty()) {
            for (Subscription subscription : subscriptions) {
                double distance = Helper.distance(Double.parseDouble(subscription.getLati())
                        , Double.parseDouble(subscription.getLongi())
                        , Double.parseDouble(lat)
                        , Double.parseDouble(lng)
                        , "K");

                if (distance <= 2.0) {
                    SubscriptionName = subscription.getSubscriptionName();
                    return true;
                }
            }
        }
        return false;
    }

    //This function set the sates of subscription button
    @SuppressLint("ResourceAsColor")
    void setSubscriptionState(){
        if(SubscriptionChecker()){
            subscribeBtn.setText("Subscribed Area");
            subscribeBtn.setBackgroundResource(R.drawable.un_subscribe_button);
            subscribeBtn.setTag(1);
            subscribeBtn.setEnabled(false);
        }else{
            subscribeBtn.setText("Subscribe Area ");
            subscribeBtn.setBackgroundResource(R.drawable.subscription_button_style);
            subscribeBtn.setTag(0);

        }
    }

    //This Function is using for filter post by its category
    public ArrayList<Post> FilterPost(ArrayList<Post> posts, ArrayList<Integer> CategoriesId){
        ArrayList<Post> TempPosts = new ArrayList<>();

        for (int id : CategoriesId) {
            for (Post post : posts){
                if(post.getCategoryId() == id){
                    TempPosts.add(post);
                }
            }
        }
        return  TempPosts;
    }

    @Override
    public void getAreaWisePost(ArrayList<Post> Posts) {
        swipeContainer.setRefreshing(false);
        //this is filter post before setting in recycler view
        if(!FilterByPostions.isEmpty()) {
            postRecyclerAdapter.setData(FilterPost(Posts, FilterByPostions));
            postRecyclerAdapter.notifyDataSetChanged();
        }
        else{
            postRecyclerAdapter.setData(Posts);
            postRecyclerAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void SubscriptionCheckerInterface(final ArrayList<Subscription> subscriptions) {

        this.subscriptions = subscriptions;
        // set subscription button color
        setSubscriptionState();

        //subscribe Button Action
        subscribeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getTag().toString().equals("0")){
                    SubscriptionopenDialog();
                }else {
                    //when user press unsubscribe the button
                    postPresenter.AddSubscription(SubscriptionName+"","2",lat+"",lng+"","0",Helper.userId+"");
                  //  postPresenter.AddSubscription("TODO"+"","2",lat+"",lng+"","0",Helper.userId+"");
                    subscribeBtn.setText("Subscribe Area");
                    subscribeBtn.setBackgroundResource(R.drawable.subscription_button_style);
                    subscribeBtn.setTag(0);

                    Toast.makeText(getContext(), "Subscription Cancelled", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    @Override
    public void getCategory(final ArrayList<Category> categories) {
        //This Loop set categories in Array list
        CategoriesName = new ArrayList<>();
        for (Category category :
                categories) {
            CategoriesName.add(category.CategoryType);
        }
    }


    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onSimpleEvent(LocalCoordinate localCoordinate) {
        setLat(localCoordinate.getLatitude());
        setLng(localCoordinate.getLongitude());
        generateCall();
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
        Log.e("DEBUG", "onResume of HomeFragment");
        super.onResume();
        locationHelper.locationSetup();
    }

    @Override
    public void onPause() {
        Log.e("DEBUG", "OnPause of HomeFragment");
        super.onPause();
        locationHelper.StopLocation();
    }




    private void initializeBmb3(View view) {

        BoomMenuButton bmb = view.findViewById(R.id.bmb1);

        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++)
            bmb.addBuilder(BuilderManager.getSimpleCircleButtonBuilder(i));


        bmb.getCustomButtonPlacePositions().add(new PointF(Util.dp2px(-80), Util.dp2px(-80)));
        bmb.getCustomButtonPlacePositions().add(new PointF(0, 0));
        bmb.getCustomButtonPlacePositions().add(new PointF(Util.dp2px(+80), Util.dp2px(+80)));


        // Use OnBoomListener to listen all methods
        bmb.setOnBoomListener(new OnBoomListener() {
            @Override
            public void onClicked(int index, BoomButton boomButton) {

                Intent CreatePostIntent = new Intent(getContext(),PostUploadActivity.class);
                switch (index){
                    case 0:
                       CreatePostIntent.putExtra("TypeOfPost","Video");
                       startActivity(CreatePostIntent);
                        break;
                    case 1:
                        CreatePostIntent.putExtra("TypeOfPost","Image");
                        startActivity(CreatePostIntent);
                        break;
                    case 2:
                        CreatePostIntent.putExtra("TypeOfPost","Text");
                        startActivity(CreatePostIntent);
                        break;
                }
            }

            @Override
            public void onBackgroundClick() {

            }

            @Override
            public void onBoomWillHide() {

            }

            @Override
            public void onBoomDidHide() {

            }

            @Override
            public void onBoomWillShow() {

            }

            @Override
            public void onBoomDidShow() {

            }
        });
    }


}
