package com.FYP.Adil.realinstant;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.FYP.Adil.realinstant.Contracts.SubscriptionContract;
import com.FYP.Adil.realinstant.Contracts.UserContract;
import com.FYP.Adil.realinstant.Contracts.UserPostsContract;
import com.FYP.Adil.realinstant.Models.Helper;
import com.FYP.Adil.realinstant.Models.Post;
import com.FYP.Adil.realinstant.Models.Subscription;
import com.FYP.Adil.realinstant.Models.User;
import com.FYP.Adil.realinstant.Presenters.PostPresenter;
import com.FYP.Adil.realinstant.Presenters.SubscriptionPresenter;
import com.FYP.Adil.realinstant.Presenters.UserPresenter;
import com.FYP.Adil.realinstant.RecyclerViews.SubscriptionRecyclerAdapter;
import com.FYP.Adil.realinstant.RecyclerViews.UserPostsRecyclerAdapter;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity implements UserContract, UserPostsContract, SubscriptionContract {

    private BottomNavigationView navigation;
    private ImageView userProfile;
    private Button UploadImadBtn;
    private UserPresenter userPresenter;
    private PostPresenter postPresenter;
    private SubscriptionPresenter subscriptionPresenter;
    private int REQUEST_CODE= 1;
    private TextView userProfileName,userProfileEmail,totalUserPosts;
    public TextView UserPaidPoststv,userSubscriptiontv;
    private UserPostsRecyclerAdapter userPostsRecyclerAdapter;
    private SubscriptionRecyclerAdapter subscriptionRecyclerAdapters;
    private File ImageFile;
    private RecyclerView AllinOneRecycler;
    private ArrayList<Subscription> SubscribedAreaNames;
    private ArrayList<Post> UserOwnPost,UserPaidPosts;


    public ProfileActivity() {
        //set constractors of presenters
        userPresenter = new UserPresenter(this);
        postPresenter = new PostPresenter(this);
        subscriptionPresenter = new SubscriptionPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Its make all layout objects
        init();

        //Upload User Profile
        UploadImadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent() ;
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CODE);
            }
        });

        userPresenter.getUserCall(Helper.getSharedPreferences("Bearer",this));
        postPresenter.getUserPosts(Helper.userId);
        subscriptionPresenter.getSubscriptionCall(Helper.userId);

    }

    public void init(){

        //navigation for user profile
        navigation = findViewById(R.id.UserProfileNavId);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        userProfile = findViewById(R.id.ivUserProfilePhoto);
        UploadImadBtn = findViewById(R.id.profileImageUpload);
        userProfileName = findViewById(R.id.userProfileNameId);
        userProfileEmail = findViewById(R.id.userProfileEmailId);
        totalUserPosts = findViewById(R.id.totalUserPostsId);
        UserPaidPoststv = findViewById(R.id.userPaidPostId);
        userSubscriptiontv = findViewById(R.id.UserSubscriptionId);


        //Set Recycler view for user own posts
        AllinOneRecycler = findViewById(R.id.rvUserProfile);
        AllinOneRecycler.setLayoutManager(new LinearLayoutManager(this));

        //set recycler adapter for user own posts
        userPostsRecyclerAdapter = new UserPostsRecyclerAdapter(this,this);
        AllinOneRecycler.setAdapter(userPostsRecyclerAdapter);
        userPostsRecyclerAdapter.setData(new ArrayList<Post>());

        //set subscription adpater
        subscriptionRecyclerAdapters = new SubscriptionRecyclerAdapter(this,this);
    }


    public BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.allUserPostId:
                    AllUserPosts();
                    return true;

                case R.id.allUserPaidPostId:
                    AllPaidPosts();
                    return true;
                case R.id.allUserSubscribedAreasId:
                    AllUserSubscriptions();
                    return true;
            }
            return false;
        }
    };


    @Override
    public void getSingleUser(User user) {
        //Set Post Image using picasso
        Picasso.get().load(user.getDpUrl())
                .error(R.drawable.avatar)
                .placeholder(R.drawable.placeholder)
                .fit()
                .centerCrop()
                .into(userProfile);

        userProfileName.setText(user.getName()+" "+user.getLastName());
        userProfileEmail.setText(user.getEmail());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null){
            Uri imageUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                userProfile.setImageBitmap(bitmap);
                userProfile.setScaleType(ImageView.ScaleType.FIT_CENTER);

                //convert image bitmap to file
                ImageFile = Helper.GetFile(bitmap,this);

                //upload user image , this is retrofit calling function
                // UplaodUserProfileImage(ImageFile,Helper.userId);
                userPresenter.UploadUserProfileImage(ImageFile,Helper.userId);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void getUserPost(ArrayList<Post> posts) {
        userPostsRecyclerAdapter.setData(posts);
        userPostsRecyclerAdapter.notifyDataSetChanged();
        totalUserPosts.setText(posts.size()+"");
        UserOwnPost = posts;
        getPaidPost(posts);
    }

    @Override
    public void SubscriptionCheckerInterface(ArrayList<Subscription> subscriptions) {
        this.SubscribedAreaNames = subscriptions;
        userSubscriptiontv.setText(subscriptions.size()+"");
    }

    public void AllUserPosts(){
        AllinOneRecycler.setLayoutManager(new LinearLayoutManager(this));
        userPostsRecyclerAdapter.notifyDataSetChanged();
        AllinOneRecycler.setAdapter(userPostsRecyclerAdapter);
        userPostsRecyclerAdapter.setData(UserOwnPost);
    }

    public void AllUserSubscriptions(){
        //set recycler adapter for user own subscription

        AllinOneRecycler.setLayoutManager( new GridLayoutManager(this,3));

        subscriptionRecyclerAdapters.notifyDataSetChanged();
        AllinOneRecycler.setAdapter(subscriptionRecyclerAdapters);
        subscriptionRecyclerAdapters.setSubscriptions(SubscribedAreaNames);
    }

    public void AllPaidPosts(){
        AllinOneRecycler.setLayoutManager(new LinearLayoutManager(this));
        userPostsRecyclerAdapter.notifyDataSetChanged();
        AllinOneRecycler.setAdapter(userPostsRecyclerAdapter);
        userPostsRecyclerAdapter.setData(UserPaidPosts);
    }

    public void getPaidPost(ArrayList<Post> posts){
        UserPaidPosts = new ArrayList<>();
        for(Post p
            :posts){
            if(p.getPostDetails() != null){
                UserPaidPosts.add(p);
            }
        }
        UserPaidPoststv.setText(UserPaidPosts.size()+"");
    }

}
