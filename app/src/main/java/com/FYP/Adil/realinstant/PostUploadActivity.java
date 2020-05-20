package com.FYP.Adil.realinstant;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.FYP.Adil.realinstant.PostFragment.CameraFregment;
import com.FYP.Adil.realinstant.PostFragment.CetagoryFragment;
import com.FYP.Adil.realinstant.PostFragment.VideoFragment;

public class PostUploadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_upload);

         if(getIntent().getStringExtra("TypeOfPost").equals("Video"))
            loadFragment(new VideoFragment());

         else if(getIntent().getStringExtra("TypeOfPost").equals("Image"))
             loadFragment(new CameraFregment());

         else if(getIntent().getStringExtra("TypeOfPost").equals("Text"))
             loadFragment(new CetagoryFragment());

    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.commit();
    }

}
