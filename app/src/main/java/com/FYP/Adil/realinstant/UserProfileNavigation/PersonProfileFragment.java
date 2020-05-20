package com.FYP.Adil.realinstant.UserProfileNavigation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.FYP.Adil.realinstant.R;
import com.FYP.Adil.realinstant.RecyclerViews.SubscriptionRecyclerAdapter;
import com.FYP.Adil.realinstant.RecyclerViews.UserPostsRecyclerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static androidx.appcompat.app.AppCompatActivity.RESULT_OK;


public class PersonProfileFragment extends Fragment implements UserContract,UserPostsContract,SubscriptionContract {

    private ImageView userProfile;
    private Button UploadImadBtn;
    private UserPresenter userPresenter;
    private PostPresenter postPresenter;
    private SubscriptionPresenter subscriptionPresenter;
    private int REQUEST_CODE= 1;
    private TextView userProfileName,userProfileEmail,totalUserPosts;
    private UserPostsRecyclerAdapter userPostsRecyclerAdapter;
    private SubscriptionRecyclerAdapter subscriptionRecyclerAdapters;
    private File ImageFile;
    private RecyclerView ProfilePostRecycler,SubscriptionRecycler;

    ArrayList<Subscription> subscription;

    private BottomNavigationView navigation;
    View view;


    public PersonProfileFragment() {
        // Required empty public constructor
        //set constractors of presenters
        userPresenter = new UserPresenter(this);
        postPresenter = new PostPresenter(this);
        subscriptionPresenter = new SubscriptionPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view = inflater.inflate(R.layout.activity_user_profile, container, false);

        Init();

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

        //call Presenters Function
      // userPresenter.getUserCall(Helper.userId);
        userPresenter.getUserCall(Helper.getSharedPreferences("Bearer",getContext()));
        postPresenter.getUserPosts(Helper.userId);
        subscriptionPresenter.getSubscriptionCall(Helper.userId);

        return view;
    }


    public void Init(){

        navigation = view.findViewById(R.id.UserProfileNavId);
       /// navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        userProfile = view.findViewById(R.id.ivUserProfilePhoto);
        UploadImadBtn = view.findViewById(R.id.profileImageUpload);
        userProfileName = view.findViewById(R.id.userProfileNameId);
        userProfileEmail = view.findViewById(R.id.userProfileEmailId);
        totalUserPosts = view.findViewById(R.id.totalUserPostsId);
        //Set Recycler view for user own posts
        ProfilePostRecycler = view.findViewById(R.id.rvUserProfile);
        ProfilePostRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        //set recycler adapter for user own posts
        userPostsRecyclerAdapter = new UserPostsRecyclerAdapter(getContext(), (AppCompatActivity) getActivity());
        ProfilePostRecycler.setAdapter(userPostsRecyclerAdapter);
        userPostsRecyclerAdapter.setData(new ArrayList<Post>());

        //Set Recycler view for user own subscription
        /*LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        SubscriptionRecycler = view.findViewById(R.id.UserOwnSubscription);
        SubscriptionRecycler.setLayoutManager(layoutManager);

        //set recycler adapter for user own subscription
        subscriptionRecyclerAdapters = new SubscriptionRecyclerAdapter(getContext(),getActivity());
        SubscriptionRecycler.setAdapter(subscriptionRecyclerAdapters);
        subscriptionRecyclerAdapters.setSubscriptions(new ArrayList<Subscription>());*/
    }

    @Override
    public void getSingleUser(User user) {
        //Set Post Image using picasso
        Picasso.get().load(user.getDpUrl())
                .error(R.drawable.error)
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
                Bitmap  bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri);
                userProfile.setImageBitmap(bitmap);
                userProfile.setScaleType(ImageView.ScaleType.FIT_CENTER);

                //convert image bitmap to file
                 ImageFile = Helper.GetFile(bitmap,getContext());

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
    }

    @Override
    public void SubscriptionCheckerInterface(ArrayList<Subscription> subscriptions) {
        this.subscription = subscriptions;
        /*subscriptionRecyclerAdapters.setSubscriptions(subscriptions);
        subscriptionRecyclerAdapters.notifyDataSetChanged();*/
    }


   /* private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.allUserPostId:
                    fragment = new PersonProfileFragment();
                    loadFragment(fragment);
                    return true;

                case R.id.allUserPaidPostId:
                    fragment = new NearbyPeopleFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.allUserSubscribedAreasId:
                    //Set Recycler view for user own subscription
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        SubscriptionRecycler = view.findViewById(R.id.rvUserProfile);
        SubscriptionRecycler.setLayoutManager(layoutManager);

        //set recycler adapter for user own subscription
        subscriptionRecyclerAdapters = new SubscriptionRecyclerAdapter(getContext(),getActivity());
        SubscriptionRecycler.setAdapter(subscriptionRecyclerAdapters);
        subscriptionRecyclerAdapters.setSubscriptions(new ArrayList<Subscription>());

                    subscriptionRecyclerAdapters.setSubscriptions(subscription);
                    subscriptionRecyclerAdapters.notifyDataSetChanged();
                    return true;
            }
            return false;
        }
    };*/


    /*private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.Profile_fragment_container, fragment);
        transaction.commit();
    }*/

}
