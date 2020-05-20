package com.FYP.Adil.realinstant.PostFragment.Comment;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.FYP.Adil.realinstant.Contracts.CommentFragmentContract;
import com.FYP.Adil.realinstant.EventBus.CommentEvent;
import com.FYP.Adil.realinstant.Models.Comment;
import com.FYP.Adil.realinstant.Models.User;
import com.FYP.Adil.realinstant.Presenters.CommentPresenter;
import com.FYP.Adil.realinstant.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CommentFragment extends Fragment implements CommentFragmentContract {


    private RecyclerView listOfComments;
    ArrayList<Comment> comments = new ArrayList<>();
    CommentAdapter commentAdapter;
    CommentPresenter commentPresenter;


    public CommentFragment() {
        // Required empty public constructor
    }


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comment, container, false);
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        commentPresenter = new CommentPresenter(this);
        commentPresenter.getUserCall();

        //Set Recycler view
        listOfComments = view.findViewById(R.id.CommentItemView);
        listOfComments.setLayoutManager(new LinearLayoutManager(getContext()));

        /*listOfComments.setAdapter(new CommentAdapter(getContext()));
        commentAdapter.setData(new ArrayList<Comment>());*/


        return view;
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onSimpleEvent(CommentEvent commentEvent) {
       comments = (ArrayList<Comment>) commentEvent.getComments();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onSimpleEvent(Comment comment) {
        commentAdapter.addItem(comment);
    }


    @Override
    public void getUsers(ArrayList<User> users) {
        //Create a object of Comment Adapter
        commentAdapter = new CommentAdapter(getContext());
        listOfComments.setAdapter(commentAdapter);

        //Set All Comments in Comment Adapter
        commentAdapter.setData(comments);

        //set user name with each comment
        commentAdapter.setUsers(users);
    }
}
