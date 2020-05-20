package com.FYP.Adil.realinstant;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.FYP.Adil.realinstant.Contracts.CommentContract;
import com.FYP.Adil.realinstant.EventBus.CommentEvent;
import com.FYP.Adil.realinstant.Models.Comment;
import com.FYP.Adil.realinstant.Models.Helper;
import com.FYP.Adil.realinstant.Presenters.CommentPresenter;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class CommentActivity extends AppCompatActivity implements CommentContract {


    CommentPresenter commentPresenter;
    EditText CommentText;
    Button CommentBtn;
    int CommentPostId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commnet_activity);
        SlidrInterface slidr;

        CommentText = findViewById(R.id.CommentText);
        CommentBtn = findViewById(R.id.CommentBtnSend);

        commentPresenter = new CommentPresenter(this);

        CommentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!CommentText.getText().toString().matches("")) {
                    commentPresenter.PostCommentsCall(Helper.userId+"",CommentPostId+"",CommentText.getText().toString());
                    EventBus.getDefault().postSticky(new Comment(CommentText.getText().toString(),CommentPostId,Helper.userId));
                    CommentText.setText("");
                }else {
                    Toast.makeText(getBaseContext(), "Entered Comment", Toast.LENGTH_SHORT).show();
                }

            }
        });

        slidr = Slidr.attach(this);
        slidr.unlock();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onSimpleEvent(CommentEvent commentEvent) {
        CommentPostId  = commentEvent.getPostId();
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
    public void SendComment(int UserId, int PostId, String Text) {

    }

}
