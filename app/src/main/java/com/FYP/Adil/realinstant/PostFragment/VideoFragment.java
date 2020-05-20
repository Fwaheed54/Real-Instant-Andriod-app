package com.FYP.Adil.realinstant.PostFragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.FYP.Adil.realinstant.R;
import com.iceteck.silicompressorr.SiliCompressor;
import com.mohammedalaa.gifloading.LoadingView;

import java.io.File;
import java.net.URISyntaxException;

import static androidx.appcompat.app.AppCompatActivity.RESULT_OK;


public class VideoFragment extends Fragment {

    private static final int RC_VIDEO_CODE = 101;
    private File File;
    private Bundle bundle;
    LoadingView loadingView;

    public VideoFragment() {
        // Required empty public constructor
        bundle = new Bundle();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_video, container, false);
         loadingView = (LoadingView) view.findViewById(R.id.loading_view);

        //intent to video camera
        Intent RecordVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        RecordVideoIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 5);
        RecordVideoIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
        startActivityForResult(RecordVideoIntent,RC_VIDEO_CODE);


        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //this for video
        if(requestCode == RC_VIDEO_CODE){
            if(resultCode == RESULT_OK){
                final Uri uri = data.getData();
                File destinationPath = new File("/storage/emulated/0/DCIM/Realinstant");
                destinationPath.mkdir();
                final File file = new File(destinationPath.getAbsolutePath());
                loadingView.showLoading();

                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        //TODO your background code
                        try {
                            //video compression and get its url from external storage
                            String filePath = SiliCompressor.with(getContext()).compressVideo(getRealPathFromURI(getContext(),uri), file.toString());
                            File = new File(filePath);

                            //create new ui thread for setting video file
                            getActivity().runOnUiThread(new Runnable() {
                                public void run() {
                                    //Make bundle to send all fragement data to an other fragement
                                    bundle.putSerializable("file",File);
                                    bundle.putString("TypeOfFile","video");
                                    loadingView.hideLoading();
                                }
                            });

                        } catch (URISyntaxException e) {
                            Log.d("EXCEPTION", e.toString());
                            e.printStackTrace();
                        }
                        trnasmit();
                    }
                });

            }
        }
        //if back to phone build in came and handle location then code here

    }

    public void trnasmit(){
        //Make new fregements object withe type of Category fregements
        Fragment CF =   new CetagoryFragment();
        CF.setArguments(bundle);
        //Trensfer Camera fregement into Category Fregemenet
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_container,CF);
        fragmentTransaction.commit();
    }


    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            assert cursor != null;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}
