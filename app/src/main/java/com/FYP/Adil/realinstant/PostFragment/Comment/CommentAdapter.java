package com.FYP.Adil.realinstant.PostFragment.Comment;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.FYP.Adil.realinstant.Models.Comment;
import com.FYP.Adil.realinstant.Models.User;
import com.FYP.Adil.realinstant.R;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.GithubViewHolder> {

     Context context;
     ArrayList<Comment> data;
     ArrayList<User> users;



    public void setData(ArrayList<Comment> data) {
        this.data = data;
    }


    public void addItem(Comment comment){
        data.add(comment);
        notifyDataSetChanged();
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }


    public CommentAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public GithubViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        View view = inflater.inflate(R.layout.list_view_comment_item,viewGroup,false);

        return new GithubViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final GithubViewHolder githubViewHolder, int i) {

        final Comment comment = data.get(i);


        if (comment.getCommentText().matches("")) {
            Toast.makeText(context, "Entered Comment", Toast.LENGTH_SHORT).show();

        }else{
            for (User user : users) {
                if(comment.getUserId().equals(user.getId())) {
                    githubViewHolder.CommentUserName.setText(user.getName()+" "+user.getLastName());
                    break;
                }
            }
            githubViewHolder.CommentText.setText(comment.getCommentText());
        }


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class GithubViewHolder extends RecyclerView.ViewHolder{

        TextView CommentText,CommentUserName;


        public GithubViewHolder(@NonNull View itemView) {
            super(itemView);

            CommentText = itemView.findViewById(R.id.CommentItemText);
            CommentUserName = itemView.findViewById(R.id.commentUserName);
        }
    }

}
