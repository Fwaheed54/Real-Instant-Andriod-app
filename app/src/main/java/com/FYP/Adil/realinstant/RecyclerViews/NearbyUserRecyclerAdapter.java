package com.FYP.Adil.realinstant.RecyclerViews;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.FYP.Adil.realinstant.Models.User;
import com.FYP.Adil.realinstant.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NearbyUserRecyclerAdapter extends RecyclerView.Adapter<NearbyUserRecyclerAdapter.GithubViewHolder>  {

    private Context context;
    private AppCompatActivity activity;
    private ArrayList<User> users;


    public NearbyUserRecyclerAdapter(Context context, AppCompatActivity activity) {
        this.context = context;
        this.activity = activity;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }


    @NonNull
    @Override
    public GithubViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        View view = inflater.inflate(R.layout.item_simple_square,viewGroup,false);

        return new GithubViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GithubViewHolder githubViewHolder, int i) {
        User user = users.get(i);



        Picasso.get().load(user.getDpUrl())
                .error(R.drawable.avatar)
                .placeholder(R.drawable.placeholder)
                .fit()
                .centerCrop()
                .into(githubViewHolder.UserImg);

        //Upper Case First letter of first name and last name
        githubViewHolder.userName.setText(user.getName().substring(0,1).toUpperCase()+user.getName().substring(1).toLowerCase()
                +" "+ user.getLastName().substring(0,1).toUpperCase()+user.getLastName().substring(1).toLowerCase());

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class GithubViewHolder extends RecyclerView.ViewHolder {

        ImageView UserImg;
        TextView userName;

        public GithubViewHolder(@NonNull View itemView) {
            super(itemView);

            UserImg = itemView.findViewById(R.id.NearbyPeopleImage);
            userName = itemView.findViewById(R.id.NearbyPeopleUserNameId);
        }
    }
}
