package com.FYP.Adil.realinstant.RecyclerViews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.FYP.Adil.realinstant.CommentActivity;
import com.FYP.Adil.realinstant.Contracts.ReportContract;
import com.FYP.Adil.realinstant.EventBus.CommentEvent;
import com.FYP.Adil.realinstant.Models.Comment;
import com.FYP.Adil.realinstant.Models.Helper;
import com.FYP.Adil.realinstant.Models.Post;
import com.FYP.Adil.realinstant.Models.React;
import com.FYP.Adil.realinstant.Models.User;
import com.FYP.Adil.realinstant.OtherObjects.NoOfAction;
import com.FYP.Adil.realinstant.Presenters.ReportPresenter;
import com.FYP.Adil.realinstant.R;
import com.bumptech.glide.Glide;
import com.marcinmoskala.videoplayview.VideoPlayView;
import com.squareup.picasso.Picasso;
import com.yarolegovich.lovelydialog.LovelyTextInputDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostRecyclerAdapter extends RecyclerView.Adapter<PostRecyclerAdapter.GithubViewHolder> implements ReportContract {

    private Context context;
    private ArrayList<Post> data;
    private AppCompatActivity activity;
    private  NoOfAction Binder = new NoOfAction();
    private ReportPresenter reportPresenter;


    public PostRecyclerAdapter(Context context, AppCompatActivity activity) {
        this.context = context;
        this.activity = activity;
        reportPresenter = new ReportPresenter();
    }


    public void setData(ArrayList<Post> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public GithubViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        View view = inflater.inflate(R.layout.item_layout, viewGroup, false);

        return new GithubViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final GithubViewHolder githubViewHolder, final int i) {


        final Post post = data.get(i);
        final User user = post.getUser();

        final  ArrayList<React> reacts = (ArrayList<React>) post.getReact();


       // Set reacts on post and also check all action (Null or Not)
       setNoOfActionOnPost(reacts, (ArrayList<Comment>) data.get(i).getComment());
       githubViewHolder.Comments.setText(Binder.getComments()+"");
       githubViewHolder.Likes.setText(Binder.getLikes()+"");
       githubViewHolder.Dislikes.setText(Binder.getDislikes()+"");


       //First Check react are null or not
            if(!reacts.isEmpty()){
                //Then get all reacts against the post
                final React react = getUserReact(Helper.userId, reacts);
                    if(react.getAction() != null){
                        ReactChecker(githubViewHolder,react.getAction());
                    }
                    else {
                        githubViewHolder.Like_Btn.setTag(0);
                        githubViewHolder.Like_Btn.setBackgroundResource(R.drawable.unpress_like);

                        githubViewHolder.Dislike_Btn.setTag(0);
                        githubViewHolder.Dislike_Btn.setBackgroundResource(R.drawable.unpress_dislike);
                    }
            }else{

                githubViewHolder.Like_Btn.setTag(0);
                githubViewHolder.Like_Btn.setBackgroundResource(R.drawable.unpress_like);

                githubViewHolder.Dislike_Btn.setTag(0);
                githubViewHolder.Dislike_Btn.setBackgroundResource(R.drawable.unpress_dislike);
            }

        Picasso.get().load(user.getDpUrl())
                .error(R.drawable.error)
                .placeholder(R.drawable.placeholder)
                .fit()
                .into(githubViewHolder.circleImageView);

            //Upper Case First letter of first name and last name
        githubViewHolder.UserName.setText(user.getName().substring(0,1).toUpperCase()+user.getName().substring(1).toLowerCase()
                +" "+ user.getLastName().substring(0,1).toUpperCase()+user.getLastName().substring(1).toLowerCase());
        githubViewHolder.description.setText(post.getDescription()+"");


        //set Subscription Name and style
        if(!data.get(i).getSubscriptionName().equals("6767Code")){
            githubViewHolder.SubscriptionName.setText("Subscribed Post By: "+data.get(i).getSubscriptionName()+"");
            githubViewHolder.SubscriptionName.setPadding(10,10,10,10);
            githubViewHolder.SubscriptionName.setTextSize(20);
        }else{
            githubViewHolder.SubscriptionName.setText("Current Area Post");
            githubViewHolder.SubscriptionName.setPadding(10,10,10,10);
            githubViewHolder.SubscriptionName.setTextSize(20);
        }

        //Set Post Image
        if(post.getPostType().equals("image")){
            githubViewHolder.postImg.setVisibility(View.VISIBLE);
            githubViewHolder.videoLayout.setVisibility(View.GONE);
            Picasso.get().load(post.getURL())
                    .error(R.drawable.error)
                    .placeholder(R.drawable.placeholder)
                    .fit()
                    .into(githubViewHolder.postImg);
        }else if(post.getPostType().equals("video")){
            Log.i("tag ","text type");
            githubViewHolder.postImg.setVisibility(View.GONE);
            githubViewHolder.videoLayout.setVisibility(View.VISIBLE);

                try {
                    githubViewHolder.VideoView.setVideoUrl(post.getURL());

                    Picasso.get().load(R.drawable.ic_play_circle_outline_black_48dp)
                            .resize(100,100)
                            .into(githubViewHolder.VideoView.getImageView());
                }catch (Exception e){
                    Log.i("exception",e+"");
                }


            Glide.with(context)
                    .load(R.drawable.loading_gif)
                    .placeholder(R.drawable.placeholder)
                    .into(githubViewHolder.VideoView.getLoadingView());

        }else {
            Log.i("tag ","text type");
            githubViewHolder.postImg.setVisibility(View.GONE);
            githubViewHolder.videoLayout.setVisibility(View.GONE);
        }


        githubViewHolder.Direction_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Direction Tag", " press direction button");


                // Direct to Google map
                String uri = "http://maps.google.com/maps?f=d&hl=es&daddr=" + post.getLati() + "," + post.getLongi();
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                context.startActivity(intent);
            }
        });

        githubViewHolder.Dislike_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("DislikeTag", " press dislike button " + v.getTag());

                if (v.getTag().toString().equals("0")) {
                    //Network Call and set tag and background to the dislike button
                    Helper.react(post.getId() + "", Helper.userId+ "", "2");
                    v.setBackgroundResource(R.drawable.press_dislike);
                    v.setTag("2");

                    // change image dislike button
                     githubViewHolder.Like_Btn.setBackgroundResource(R.drawable.unpress_like);
                    //Change dislike count
                    if(!githubViewHolder.Dislikes.getText().equals("0"))
                        githubViewHolder.Likes.setText(Integer.parseInt((String) githubViewHolder.Dislikes.getText())-1+"");

                    if(githubViewHolder.Like_Btn.getTag().toString().equals("1"))
                        githubViewHolder.Likes.setText(Integer.parseInt((String) githubViewHolder.Likes.getText())-1+"");

                    githubViewHolder.Dislikes.setText(Integer.parseInt((String) githubViewHolder.Dislikes.getText())+1+"");

                    //This Line of code set Tag to Like Button
                        githubViewHolder.Like_Btn.setTag("0");

                } else if(v.getTag().toString().equals("2")){
                    //Network Call and set tag to the dislike button
                    Helper.react(post.getId() + "", Helper.userId+ "", "0");
                    v.setTag("0");
                    v.setBackgroundResource(R.drawable.unpress_dislike);
                    // change image dislike button
                    githubViewHolder.Like_Btn.setBackgroundResource(R.drawable.unpress_like);
                    //Change dislike count
                    githubViewHolder.Dislikes.setText(Integer.parseInt((String) githubViewHolder.Dislikes.getText())-1+"");
                }
            }
        });

        githubViewHolder.Like_Btn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                Log.i("likeTag", " press like button " + v.getTag());

                if (v.getTag().toString().equals("0")) {
                    //Network Call and set tag to the like button
                    Helper.react(post.getId() + "", Helper.userId + "", "1");
                    v.setBackgroundResource(R.drawable.press_like);
                    v.setTag("1");

                    // change image dislike button
                    githubViewHolder.Dislike_Btn.setBackgroundResource(R.drawable.unpress_dislike);
                    //Change like count
                    if(!githubViewHolder.Dislikes.getText().equals("0"))
                        githubViewHolder.Dislikes.setText(Integer.parseInt((String) githubViewHolder.Dislikes.getText())-1+"");

                    else if(githubViewHolder.Dislike_Btn.getTag().toString().equals("2"))
                        githubViewHolder.Dislikes.setText(Integer.parseInt((String) githubViewHolder.Dislikes.getText())-1+"");

                    githubViewHolder.Likes.setText(Integer.parseInt((String) githubViewHolder.Likes.getText())+1+"");

                    //this line of code se tag to Dislike button
                    githubViewHolder.Dislike_Btn.setTag("0");

                } else if(v.getTag().toString().equals("1")){
                    Helper.react(post.getId() + "", Helper.userId + "", "0");
                    v.setTag("0");
                    v.setBackgroundResource(R.drawable.unpress_like);

                    //Change like count
                    githubViewHolder.Likes.setText(Integer.parseInt((String) githubViewHolder.Likes.getText())-1+"");

                }
            }
        });

               githubViewHolder.Comment_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent comment = new Intent(context, CommentActivity.class);
                context.startActivity(comment);
                activity.overridePendingTransition(R.anim.right_slider,R.anim.left_slider);

                EventBus.getDefault().postSticky(new CommentEvent(data.get(i).getComment(),data.get(i).getId(),data.get(i).getUserId()));

            }
        });


        githubViewHolder.Report_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                Log.i("kjfnxm", "jv,");
                final PopupMenu popupMenu = new PopupMenu(context, githubViewHolder.Report_Btn);

                if(data.get(i).getReportStatus() == 1){
                    popupMenu.inflate(R.menu.card_menu_disable);
                }else {
                    popupMenu.inflate(R.menu.card_menu);
                }

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {

                            case R.id.Report_nav:
                                Log.i("Report item ", data.get(i).getReportStatus()+"");
                                popupMenu.inflate(R.menu.card_menu_disable);
                                ReportOpenDialog(data.get(i).getId());
                                data.get(i).setReportStatus(1);

                                break;
                            case R.id.no_interasted:
                                Log.i("Hide ", "jkcm");
                                break;
                        }
                        return false;
                    }
                });

                popupMenu.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }



    public class GithubViewHolder extends RecyclerView.ViewHolder {

        ImageView postImg;
        TextView UserName, description, Likes, Dislikes, Comments,SubscriptionName;
        private ImageButton Direction_Btn, Like_Btn, Dislike_Btn, Comment_Btn;
        ImageButton Report_Btn;
        CircleImageView circleImageView;
        VideoPlayView VideoView ;
        LinearLayout videoLayout;

        public GithubViewHolder(@NonNull View itemView) {
            super(itemView);

            postImg = itemView.findViewById(R.id.cardImg);
            UserName = itemView.findViewById(R.id.UserName);
            description = itemView.findViewById(R.id.Description);
            Likes = itemView.findViewById(R.id.likesCount);
            Dislikes = itemView.findViewById(R.id.dislikesCount);
            Direction_Btn = itemView.findViewById(R.id.directionBtn);
            Dislike_Btn = itemView.findViewById(R.id.dislikeBtn);
            Like_Btn = itemView.findViewById(R.id.likesBtn);
            Comment_Btn = itemView.findViewById(R.id.commentBtn);
            Report_Btn = itemView.findViewById(R.id.Report_btn);
            Comments = itemView.findViewById(R.id.CommentsCount);
            SubscriptionName = itemView.findViewById(R.id.subscriptionNameId);
            circleImageView = itemView.findViewById(R.id.profile_image);
            VideoView = itemView.findViewById(R.id.videoViewId);
            videoLayout = itemView.findViewById(R.id.videoViewLayoutId);
        }
    }

    private void ReactChecker(GithubViewHolder v, int ReactId){

        switch (ReactId){
            case 0:
                v.Like_Btn.setTag(0);
                v.Like_Btn.setBackgroundResource(R.drawable.unpress_like);

                v.Dislike_Btn.setTag(0);
                v.Dislike_Btn.setBackgroundResource(R.drawable.unpress_dislike);
                break;
            case 1:
                v.Like_Btn.setTag(1);
                v.Like_Btn.setBackgroundResource(R.drawable.press_like);

                v.Dislike_Btn.setBackgroundResource(R.drawable.unpress_dislike);
                v.Dislike_Btn.setTag(0);
                break;
            case 2:
                v.Dislike_Btn.setTag(2);
                v.Dislike_Btn.setBackgroundResource(R.drawable.press_dislike);

                v.Like_Btn.setBackgroundResource(R.drawable.unpress_like);
                v.Like_Btn.setTag(0);
                break;
            }
    }

    public React getUserReact(int userId,ArrayList<React> reacts){
        React temp = new React();
        for(React r: reacts){
            if(r.getUserId() == userId){
                temp = r;
                break;
            }
        }
            return temp;
    }

    void setNoOfActionOnPost(ArrayList<React> reacts , ArrayList<Comment> comments){

        if(comments.isEmpty())
            Binder.setComments(0);
        else
          Binder.setComments(comments.size());

        if (reacts.isEmpty()){
            Binder.setDislikes(0);
            Binder.setLikes(0);
        }else {
            setReacts(reacts);
        }
    }


    void setReacts(ArrayList<React> reacts){
        int likes=0,dislikes=0;

        for (React r : reacts){
            if(r.getAction().equals(1))
              likes++;
            else if (r.getAction().equals(2))
              dislikes++;
        }

        Binder.setLikes(likes);
        Binder.setDislikes(dislikes);
    }

    //this function is use to opoup dialog box for reporting against the post
    public void ReportOpenDialog(final int postId){
        new LovelyTextInputDialog(context, R.style.TintTheme)
                .setTopColorRes(R.color.blue)
                .setTitle("Write Your Report")
                .setMessage("Report")
                .setIcon(R.drawable.report)
                .setNegativeButton("Cancel",null)
                .setConfirmButton(android.R.string.ok, new LovelyTextInputDialog.OnTextInputConfirmListener() {
                    @Override
                    public void onTextInputConfirmed(String text) {
                        reportPresenter.ReportPosrCall(Helper.userId+"",postId+"",text+"");
                    }
                })
                .show();
    }

}
